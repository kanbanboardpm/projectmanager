package com.pm.projectmanager.domain.category;

import com.pm.projectmanager.domain.category.dto.CreateCategoryRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public void createCategory(CreateCategoryRequestDto requestDto) {
        Category category = Category.builder().name(requestDto.getName())
                        .color(requestDto.getColor())
                        .description(requestDto.getDescription()).build();
        categoryRepository.save(category);
    }
}
