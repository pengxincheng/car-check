package com.jeesite.modules.enums;

/**
 * @description
 * @author: pengxincheng
 * @date: 2019-06-27 14:09
 */
public enum BillTypeEnum {

    /**
     * 正单
     */
    NOMAL_BILL(0, "正单"),
    /**
     * 负单
     */
    REFUND_BILL(1, "负单"),

    /**
     * 已退过单的正单
     */
    HAS_REFUNDED(2,"已退过单的正单"),

    UN_SETTLE(3,"未结算"),

    SETTLED(4,"已结算")
    ;

    private int code;
    private String desc;

    BillTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
