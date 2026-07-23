package com.neu.wenjian.biz.impl;

import com.neu.common.auth.DangqianYonghu;
import com.neu.wenjian.biz.WenjianBiz;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class WenjianBizImpl implements WenjianBiz {

    private static final long MAX_SIZE = 5L * 1024L * 1024L;
    private static final Path UPLOAD_ROOT = Paths.get("E:/2026shixun/oldman/uploads").toAbsolutePath().normalize();
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final Set<String> ALLOWED_TYPES = Set.of(
            "home", "environment", "login", "zanzhu", "avatar", "default", "captcha"
    );
    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("jpg", "jpeg", "png", "webp");

    @Override
    public List<Map<String, Object>> imageList(DangqianYonghu dangqianYonghu, String type, String keyword) {
        requireAdmin(dangqianYonghu);
        String imageType = StringUtils.hasText(type) ? normalizeType(type) : "";
        String key = StringUtils.hasText(keyword) ? keyword.trim().toLowerCase(Locale.ROOT) : "";
        try {
            List<String> types = StringUtils.hasText(imageType)
                    ? List.of(imageType)
                    : ALLOWED_TYPES.stream().sorted().toList();
            List<Map<String, Object>> images = new ArrayList<>();
            for (String item : types) {
                images.addAll(listTypeImages(item, key));
            }
            images.sort(Comparator.comparing((Map<String, Object> item) -> String.valueOf(item.get("type")))
                    .thenComparing(item -> String.valueOf(item.get("name"))));
            return images;
        } catch (IOException exception) {
            throw new IllegalArgumentException("读取图片列表失败：" + exception.getMessage());
        }
    }

    @Override
    public Map<String, Object> uploadImage(DangqianYonghu dangqianYonghu, MultipartFile file, String type) {
        requireAdmin(dangqianYonghu);
        validateFile(file);
        String imageType = normalizeType(type);
        String extension = extension(file.getOriginalFilename());
        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new IllegalArgumentException("只允许上传jpg、jpeg、png、webp格式图片");
        }

        String filename = UUID.randomUUID().toString().replace("-", "") + "." + extension;
        Path target = safeTarget(imageType, filename);
        save(file, target);
        return imageInfo(imageType, target);
    }

    @Override
    public Map<String, Object> replaceImage(DangqianYonghu dangqianYonghu, MultipartFile file, String type, String oldName) {
        requireAdmin(dangqianYonghu);
        validateFile(file);
        String imageType = normalizeType(type);
        String name = safeName(oldName);
        String oldExtension = extension(name);
        String newExtension = extension(file.getOriginalFilename());
        if (!ALLOWED_EXTENSIONS.contains(oldExtension) || !ALLOWED_EXTENSIONS.contains(newExtension)) {
            throw new IllegalArgumentException("只允许替换jpg、jpeg、png、webp格式图片");
        }

        Path target = safeTarget(imageType, name);
        if (!Files.exists(target) || !Files.isRegularFile(target)) {
            throw new IllegalArgumentException("要替换的图片不存在");
        }
        save(file, target);
        return imageInfo(imageType, target);
    }

    @Override
    public void deleteImage(DangqianYonghu dangqianYonghu, String type, String name) {
        requireAdmin(dangqianYonghu);
        String imageType = normalizeType(type);
        Path target = safeTarget(imageType, safeName(name));
        if (!Files.exists(target) || !Files.isRegularFile(target)) {
            throw new IllegalArgumentException("图片不存在");
        }
        try {
            Files.delete(target);
        } catch (IOException exception) {
            throw new IllegalArgumentException("删除图片失败：" + exception.getMessage());
        }
    }

    private List<Map<String, Object>> listTypeImages(String type, String keyword) throws IOException {
        Path dir = safeTypeDir(type);
        if (!Files.exists(dir)) {
            return List.of();
        }
        try (Stream<Path> stream = Files.list(dir)) {
            return stream
                    .filter(Files::isRegularFile)
                    .filter(this::hasAllowedExtension)
                    .filter(path -> !StringUtils.hasText(keyword)
                            || path.getFileName().toString().toLowerCase(Locale.ROOT).contains(keyword))
                    .map(path -> imageInfo(type, path))
                    .toList();
        }
    }

    private Map<String, Object> imageInfo(String type, Path path) {
        try {
            String name = path.getFileName().toString();
            long size = Files.size(path);
            String lastModified = TIME_FORMATTER.format(Files.getLastModifiedTime(path)
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime());
            return Map.of(
                    "name", name,
                    "filename", name,
                    "url", "/uploads/" + type + "/" + name,
                    "type", type,
                    "size", size,
                    "lastModified", lastModified
            );
        } catch (IOException exception) {
            throw new IllegalArgumentException("读取图片信息失败：" + exception.getMessage());
        }
    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("请选择要上传的图片");
        }
        if (file.getSize() > MAX_SIZE) {
            throw new IllegalArgumentException("图片大小不能超过5MB");
        }
        String extension = extension(file.getOriginalFilename());
        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new IllegalArgumentException("只允许上传jpg、jpeg、png、webp格式图片");
        }
    }

    private void save(MultipartFile file, Path target) {
        try {
            Files.createDirectories(target.getParent());
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException exception) {
            throw new IllegalArgumentException("图片保存失败：" + exception.getMessage());
        }
    }

    private void requireAdmin(DangqianYonghu dangqianYonghu) {
        if (dangqianYonghu == null || !dangqianYonghu.hasJuese("admin")) {
            throw new IllegalArgumentException("只有管理员可以管理图片");
        }
    }

    private String normalizeType(String type) {
        String value = StringUtils.hasText(type) ? type.trim().toLowerCase(Locale.ROOT) : "";
        if (!ALLOWED_TYPES.contains(value)) {
            throw new IllegalArgumentException("图片类型不正确");
        }
        return value;
    }

    private Path safeTypeDir(String type) {
        Path dir = UPLOAD_ROOT.resolve(normalizeType(type)).normalize();
        if (!dir.startsWith(UPLOAD_ROOT)) {
            throw new IllegalArgumentException("图片目录不合法");
        }
        return dir;
    }

    private Path safeTarget(String type, String name) {
        Path target = safeTypeDir(type).resolve(safeName(name)).normalize();
        if (!target.startsWith(safeTypeDir(type))) {
            throw new IllegalArgumentException("图片路径不合法");
        }
        return target;
    }

    private String safeName(String name) {
        String value = StringUtils.hasText(name) ? name.trim() : "";
        if (!StringUtils.hasText(value)
                || value.contains("/")
                || value.contains("\\")
                || value.contains("..")) {
            throw new IllegalArgumentException("图片文件名不合法");
        }
        if (!ALLOWED_EXTENSIONS.contains(extension(value))) {
            throw new IllegalArgumentException("图片文件格式不正确");
        }
        return value;
    }

    private boolean hasAllowedExtension(Path path) {
        try {
            return ALLOWED_EXTENSIONS.contains(extension(path.getFileName().toString()));
        } catch (IllegalArgumentException exception) {
            return false;
        }
    }

    private String extension(String filename) {
        String value = StringUtils.hasText(filename) ? filename.trim() : "";
        int index = value.lastIndexOf('.');
        if (index < 0 || index == value.length() - 1) {
            throw new IllegalArgumentException("图片文件缺少扩展名");
        }
        return value.substring(index + 1).toLowerCase(Locale.ROOT);
    }
}
