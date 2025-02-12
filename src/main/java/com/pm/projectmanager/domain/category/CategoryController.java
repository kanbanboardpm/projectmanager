package com.pm.projectmanager.domain.category;

import com.pm.projectmanager.common.response.HttpResponseDto;
import com.pm.projectmanager.domain.category.dto.DeleteCategoryRequestDto;
import com.pm.projectmanager.domain.category.dto.UpdateCategoryRequestDto;
import com.pm.projectmanager.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.pm.projectmanager.common.response.ResponseCodeEnum.CATEGORY_DELETE_SUCCESS;
import static com.pm.projectmanager.common.response.ResponseCodeEnum.CATEGORY_UPDATE_SUCCESS;
import static com.pm.projectmanager.common.response.ResponseUtils.of;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @PutMapping
    public ResponseEntity<HttpResponseDto> updateCategory(
            @RequestBody UpdateCategoryRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        categoryService.updateCategory(requestDto, userDetails.getUser());
        return of(CATEGORY_UPDATE_SUCCESS);
    }

    @DeleteMapping
    public ResponseEntity<HttpResponseDto> deleteCategory(
            @RequestBody DeleteCategoryRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        categoryService.deleteCategory(requestDto, userDetails.getUser());
        return of(CATEGORY_DELETE_SUCCESS);
    }
}
