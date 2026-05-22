package com.feng.common.constant;

public class ProductConstant {

    public enum AttrEnum {

        ATTR_TYPE_BASE(1, "base attr"),
        ATTR_TYPE_SALE(0, "sale attr");

        private int code;
        private String msg;

        AttrEnum(int code, String msg) {
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

    public enum ProductStatusEnum {
        NEW_SPU(0, "new"),
        SPU_UP(1, "up"),
        SPU_DOWN(2, "down");

        private int code;

        private String msg;

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

        ProductStatusEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

    }

}
