package com.example.cloudCommon.domain.dto.category;

import com.example.cloudCommon.domain.dto.BaseDto;
import lombok.Data;

@Data
public class UpdateCategoryDto implements BaseDto {
    private Long id;
    private String name;
    private String description;
    private String status;
}
