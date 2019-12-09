package com.fj.exception;


import com.fj.base.BizStatus;

/**
 * @Desc 自定义业务异常
 * @Author
 * @Date 2019-08-07
 **/
public class BusinessStatusException extends MeaninglessException {

    public BusinessStatusException(BizStatus bizStatus) {
        super(bizStatus);
    }

    public BusinessStatusException(BizStatus bizStatus, String cause) {
        super(bizStatus, cause);
    }
}
