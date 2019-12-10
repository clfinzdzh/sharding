package com.fj.common.base;


import com.fj.common.enums.CommonFrameBizStatus;

import java.io.Serializable;

public class BaseResult<T> implements Serializable, BizStatus {

    /**
     * 响应数据
     */
    protected T data;
    /**
     * tips
     */
    protected String tips;
    /**
     * 响应业务状态
     */
    protected BizStatus status;

    /**
     * 默认成功响应
     */
    protected BaseResult() {
        this.status = CommonFrameBizStatus.SUCCESSFUL;
    }
    /**
     * 成功响应，返回响应数据
     */
    protected BaseResult(T data) {
        this();
        this.data = data;
    }

    /**
     * 成功响应没有响应数据时
     *
     * @return
     */
    public static BaseResult success() {
        return new BaseResult<>();
    }

    /**
     * 成功响应有响应数据时
     */
    public static <T> BaseResult success(T data) {
        return new BaseResult<>(data);
    }

    public static <T> BaseResult error(BizStatus bizStatus) {
        BaseResult<T> baseResult = new BaseResult<>();
        baseResult.status = bizStatus;
        return baseResult;
    }

    public static <T> BaseResult error(BizStatus bizStatus, String cause) {
        BaseResult<T> error = error(bizStatus);
        error.tips = cause == null ? "" : cause;
        return error;
    }

    public T getData() {
        return data;
    }

    public String getTips() {
        return tips;
    }

    @Override
    public int getCode() {
        return status.getCode();
    }

    @Override
    public String getDesc() {
        return status.getDesc();
    }
}
