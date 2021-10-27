package com.jeesite.modules.enums;

/**
 * @description
 * @author: pengxincheng
 * @date: 2019-06-27 14:09
 */
public enum ApplyTypeEnum {

    /**
     * 本地车申请
     */
    LOCAL(0, "本地车申请"),
    /**
     * 外地车申请
     */
    NO_LOCAL(1, "外地车申请");


    private int code;
    private String desc;

    ApplyTypeEnum(int code, String desc) {
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
