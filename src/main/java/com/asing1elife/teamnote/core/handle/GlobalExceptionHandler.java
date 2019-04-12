package com.asing1elife.teamnote.core.handle;

import com.asing1elife.teamnote.core.bean.ResponseData;
import com.asing1elife.teamnote.core.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 自定义异常捕获
     */
    @ExceptionHandler(CustomException.class)
    public ResponseData handleCustomException(CustomException e) {
        logger.error(e.getMessage());

        ResponseData responseData = new ResponseData();
        responseData.setError(e.getMessage());

        return responseData;
    }

    /**
     * 默认异常捕获
     */
    @ExceptionHandler(Exception.class)
    public ResponseData handleException(Exception e) {
        logger.error(e.getMessage(), e);

        String message = "";

        if (e.getMessage().contains("uni_idx_name")) {
            message = "名称冲突，无法重复添加";
        }

        if (e.getMessage().contains("SQL [n/a]; constraint [null]")) {
            message = "待删除项存在子内容关联，无法直接删除";
        }

        ResponseData responseData = new ResponseData();
        responseData.setError(message);

        return responseData;
    }

}
