package com.pm.projectmanager.domain.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class CreateCategoryRequestDto {

    @NotBlank(message = "Required Category Color")
    private String color;
    @NotBlank(message = "Required Category Name")
    private String name;
    private String description;
    @NotNull
    @Positive
    private Long projectId;
}
