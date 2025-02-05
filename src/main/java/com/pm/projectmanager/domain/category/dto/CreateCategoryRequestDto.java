package com.pm.projectmanager.domain.category.dto;

import com.pm.projectmanager.domain.project.Color;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateCategoryRequestDto {

    private Color color;
    @NotBlank(message = "Required Category Name")
    private String name;
    private String description;
    @NotNull
    @Positive
    private Long projectId;
}
