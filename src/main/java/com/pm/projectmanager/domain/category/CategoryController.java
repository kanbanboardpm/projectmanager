package com.pm.projectmanager.domain.category;

import com.pm.projectmanager.common.response.HttpResponseDto;
import com.pm.projectmanager.domain.category.dto.CreateCategoryRequestDto;
import com.pm.projectmanager.domain.category.dto.SelectCategoryRequestDto;
import com.pm.projectmanager.domain.category.dto.SelectCategoryResponseDto;
import com.pm.projectmanager.domain.category.dto.UpdateCategoryRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<HttpResponseDto> createCategory(@Valid @RequestBody CreateCategoryRequestDto requestDto) {
        categoryService.createCategory(requestDto);
        return of(CATEGORY_CREATE_SUCCESS);
    }

    @GetMapping("/categories")
    public ResponseEntity<HttpResponseDto> selectAllCategory(@RequestBody SelectCategoryRequestDto requestDto) {
        List<SelectCategoryResponseDto> responseDto = categoryService.selectAllCategory(requestDto);
        return of(CATEGORY_SELECT_SUCCESS, responseDto);
    }

    @PutMapping("/categories")
    public ResponseEntity<HttpResponseDto> updateCategory(@RequestBody UpdateCategoryRequestDto requestDto) {
        categoryService.updateCategory(requestDto);
        return of(CATEGORY_UPDATE_SUCCESS);
    }

}