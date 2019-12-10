package com.fj.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * @Desc 自动填充
 * @Author clf
 **/
@Slf4j
@Component
public class AUTOFilleDataHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        String loginName = "";
        try {
            loginName = "用户名称";
        } catch (Exception e) {

        }
        String now = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
        setDefaultValue(metaObject, "createdAt", now);
        setDefaultValue(metaObject, "updatedAt", now);
        setDefaultValue(metaObject, "createdBy", loginName);
        setDefaultValue(metaObject, "updatedBy", loginName);
    }

    private void setDefaultValue(MetaObject metaObject, String fieldName, String newValue) {
        try {
            if (Objects.nonNull(newValue)) {
                if (metaObject.hasSetter(fieldName) && metaObject.hasGetter(fieldName)) {
                    setByName(metaObject, fieldName, newValue);
                } else if (metaObject.hasGetter(Constants.ENTITY)) {
                    Object et = metaObject.getValue(Constants.ENTITY);
                    if (et != null) {
                        MetaObject etMeta = SystemMetaObject.forObject(et);
                        if (etMeta.hasSetter(fieldName)) {
                            setByName(etMeta, fieldName, newValue);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.warn("通用字段自动填充出现异常：" + e);
        }
    }

    private void setByName(MetaObject metaObject, String fieldName, String newValue) {
        Object obj = metaObject.getValue(fieldName);
        if (obj == null) {
            metaObject.setValue(fieldName, newValue);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        String loginName = "";
        try {
            loginName = "用户名称";
        } catch (Exception e) {
        }
        setDefaultValue(metaObject, "updatedAt", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
        setDefaultValue(metaObject, "updatedBy", loginName);
    }
}
