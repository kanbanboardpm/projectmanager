package com.pm.projectmanager.domain.category;

import com.pm.projectmanager.common.response.ResponseExceptionEnum;
import com.pm.projectmanager.domain.category.dto.CreateCategoryRequestDto;
import com.pm.projectmanager.exception.CategoryNameAlreadyExistsInProjectException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public void createCategory(CreateCategoryRequestDto requestDto) {

        try {
            Category category = Category.builder().name(requestDto.getName())
                    .color(requestDto.getColor())
                    .description(requestDto.getDescription())
                    .projectId(requestDto.getProjectId()).build();
            categoryRepository.save(category);
        } catch (DataIntegrityViolationException e) {
            throw new CategoryNameAlreadyExistsInProjectException(
                    ResponseExceptionEnum.CATEGORY_NAME_ALREADY_EXISTS_IN_PROJECT);
        }

    }
}
