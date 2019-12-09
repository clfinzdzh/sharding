package com.fj.enums;

import com.fj.base.BizStatus;

/**
 * @Desc 框架通用异常状态枚举
 * @Author
 * @Date 2019-08-07
 **/
public enum CommonFrameBizStatus implements BizStatus {

    SUCCESSFUL(0, "响应成功"),

    METHOD_PARAM_VALID_EXCEPTION(403014, "方法入参校验异常"),

    UNEXPECTED_EXCEPTION(507003, "出现异常，请联系管理员"),
    PAGE_PARAM_EXCEPTION(507004, "出现异常，请联系管理员");
    private int code;

    private String desc;

    CommonFrameBizStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}
