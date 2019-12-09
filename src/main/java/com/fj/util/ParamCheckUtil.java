package com.fj.util;
import com.fj.enums.CommonFrameBizStatus;
import com.fj.exception.BusinessStatusException;
import org.springframework.util.StringUtils;

/**
 * @Desc
 * @Author
 * @Date 2019-08-08
 **/
public class ParamCheckUtil {

    public static boolean isIntNum(String number) {
        if (!StringUtils.isEmpty(number) && number.matches("^[-+]?[0-9]*[1-9][0-9]*$"))
            return Boolean.TRUE;
        throw new BusinessStatusException(CommonFrameBizStatus.PAGE_PARAM_EXCEPTION);
    }
}
