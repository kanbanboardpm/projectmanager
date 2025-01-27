package com.pm.projectmanager.domain.category.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreateCategoryRequestDto {
    @NotBlank(message = "Required Category Color")
    private String color;
    @NotBlank(message = "Required Category Name")
    private String name;
    private String description;
    @NotBlank(message = "Required ProjectId")
    private String projectId;
}
