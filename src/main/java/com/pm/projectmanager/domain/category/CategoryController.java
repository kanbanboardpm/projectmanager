package com.pm.projectmanager.domain.category;

import com.pm.projectmanager.common.response.HttpResponseDto;
import com.pm.projectmanager.domain.category.dto.CreateCategoryRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.pm.projectmanager.common.response.ResponseCodeEnum.CATEGORY_CREATE_SUCCESS;
import static com.pm.projectmanager.common.response.ResponseUtils.of;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/categories")
    public ResponseEntity<HttpResponseDto> createCategory(@Valid @RequestBody CreateCategoryRequestDto requestDto) {
        categoryService.createCategory(requestDto);
        return of(CATEGORY_CREATE_SUCCESS);
    }
}