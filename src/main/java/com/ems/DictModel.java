package com.ems;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @program: ems
 * @description: 数据字典实体类
 * @author: Frank.li
 * @create: 2019-05-30 17:12
 */
@Data
public class DictModel implements java.io.Serializable {
    @Excel(name  = "编码", orderNum  = "0")
    private String code;

    @Excel(name  = "内容", orderNum  = "1")
    private String content;

    @Excel(name  = "字典类型", orderNum  = "2")
    private String type;

    @Excel(name  = "EN", orderNum  = "3")
    private String enName;

    @Excel(name  = "父级编码", orderNum  = "4")
    private String praCode;

    @Excel(name  = "备注", orderNum  = "5")
    private String mark;

    @Excel(name  = "排序", orderNum  = "6")
    private String sort;
}

