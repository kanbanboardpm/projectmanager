package com.pm.projectmanager.domain.category.dto;

import com.pm.projectmanager.domain.category.Category;
import lombok.Getter;

@Getter
public class SelectCategoryResponseDto {
    private final Long id;
    private final String color;
    private final String name;
    private final String description;

    public SelectCategoryResponseDto(Category category) {
        this.color = category.getColor().getHexCode();
        this.name = category.getName();
        this.id = category.getId();
        this.description = category.getDescription();
    }
}
