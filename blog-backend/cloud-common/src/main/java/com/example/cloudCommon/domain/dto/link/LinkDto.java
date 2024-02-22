package com.example.cloudCommon.domain.dto.link;

import com.example.cloudCommon.annotation.DtoNoRequest;
import com.example.cloudCommon.domain.dto.BaseDto;
import lombok.Data;

@Data
public class LinkDto implements BaseDto {
    @DtoNoRequest
    Long id;

    String status;
    String name;
    String logo;
    String description;
    String address;
}
