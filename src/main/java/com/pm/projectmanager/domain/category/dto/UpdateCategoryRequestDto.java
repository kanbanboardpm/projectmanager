package com.pm.projectmanager.domain.category.dto;

import com.pm.projectmanager.common.Color;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
public class UpdateCategoryRequestDto {
    private String name;
    private String description;
    private Color color;
}
