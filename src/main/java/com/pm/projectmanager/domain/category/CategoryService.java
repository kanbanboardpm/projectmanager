package com.pm.projectmanager.domain.category;

import com.pm.projectmanager.common.response.ResponseExceptionEnum;
import com.pm.projectmanager.domain.authority.Authority;
import com.pm.projectmanager.domain.authority.AuthorityRepository;
import com.pm.projectmanager.domain.card.CardRepository;
import com.pm.projectmanager.domain.category.dto.*;
import com.pm.projectmanager.domain.user.User;
import com.pm.projectmanager.exception.AuthorityNullException;
import com.pm.projectmanager.exception.CannotDeleteCategoryWithRelatedCardsException;
import com.pm.projectmanager.exception.CategoryNameAlreadyExistsInProjectException;
import com.pm.projectmanager.exception.CategoryNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final AuthorityRepository authorityRepository;
    private final CardRepository cardRepository;

    // 카테고리 생성
    public void createCategory(CreateCategoryRequestDto requestDto, User user, Long projectId) {
        Authority authority = findByProjectIdAndUserId(projectId, user.getId());
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
    public List<SelectCategoryResponseDto> selectAllCategory(Long projectId, User user) {
        hasProjectAndUser(projectId, user.getId());
        return categoryRepository.findByProjectId(projectId).stream().map(SelectCategoryResponseDto::new).toList();
    }

    // 카테고리 수정
    @Transactional
    public void updateCategory(UpdateCategoryRequestDto requestDto, User user, Long categoryId, Long projectId) {
        hasProjectAndUser(projectId, user.getId());

        Optional<Category> existingCategory = categoryRepository.findByProjectIdAndName(projectId, requestDto.getName());
        if(existingCategory.isPresent()) {
            throw new CategoryNameAlreadyExistsInProjectException(
                    ResponseExceptionEnum.CATEGORY_NAME_ALREADY_EXISTS_IN_PROJECT);
        }
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new CategoryNotFoundException(ResponseExceptionEnum.CATEGORY_NOT_FOUND));
        category.update(requestDto.getColor(), requestDto.getName(), requestDto.getDescription());
    }

    // 카테고리 삭제
    public void deleteCategory(Long projectId, Long categoryId, User user) {
        hasProjectAndUser(projectId, user.getId());
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new CategoryNotFoundException(ResponseExceptionEnum.CATEGORY_NOT_FOUND));
        if(cardRepository.existsByCategoryId(categoryId)) {
            throw new CannotDeleteCategoryWithRelatedCardsException(
                    ResponseExceptionEnum.CATEGORY_DELETE_RELATED_CARDS_EXIST);
        }
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
