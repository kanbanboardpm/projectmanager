package com.pm.projectmanager.domain.category;

import com.pm.projectmanager.common.response.ResponseExceptionEnum;
import com.pm.projectmanager.domain.authority.Authority;
import com.pm.projectmanager.domain.authority.AuthorityRepository;
import com.pm.projectmanager.domain.category.dto.*;
import com.pm.projectmanager.domain.project.Project;
import com.pm.projectmanager.domain.project.ProjectRepository;
import com.pm.projectmanager.domain.user.User;
import com.pm.projectmanager.exception.AuthorityNullException;
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
    private final AuthorityRepository authorityRepository;

    // 카테고리 생성
    public void createCategory(CreateCategoryRequestDto requestDto, User user) {
        Authority authority = findByProjectIdAndUserId(requestDto.getProjectId(), user.getId());
        try {
            Category category = Category.builder().name(requestDto.getName())
                    .color(requestDto.getColor())
                    .description(requestDto.getDescription())
                    .project(authority.getProject()).build();
            categoryRepository.save(category);
        } catch (DataIntegrityViolationException e) {
            throw new CategoryNameAlreadyExistsInProjectException(
                    ResponseExceptionEnum.CATEGORY_NAME_ALREADY_EXISTS_IN_PROJECT);
        }
    }

    // 카테고리 조회
    public List<SelectCategoryResponseDto> selectAllCategory(SelectCategoryRequestDto requestDto, User user) {
        hasProjectAndUser(requestDto.getProjectId(), user.getId());
        return categoryRepository.findAll().stream().map(SelectCategoryResponseDto::new).toList();
    }

    // 카테고리 수정
    @Transactional
    public void updateCategory(UpdateCategoryRequestDto requestDto, User user) {
        hasProjectAndUser(requestDto.getProjectId(), user.getId());
        Category category = categoryRepository.findById(requestDto.getCategoryId()).orElseThrow(
                () -> new CategoryNotFoundException(ResponseExceptionEnum.CATEGORY_NOT_FOUND));
        category.update(requestDto.getColor(), requestDto.getName(), requestDto.getDescription());
    }

    // 카테고리 삭제
    public void deleteCategory(DeleteCategoryRequestDto requestDto, User user) {
        hasProjectAndUser(requestDto.getProjectId(), user.getId());
        Category category = categoryRepository.findById(requestDto.getCategoryId()).orElseThrow(
                () -> new CategoryNotFoundException(ResponseExceptionEnum.CATEGORY_NOT_FOUND));
        categoryRepository.delete(category);
    }

    private Authority findByProjectIdAndUserId(Long projectId, Long userId) {
        Authority authority = authorityRepository.findByProjectIdAndUserId(projectId, userId);
        if (authority == null) {
            throw new AuthorityNullException(ResponseExceptionEnum.AUTHORITY_NULL_EXCEPTION);
        }
        return authority;
    }

    private void hasProjectAndUser(Long projectId, Long userId) {
        Authority authority = authorityRepository.findByProjectIdAndUserId(projectId, userId);
        if (authority == null) {
            throw new AuthorityNullException(ResponseExceptionEnum.AUTHORITY_NULL_EXCEPTION);
        }
    }

}
