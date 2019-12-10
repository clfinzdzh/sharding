package com.fj.common.util;

import com.fj.common.enums.CommonFrameBizStatus;
import com.fj.common.exception.BusinessStatusException;
import org.springframework.util.StringUtils;

public class ParamCheckUtil {

    public static boolean isIntNum(String number) {
        if (!StringUtils.isEmpty(number) && number.matches("^[-+]?[0-9]*[1-9][0-9]*$")) {
            return Boolean.TRUE;
        }
        throw new BusinessStatusException(CommonFrameBizStatus.PAGE_PARAM_EXCEPTION);
    }
}
