package com.pm.projectmanager.domain.category;

import com.pm.projectmanager.common.response.ResponseExceptionEnum;
import com.pm.projectmanager.domain.category.dto.CreateCategoryRequestDto;
import com.pm.projectmanager.domain.category.dto.SelectCategoryRequestDto;
import com.pm.projectmanager.domain.category.dto.SelectCategoryResponseDto;
import com.pm.projectmanager.domain.category.dto.UpdateCategoryRequestDto;
import com.pm.projectmanager.domain.project.Project;
import com.pm.projectmanager.domain.project.ProjectRepository;
import com.pm.projectmanager.exception.CategoryNameAlreadyExistsInProjectException;
import com.pm.projectmanager.exception.CategoryNotFoundException;
import com.pm.projectmanager.exception.ProjectNullException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProjectRepository projectRepository;

    // 카테고리 생성
    public void createCategory(CreateCategoryRequestDto requestDto) {
        Project project = findByProjectId(requestDto.getProjectId());
        try {
            Category category = Category.builder().name(requestDto.getName())
                    .color(requestDto.getColor())
                    .description(requestDto.getDescription())
                    .project(project).build();
            categoryRepository.save(category);
        } catch (DataIntegrityViolationException e) {
            throw new CategoryNameAlreadyExistsInProjectException(
                    ResponseExceptionEnum.CATEGORY_NAME_ALREADY_EXISTS_IN_PROJECT);
        }
    }

    public List<SelectCategoryResponseDto> selectAllCategory(SelectCategoryRequestDto requestDto) {
        hasProject(requestDto.getProjectId());
        return categoryRepository.findAll().stream().map(SelectCategoryResponseDto::new).toList();
    }

    @Transactional
    public void updateCategory(UpdateCategoryRequestDto requestDto) {
        Category category = categoryRepository.findById(requestDto.getCategoryId()).orElseThrow(
                () -> new CategoryNotFoundException(ResponseExceptionEnum.CATEGORY_NOT_FOUND));
        category.update(requestDto.getColor(), requestDto.getName(), requestDto.getDescription());
    }

    private Project findByProjectId(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNullException(ResponseExceptionEnum.PROJECT_NOT_FOUND));
    }

    private void hasProject(Long projectId) {
        if(projectRepository.findById(projectId).isEmpty()) {
            throw new ProjectNullException(ResponseExceptionEnum.PROJECT_NOT_FOUND);
        }
    }

}
