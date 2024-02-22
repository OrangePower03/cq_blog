package com.example.cloudCommon.utils;


import com.example.cloudCommon.annotation.DtoNoRequest;
import com.example.cloudCommon.domain.dto.BaseDto;
import com.example.cloudCommon.enums.AppHttpCodeEnum;
import com.example.cloudCommon.exception.SystemException;

import java.lang.reflect.Field;
import java.util.Objects;

public class DtoVerifyUtils {
    private final BaseDto dto;

    private DtoVerifyUtils(BaseDto dto) {
        this.dto=dto;
    }

    private boolean isEmpty() {
        return Objects.isNull(dto);
    }

    private boolean allExist() {
        Class<? extends BaseDto> dtoClass = dto.getClass();
        Field[] fields = dtoClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                DtoNoRequest annotation = field.getAnnotation(DtoNoRequest.class);
                if(Objects.isNull(annotation)) {
                    Object attribute = field.get(dto);
                    if(Objects.isNull(attribute)) {
                        return false;
                    }
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }

    public static void verifyNonNull(BaseDto dto) {
        DtoVerifyUtils verifyObj = new DtoVerifyUtils(dto);
        if(verifyObj.isEmpty() || !verifyObj.allExist()) {
            throw new SystemException(AppHttpCodeEnum.DTO_NULL);
        }
    }
}
