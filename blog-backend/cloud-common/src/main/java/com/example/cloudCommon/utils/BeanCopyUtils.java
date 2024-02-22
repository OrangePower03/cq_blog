package com.example.cloudCommon.utils;

import com.example.cloudCommon.domain.dto.BaseDto;
import com.example.cloudCommon.domain.eitity.BaseEntity;
import com.example.cloudCommon.domain.vo.BaseVo;
import org.springframework.beans.BeanUtils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 将entity对象转换为vo(value object)对象的工具类
 * 将dto对象转换为entity对象的工具类
 */
public class BeanCopyUtils {
    private BeanCopyUtils(){
    }

    public static<V extends BaseVo> V copyBean(BaseEntity source, Class<V> targetClass){
        V target = null;
        try {
            target = targetClass.getConstructor().newInstance();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        BeanUtils.copyProperties(source,target);
        return target;
    }

    public static<V extends BaseEntity> V copyBean(BaseDto source, Class<V> targetClass){
        V target = null;
        try {
            target = targetClass.getConstructor().newInstance();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        BeanUtils.copyProperties(source,target);
        return target;
    }

    public static<V extends BaseVo> List<V> copyBeans(Collection<? extends BaseEntity> sourceList, Class<V> targetClass){

        return sourceList.stream()
                .map(baseEntity -> copyBean(baseEntity, targetClass))
                .collect(Collectors.toList());
    }
}
