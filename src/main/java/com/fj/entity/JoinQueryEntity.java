package com.fj.entity;

import lombok.Data;

import java.util.List;

@Data
public class JoinQueryEntity {

    private Integer equipId;

    private String equipName;

    private Integer equipType;

    private List<EquipParts> equipPartsList;

}
