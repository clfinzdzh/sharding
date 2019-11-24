package com.fj.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_equip_parts")
public class EquipParts {

    private int equipPartsId;

    private int equipId;

    private String name;

    private int type;

}
