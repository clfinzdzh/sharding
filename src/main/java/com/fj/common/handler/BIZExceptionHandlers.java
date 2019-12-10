package com.fj.common.handler;


import com.fj.common.base.BaseResult;
import com.fj.common.enums.CommonFrameBizStatus;
import com.fj.common.exception.BusinessStatusException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class BIZExceptionHandlers {

    @ExceptionHandler(BindException.class)
    public BaseResult paramBindErrorHandler(BindException e) {
        StringBuffer buffer = new StringBuffer();
        e.getFieldErrors()
                .forEach(s ->
                        buffer.append("【")
                                .append(s.getField() + " ==> " + s.getDefaultMessage())
                                .append("】")
                );
        return BaseResult.error(CommonFrameBizStatus.METHOD_PARAM_VALID_EXCEPTION, buffer.toString());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResult paramBindErrorHandler(MethodArgumentNotValidException e) {
        StringBuffer buffer = new StringBuffer();
        e.getBindingResult().getFieldErrors()
                .forEach(s ->
                        buffer.append("【")
                                .append(s.getField() + " ==> " + s.getDefaultMessage())
                                .append("】")
                );
        return BaseResult.error(CommonFrameBizStatus.METHOD_PARAM_VALID_EXCEPTION, buffer.toString());
    }

    @ExceptionHandler(BusinessStatusException.class)
    public BaseResult businessExceptionHandler(BusinessStatusException exception) {
        return BaseResult.error(exception.getBizStatus(), exception.getMessage());
    }

    @ExceptionHandler(Throwable.class)
    public BaseResult rootExceptionHandler(Throwable exception) {
        log.info("异常信息如下:{}", exception);
        StackTraceElement[] stackTrace = exception.getStackTrace();
        String tips = "";
        if (stackTrace != null && stackTrace.length != 0) {
            tips = exception.getStackTrace()[0].toString();
        }
        return BaseResult.error(CommonFrameBizStatus.UNEXPECTED_EXCEPTION, tips + "==>" + exception.getMessage());
    }
}
