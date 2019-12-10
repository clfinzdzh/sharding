package com.fj.common.enums;

import com.fj.common.base.BizStatus;

public enum CommonFrameBizStatus implements BizStatus {

    SUCCESSFUL(0, "响应成功"),
    METHOD_PARAM_VALID_EXCEPTION(403014, "方法入参校验异常"),
    UNEXPECTED_EXCEPTION(507003, "出现异常，请联系管理员"),
    PAGE_PARAM_EXCEPTION(507004, "分页参数异常格式异常");

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
