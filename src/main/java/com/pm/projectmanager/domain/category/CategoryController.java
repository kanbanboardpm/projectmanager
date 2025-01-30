package com.pm.projectmanager.domain.category;

import com.pm.projectmanager.common.response.HttpResponseDto;
import com.pm.projectmanager.domain.category.dto.*;
import com.pm.projectmanager.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.pm.projectmanager.common.response.ResponseCodeEnum.*;
import static com.pm.projectmanager.common.response.ResponseUtils.of;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/categories")
    public ResponseEntity<HttpResponseDto> createCategory(
            @Valid @RequestBody CreateCategoryRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        categoryService.createCategory(requestDto, userDetails.getUser());
        return of(CATEGORY_CREATE_SUCCESS);
    }

    @GetMapping("/categories")
    public ResponseEntity<HttpResponseDto> selectAllCategory(
            @RequestBody SelectCategoryRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        List<SelectCategoryResponseDto> responseDto = categoryService.selectAllCategory(requestDto, userDetails.getUser());
        return of(CATEGORY_SELECT_SUCCESS, responseDto);
    }

    @PutMapping("/categories")
    public ResponseEntity<HttpResponseDto> updateCategory(
            @RequestBody UpdateCategoryRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        categoryService.updateCategory(requestDto, userDetails.getUser());
        return of(CATEGORY_UPDATE_SUCCESS);
    }

    @DeleteMapping("/categories")
    public ResponseEntity<HttpResponseDto> deleteCategory(
            @RequestBody DeleteCategoryRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        categoryService.deleteCategory(requestDto, userDetails.getUser());
        return of(CATEGORY_DELETE_SUCCESS);
    }

}