package com.fj.common.page;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public class PageController<T> {

    protected Page<T> getPage(long page, long size) {
        //构建分页查询对象
        Page<T> pager = new Page<>();
        pager.setCurrent(page).setSize(size).setSearchCount(true);
        return pager;
    }
}
