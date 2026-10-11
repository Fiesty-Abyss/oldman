package com.oldman.config;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 统一解析 uploads 根目录，避免各处硬编码本机绝对路径。
 * 优先环境变量 OLDMAN_UPLOAD_DIR；否则尝试 ../uploads（从 oldman01 启动）或 ./uploads。
 */
public final class UploadPaths {

    private UploadPaths() {
    }

    public static Path resolveUploadRoot() {
        String env = System.getenv("OLDMAN_UPLOAD_DIR");
        if (env != null && !env.isBlank()) {
            return Paths.get(env).toAbsolutePath().normalize();
        }
        Path sibling = Paths.get("..", "uploads").toAbsolutePath().normalize();
        if (sibling.toFile().isDirectory()) {
            return sibling;
        }
        return Paths.get("uploads").toAbsolutePath().normalize();
    }

    public static Path avatarDir() {
        return resolveUploadRoot().resolve("avatar");
    }
}
