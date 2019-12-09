package com.fj.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_equip")
public class Equip {

    private  Long equipId;

    private String name;

    private int type;

}
