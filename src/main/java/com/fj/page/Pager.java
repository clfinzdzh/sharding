package com.fj.page;


import com.fj.util.ParamCheckUtil;
import com.fj.util.RequestResponseUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @Desc 分页对象
 * @Author
 * @Date 2019-08-07
 **/
@Data
@Accessors
@Builder
public class Pager<T> {

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
