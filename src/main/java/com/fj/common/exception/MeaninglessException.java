package com.fj.common.exception;


import com.fj.common.base.BizStatus;

public class MeaninglessException extends RuntimeException {

    /**
     * 业务状态
     */
    protected BizStatus bizStatus;


    public MeaninglessException(BizStatus bizStatus) {
        super();
        this.bizStatus = bizStatus;
    }

    public MeaninglessException(BizStatus bizStatus, String cause) {
        super(cause);
        this.bizStatus = bizStatus;
    }

    public BizStatus getBizStatus() {
        return bizStatus;
    }
}
