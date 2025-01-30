package com.pm.projectmanager.domain.category.dto;

import com.pm.projectmanager.domain.project.Color;
import lombok.Getter;

@Getter
public class UpdateCategoryRequestDto {
    private Long projectId;
    private Long categoryId;
    private String name;
    private String description;
    private Color color;
}
