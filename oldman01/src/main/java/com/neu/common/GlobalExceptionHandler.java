package com.neu.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponse<Void> handleIllegalArgumentException(IllegalArgumentException exception) {
        return new ApiResponse<>(400, exception.getMessage(), null);
    }

    /** 访问不存在的路径（例如直接打开后端根路径）返回 404，而不是当作服务器错误 */
    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Void> handleNoResource(NoResourceFoundException exception) {
        return new ApiResponse<>(404, "接口不存在：" + exception.getResourcePath(), null);
    }

    /** 未预期的异常只记录到日志，不把 SQL、堆栈等内部细节返回给前端 */
    @ExceptionHandler(Exception.class)
    public ApiResponse<Void> handleException(Exception exception) {
        log.error("请求处理失败", exception);
        return ApiResponse.error("服务器处理失败，请稍后重试或联系管理员");
    }
}
