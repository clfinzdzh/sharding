package com.fj.util;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * @Desc
 * @Author
 * @Date 2019-08-08
 **/
public class RequestResponseUtil {

    public static HttpServletRequest getRequest() {
        RequestAttributes attributes = RequestContextHolder.currentRequestAttributes();
        if (attributes instanceof ServletRequestAttributes) {
            return ((ServletRequestAttributes) attributes).getRequest();
        }
        return null;
    }

    public static HttpServletResponse getResponse() {
        RequestAttributes attributes = RequestContextHolder.currentRequestAttributes();
        if (attributes instanceof ServletRequestAttributes) {
            return ((ServletRequestAttributes) attributes).getResponse();
        }
        return null;
    }

    public static String getRequestBody() throws IOException {
        RequestAttributes attributes = RequestContextHolder.currentRequestAttributes();
        if (attributes instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes) attributes).getRequest();
            BufferedReader bufferedReader = request.getReader();
            String str, requestLine = "";
            while ((str = bufferedReader.readLine()) != null) {
                requestLine += str;
            }
            return requestLine;
        }
        return "";
    }
}
