package com.pm.projectmanager.domain.category.dto;

import com.pm.projectmanager.common.Color;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateCategoryRequestDto {

    private Color color;
    @NotBlank(message = "Required Category Name")
    private String name;
    private String description;
}
