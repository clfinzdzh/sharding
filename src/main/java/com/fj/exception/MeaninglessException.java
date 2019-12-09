package com.fj.exception;


import com.fj.base.BizStatus;

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
