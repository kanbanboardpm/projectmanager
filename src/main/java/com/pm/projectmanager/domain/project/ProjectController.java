package com.pm.projectmanager.domain.project;

import static com.pm.projectmanager.common.response.ResponseCodeEnum.PROJECT_CREATE_SUCCESS;
import static com.pm.projectmanager.common.response.ResponseCodeEnum.PROJECT_GET_SUCCESS;
import static com.pm.projectmanager.common.response.ResponseCodeEnum.USER_SIGNUP_SUCCESS;
import static com.pm.projectmanager.common.response.ResponseUtils.of;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pm.projectmanager.common.response.HttpResponseDto;
import com.pm.projectmanager.domain.project.dto.ProjectCreateRequestDto;
import com.pm.projectmanager.domain.project.dto.ProjectResponseDto;
import com.pm.projectmanager.domain.user.dto.SignupRequestDto;
import com.pm.projectmanager.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects")
public class ProjectController {

	private final ProjectService projectService;

	@PostMapping
	public ResponseEntity<HttpResponseDto> create(
		@RequestBody ProjectCreateRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		projectService.create(requestDto, userDetails);
		return of(PROJECT_CREATE_SUCCESS);
	}

	@GetMapping
	public ResponseEntity<HttpResponseDto> get(
		@RequestParam Long projectId,
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		ProjectResponseDto responseDto = projectService.get(projectId, userDetails);
		return of(PROJECT_GET_SUCCESS, responseDto);
	}
}
