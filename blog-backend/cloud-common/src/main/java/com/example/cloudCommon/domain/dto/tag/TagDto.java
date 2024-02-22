package com.example.cloudCommon.domain.dto.tag;

import com.example.cloudCommon.annotation.DtoNoRequest;
import com.example.cloudCommon.domain.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagDto implements BaseDto {
    @DtoNoRequest
    Long id;

    String name;
    String remark;
}
