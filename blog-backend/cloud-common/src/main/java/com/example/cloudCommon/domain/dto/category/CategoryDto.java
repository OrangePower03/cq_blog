package com.example.cloudCommon.domain.dto.category;

import com.example.cloudCommon.domain.dto.BaseDto;
import lombok.Data;

@Data
public class CategoryDto implements BaseDto {
    String name;
    String description;
    String status;
}
