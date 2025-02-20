package com.pm.projectmanager.domain.category;

import com.pm.projectmanager.common.response.HttpResponseDto;
import com.pm.projectmanager.domain.category.dto.CreateCategoryRequestDto;
import com.pm.projectmanager.domain.category.dto.SelectCategoryResponseDto;
import com.pm.projectmanager.domain.category.dto.UpdateCategoryRequestDto;
import com.pm.projectmanager.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.pm.projectmanager.common.response.ResponseCodeEnum.*;
import static com.pm.projectmanager.common.response.ResponseCodeEnum.CATEGORY_SELECT_SUCCESS;
import static com.pm.projectmanager.common.response.ResponseUtils.of;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects/{projectId}/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<HttpResponseDto> createCategory(
            @Valid @RequestBody CreateCategoryRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long projectId)
    {
        categoryService.createCategory(requestDto, userDetails.getUser(), projectId);
        return of(CATEGORY_CREATE_SUCCESS);
    }

    @GetMapping
    public ResponseEntity<HttpResponseDto> selectAllCategory(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long projectId)
    {

        List<SelectCategoryResponseDto> responseDto = categoryService.selectAllCategory(projectId, userDetails.getUser());
        return of(CATEGORY_SELECT_SUCCESS, responseDto);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<HttpResponseDto> updateCategory(
            @RequestBody UpdateCategoryRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long categoryId,
            @PathVariable Long projectId)
    {
        categoryService.updateCategory(requestDto, userDetails.getUser(), categoryId, projectId);
        return of(CATEGORY_UPDATE_SUCCESS);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<HttpResponseDto> deleteCategory(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long projectId,
            @PathVariable Long categoryId)
    {
        categoryService.deleteCategory(projectId, categoryId, userDetails.getUser());
        return of(CATEGORY_DELETE_SUCCESS);
    }
}
