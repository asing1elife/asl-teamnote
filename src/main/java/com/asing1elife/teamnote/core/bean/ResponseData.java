package com.asing1elife.teamnote.core.bean;

import lombok.Data;

@Data
public class ResponseData {

    private static final String SUCCESS_CODE = "200";

    private static final String ERROR_CODE = "400";

    private String status = SUCCESS_CODE;

    private Object data;

    public Boolean isSuccess() {
        return this.status.equalsIgnoreCase(SUCCESS_CODE);
    }

    public void setError(Object data) {
        this.data = data;
        this.status = ERROR_CODE;
    }

}
