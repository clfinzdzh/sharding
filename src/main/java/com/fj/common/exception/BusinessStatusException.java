package com.fj.common.exception;


import com.fj.common.base.BizStatus;

/**
 * @Desc 自定义业务异常
 **/
public class BusinessStatusException extends MeaninglessException {

    public BusinessStatusException(BizStatus bizStatus) {
        super(bizStatus);
    }

    public BusinessStatusException(BizStatus bizStatus, String cause) {
        super(bizStatus, cause);
    }
}
