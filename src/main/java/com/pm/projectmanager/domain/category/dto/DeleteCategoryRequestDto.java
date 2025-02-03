package com.pm.projectmanager.domain.category.dto;

import lombok.Getter;

@Getter
public class DeleteCategoryRequestDto {
    private Long projectId;
    private Long categoryId;
}
