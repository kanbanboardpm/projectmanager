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
@RequestMapping("/api/projects/{projectId}/categories")
public class ProjectCategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<HttpResponseDto> createCategory(
            @Valid @RequestBody CreateCategoryRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long projectId)
    {
        requestDto.setProjectId(projectId);
        categoryService.createCategory(requestDto, userDetails.getUser());
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

}