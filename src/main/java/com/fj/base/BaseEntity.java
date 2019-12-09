package com.fj.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * @Desc 实体基类
 * @Author
 * @Date 2019-08-07
 **/
@Data
public class BaseEntity implements Serializable {
    protected static final long serialVersionUID = 1L;

    @TableId(type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 创建时间
     */
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    @Length(max = 19,message = "长度最长为19个字符")
    private String createdAt;

    /**
     * 修改时间
     */
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    @Length(max = 19,message = "长度最长为19个字符")
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

    @TableField(value = "deleted")
    private int deleted;

}
