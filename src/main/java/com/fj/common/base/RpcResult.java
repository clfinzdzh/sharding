package com.fj.common.base;


import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RpcResult<T> {

    private int code = 0;

    private String desc = "响应成功";

    private String tips;

    private T data;


    /**
     * 成功响应没有响应数据时
     *
     * @return
     */
    public static RpcResult success() {
        return new RpcResult<>();
    }

    /**
     * 成功响应有响应数据时
     */
    public static <T> RpcResult success(T data) {
        RpcResult<T> rpcResult = new RpcResult<>();
        rpcResult.data = data;
        return rpcResult;
    }

    public static <T> RpcResult error(BizStatus bizStatus) {
        RpcResult<T> rpcResult = new RpcResult<>();
        rpcResult.code = bizStatus.getCode();
        rpcResult.desc = bizStatus.getDesc();
        return rpcResult;
    }

    public static <T> RpcResult error(BizStatus bizStatus, String cause) {
        RpcResult<T> error = error(bizStatus);
        error.tips = cause == null ? "" : cause;
        return error;
    }

}
