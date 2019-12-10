package com.fj.common.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

@Data
public class BaseEntity implements Serializable {

    protected static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;
    /**
     * 创建时间
     */
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private String createdAt;
    /**
     * 修改时间
     */
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private String updatedAt;
    /**
     * 创建者
     */
    @TableField(value = "created_by", fill = FieldFill.INSERT)
    private String createdBy;
    /**
     * 修改者
     */
    @TableField(value = "updated_by", fill = FieldFill.INSERT_UPDATE)
    private String updatedBy;
    /**
     * -1:未启用 0:启用 1:删除
     */
    @TableField(value = "deleted")
    private int deleted;

}
