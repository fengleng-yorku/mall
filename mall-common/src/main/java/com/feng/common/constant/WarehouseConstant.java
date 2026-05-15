package com.feng.common.constant;

public class WarehouseConstant {

    public enum PurchaseStatusEnum {
        CREATED(0, "Created"),
        ASSIGNED(1, "Assigned"),
        RECEIVE(2, "Received"),
        FINISH(3, "Finished"),
        HASERROR(4, "Error"),

        ;

        private int code;

        private String msg;

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

        PurchaseStatusEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

    }

    public enum PurchaseDetailStatusEnum {
        CREATED(0, "Created"),
        ASSIGNED(1, "Assigned"),
        BUYING(2, "Purchasing"),
        FINISH(3, "Finished"),
        HASERROR(4, "Purchase Failed"),

        ;

        private int code;

        private String msg;

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

        PurchaseDetailStatusEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

    }

}
