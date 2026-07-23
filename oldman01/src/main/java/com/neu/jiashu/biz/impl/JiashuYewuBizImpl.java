package com.neu.jiashu.biz.impl;

import com.neu.common.auth.DangqianYonghu;
import com.neu.config.UploadPaths;
import com.neu.jiashu.biz.JiashuYewuBiz;
import com.neu.jiashu.mapper.JiashuYewuMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
public class JiashuYewuBizImpl implements JiashuYewuBiz {

    private static final long MAX_AVATAR_SIZE = 5L * 1024L * 1024L;
    private static final Path AVATAR_DIR = UploadPaths.avatarDir();
    private static final Set<String> ALLOWED_AVATAR_EXTENSIONS = Set.of("jpg", "jpeg", "png", "webp");

    private final JiashuYewuMapper jiashuYewuMapper;

    public JiashuYewuBizImpl(JiashuYewuMapper jiashuYewuMapper) {
        this.jiashuYewuMapper = jiashuYewuMapper;
    }

    @Override
    public List<Map<String, Object>> laorenXinxi(DangqianYonghu dangqianYonghu) {
        requireJiashu(dangqianYonghu);
        return jiashuYewuMapper.findLaorenXinxi(dangqianYonghu.yonghuId());
    }

    @Override
    public Map<String, Object> laorenTouxiangUpload(DangqianYonghu dangqianYonghu, Long laorenId, MultipartFile file) {
        requireBangding(dangqianYonghu, laorenId);
        String url = saveAvatar(file);
        jiashuYewuMapper.updateLaorenTouxiang(laorenId, url);
        return Map.of("url", url);
    }

    @Override
    public List<Map<String, Object>> laorenXiugaiShenqingList(DangqianYonghu dangqianYonghu) {
        requireJiashu(dangqianYonghu);
        return jiashuYewuMapper.findLaorenXiugaiShenqingList(dangqianYonghu.yonghuId());
    }

    @Override
    public void laorenXiugaiShenqingAdd(DangqianYonghu dangqianYonghu, Map<String, Object> data) {
        Long laorenId = requireLong(data, "laorenId");
        requireBangding(dangqianYonghu, laorenId);
        Map<String, Object> base = jiashuYewuMapper.findLaorenXiugaiBase(dangqianYonghu.yonghuId(), laorenId);
        if (base == null) {
            throw new IllegalArgumentException("当前家属未绑定该老人");
        }
        Map<String, Object> insertData = new LinkedHashMap<>();
        insertData.put("laorenId", laorenId);
        insertData.put("jiashuId", base.get("jiashuId"));
        insertData.put("jiashuXingming", base.get("jiashuXingming"));
        insertData.put("laorenXingming", base.get("laorenXingming"));
        insertData.put("yuanLianxiDianhua", base.get("lianxiDianhua"));
        insertData.put("xinLianxiDianhua", cleanString(data.get("xinLianxiDianhua")));
        insertData.put("yuanJiatingZhuzhi", base.get("jiatingZhuzhi"));
        insertData.put("xinJiatingZhuzhi", cleanString(data.get("xinJiatingZhuzhi")));
        insertData.put("yuanTouxiang", base.get("touxiang"));
        insertData.put("xinTouxiang", cleanString(data.get("xinTouxiang")));
        insertData.put("yuanBeizhu", base.get("beizhu"));
        insertData.put("xinBeizhu", cleanString(data.get("xinBeizhu")));
        insertData.put("xiugaiShuoming", cleanString(data.get("xiugaiShuoming")));
        jiashuYewuMapper.insertLaorenXiugaiShenqing(insertData);
    }

    @Override
    public Map<String, Object> laorenXiugaiTouxiangUpload(DangqianYonghu dangqianYonghu, Long laorenId, MultipartFile file) {
        requireBangding(dangqianYonghu, laorenId);
        return Map.of("url", saveAvatar(file));
    }

    @Override
    public Map<String, Integer> tongji(DangqianYonghu dangqianYonghu) {
        requireJiashu(dangqianYonghu);
        Map<String, Integer> data = new LinkedHashMap<>();
        data.put("bangdingLaoren", jiashuYewuMapper.countBangdingLaorenByYonghuId(dangqianYonghu.yonghuId()));
        data.put("zaizhuLaoren", jiashuYewuMapper.countZaizhuLaorenByYonghuId(dangqianYonghu.yonghuId()));
        data.put("zuijinHuliJilu", jiashuYewuMapper.countZuijinHuliJilu(dangqianYonghu.yonghuId()));
        data.put("zuijinJiankangJiance", jiashuYewuMapper.countZuijinJiankangJiance(dangqianYonghu.yonghuId()));
        data.put("daishenpiWaichu", jiashuYewuMapper.countDaishenpiWaichu(dangqianYonghu.yonghuId()));
        data.put("daishenpiTuizhu", jiashuYewuMapper.countDaishenpiTuizhu(dangqianYonghu.yonghuId()));
        return data;
    }

    @Override
    public List<Map<String, Object>> chuangweiInfo(DangqianYonghu dangqianYonghu, Long laorenId) {
        requireBangdingIfPresent(dangqianYonghu, laorenId);
        return jiashuYewuMapper.findChuangweiInfo(dangqianYonghu.yonghuId(), laorenId);
    }

    @Override
    public List<Map<String, Object>> huliJiluList(DangqianYonghu dangqianYonghu, Long laorenId) {
        requireBangdingIfPresent(dangqianYonghu, laorenId);
        return jiashuYewuMapper.findHuliJiluList(dangqianYonghu.yonghuId(), laorenId);
    }

    @Override
    public List<Map<String, Object>> jiankangJianceList(DangqianYonghu dangqianYonghu, Long laorenId) {
        requireBangdingIfPresent(dangqianYonghu, laorenId);
        return jiashuYewuMapper.findJiankangJianceList(dangqianYonghu.yonghuId(), laorenId);
    }

    @Override
    public List<Map<String, Object>> yongyaoJihuaList(DangqianYonghu dangqianYonghu, Long laorenId) {
        requireBangdingIfPresent(dangqianYonghu, laorenId);
        return jiashuYewuMapper.findYongyaoJihuaList(dangqianYonghu.yonghuId(), laorenId);
    }

    @Override
    public List<Map<String, Object>> fuyaoJiluList(DangqianYonghu dangqianYonghu, Long laorenId) {
        requireBangdingIfPresent(dangqianYonghu, laorenId);
        return jiashuYewuMapper.findFuyaoJiluList(dangqianYonghu.yonghuId(), laorenId);
    }

    @Override
    public List<Map<String, Object>> feiyongZhangdanList(DangqianYonghu dangqianYonghu, Long laorenId) {
        requireBangdingIfPresent(dangqianYonghu, laorenId);
        return jiashuYewuMapper.findFeiyongZhangdanList(dangqianYonghu.yonghuId(), laorenId);
    }

    @Override
    public List<Map<String, Object>> jiaofeiJiluList(DangqianYonghu dangqianYonghu, Long laorenId) {
        requireBangdingIfPresent(dangqianYonghu, laorenId);
        return jiashuYewuMapper.findJiaofeiJiluList(dangqianYonghu.yonghuId(), laorenId);
    }

    @Override
    public List<Map<String, Object>> waichuList(DangqianYonghu dangqianYonghu, Long laorenId) {
        requireBangdingIfPresent(dangqianYonghu, laorenId);
        return jiashuYewuMapper.findWaichuList(dangqianYonghu.yonghuId(), laorenId);
    }

    @Override
    public void waichuAdd(DangqianYonghu dangqianYonghu, Map<String, Object> data) {
        Long laorenId = requireLong(data, "laorenId");
        requireBangding(dangqianYonghu, laorenId);
        Map<String, Object> jiashu = requireJiashuXinxi(dangqianYonghu, laorenId);
        data.put("huliRenyuanId", null);
        data.put("shenqingRenId", jiashu.get("id"));
        data.put("shenqingRenLeixing", "家属");
        data.put("shenqingRenXingming", jiashu.get("xingming"));
        jiashuYewuMapper.insertWaichu(data);
    }

    @Override
    public List<Map<String, Object>> tuizhuList(DangqianYonghu dangqianYonghu, Long laorenId) {
        requireBangdingIfPresent(dangqianYonghu, laorenId);
        return jiashuYewuMapper.findTuizhuList(dangqianYonghu.yonghuId(), laorenId);
    }

    @Override
    public void tuizhuAdd(DangqianYonghu dangqianYonghu, Map<String, Object> data) {
        Long laorenId = requireLong(data, "laorenId");
        requireBangding(dangqianYonghu, laorenId);
        Map<String, Object> jiashu = requireJiashuXinxi(dangqianYonghu, laorenId);
        data.put("huliRenyuanId", null);
        data.put("shenqingRenId", jiashu.get("id"));
        data.put("shenqingRenLeixing", "家属");
        data.put("shenqingRenXingming", jiashu.get("xingming"));
        jiashuYewuMapper.insertTuizhu(data);
    }

    private void requireBangdingIfPresent(DangqianYonghu dangqianYonghu, Long laorenId) {
        requireJiashu(dangqianYonghu);
        if (laorenId != null) {
            requireBangding(dangqianYonghu, laorenId);
        }
    }

    private void requireBangding(DangqianYonghu dangqianYonghu, Long laorenId) {
        requireJiashu(dangqianYonghu);
        if (jiashuYewuMapper.countBangdingLaoren(dangqianYonghu.yonghuId(), laorenId) == 0) {
            throw new IllegalArgumentException("只能查看或操作自己绑定的老人");
        }
    }

    private void requireJiashu(DangqianYonghu dangqianYonghu) {
        if (!dangqianYonghu.hasJuese("jiashu")) {
            throw new IllegalArgumentException("只有家属可以访问家属端业务");
        }
    }

    private Map<String, Object> requireJiashuXinxi(DangqianYonghu dangqianYonghu, Long laorenId) {
        requireJiashu(dangqianYonghu);
        Map<String, Object> jiashu = jiashuYewuMapper.findJiashuByYonghuIdAndLaorenId(dangqianYonghu.yonghuId(), laorenId);
        if (jiashu == null) {
            throw new IllegalArgumentException("当前账号未绑定家属信息");
        }
        return jiashu;
    }

    private Long requireLong(Map<String, Object> data, String key) {
        Object value = data.get(key);
        if (value == null || value.toString().isBlank()) {
            throw new IllegalArgumentException("缺少必要参数：" + key);
        }
        return Long.valueOf(value.toString());
    }

    private void validateAvatar(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("请选择要上传的头像");
        }
        if (file.getSize() > MAX_AVATAR_SIZE) {
            throw new IllegalArgumentException("头像大小不能超过5MB");
        }
        String extension = extension(file.getOriginalFilename());
        if (!ALLOWED_AVATAR_EXTENSIONS.contains(extension)) {
            throw new IllegalArgumentException("只允许上传jpg、jpeg、png、webp格式图片");
        }
    }

    private String saveAvatar(MultipartFile file) {
        validateAvatar(file);
        String extension = extension(file.getOriginalFilename());
        String filename = UUID.randomUUID().toString().replace("-", "") + "." + extension;
        Path target = AVATAR_DIR.resolve(filename).normalize();
        if (!target.startsWith(AVATAR_DIR)) {
            throw new IllegalArgumentException("头像保存路径不合法");
        }
        try {
            Files.createDirectories(AVATAR_DIR);
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException exception) {
            throw new IllegalArgumentException("头像保存失败：" + exception.getMessage());
        }
        return "/uploads/avatar/" + filename;
    }

    private String cleanString(Object value) {
        if (value == null) {
            return null;
        }
        String text = value.toString().trim();
        return text.isEmpty() ? null : text;
    }

    private String extension(String filename) {
        String value = filename == null ? "" : filename.trim();
        int index = value.lastIndexOf('.');
        if (index < 0 || index == value.length() - 1) {
            throw new IllegalArgumentException("图片文件缺少扩展名");
        }
        return value.substring(index + 1).toLowerCase(Locale.ROOT);
    }
}
