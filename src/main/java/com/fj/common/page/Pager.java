package com.fj.common.page;


import com.fj.common.util.ParamCheckUtil;
import com.fj.common.util.RequestResponseUtil;
import lombok.Builder;
import lombok.Data;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;



@Data
@Builder
public class Pager {

    protected long page;

    protected long size;

    private long total;

    private Long pages;

    private List pageData;

    public static <T> Pager pager(long total, long pages, List<T> data) {
        HttpServletRequest request = RequestResponseUtil.getRequest();
        String page1 = request.getParameter("page");
        String size1 = request.getParameter("size");
        return Pager.builder()
                .page(StringUtils.isEmpty(page1) ? 1 : ParamCheckUtil.isIntNum(page1) ? Long.valueOf(page1) : 1L)
                .size(StringUtils.isEmpty(size1) ? 20 : ParamCheckUtil.isIntNum(size1) ? Long.valueOf(size1) : 20L)
                .total(total).pages(pages).pageData(data).build();
    }
}
