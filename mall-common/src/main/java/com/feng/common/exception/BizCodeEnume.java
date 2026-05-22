package com.feng.common.exception;

public enum BizCodeEnume {

    UNKNOW_EXCEPTION(10000, "Unknown error"),
    VAILD_EXCEPTION(10001, "Validation failed"),
    PRODUCT_UP_EXCEPTION(11000, "Product up exception");

    private final int code;
    private final String msg;

    BizCodeEnume(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
