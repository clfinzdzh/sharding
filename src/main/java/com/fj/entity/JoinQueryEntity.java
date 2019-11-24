package com.fj.entity;

import lombok.Data;

import java.util.List;

/**
 * @Desc 用作 VO
 * @Author caolifeng
 * @Date
 **/
@Data
public class JoinQueryEntity {

    private Integer equipId;

    private String equipName;

    private Integer equipType;

    private List<EquipParts> equipPartsList;

}
