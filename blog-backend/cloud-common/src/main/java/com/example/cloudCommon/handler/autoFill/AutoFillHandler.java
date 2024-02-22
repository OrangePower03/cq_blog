package com.example.cloudCommon.handler.autoFill;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.example.cloudCommon.utils.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AutoFillHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Date date=new Date();
        setFieldValByName("createTime",date,metaObject);
        setFieldValByName("updateTime",date,metaObject);
        if(SecurityUtils.isAnonymous()) {
            setFieldValByName("createBy",-1L,metaObject);
            setFieldValByName("updateBy",-1L,metaObject);
            return ;
        }
        setFieldValByName("createBy",SecurityUtils.getUserId(),metaObject);
        setFieldValByName("updateBy",SecurityUtils.getUserId(),metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Date date=new Date();
        setFieldValByName("updateTime",date,metaObject);
        if(SecurityUtils.isInternal()) {
            setFieldValByName("updateBy",1L,metaObject);
            return ;
        }
        setFieldValByName("updateBy",SecurityUtils.getUserId(),metaObject);
    }
}