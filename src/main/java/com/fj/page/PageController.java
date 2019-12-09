package com.fj.page;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @Desc 分页请求
 * @Author
 * @Date 2019-08-07
 **/
public class PageController<T> {

    protected Page<T> getPage(long page, long size) {
        //构建分页查询对象
        Page<T> pager = new Page<>();
        pager.setCurrent(page).setSize(size).setSearchCount(true);
        return pager;
    }
}
