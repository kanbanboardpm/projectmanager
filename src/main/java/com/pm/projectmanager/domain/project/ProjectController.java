package com.pm.projectmanager.domain.project;

import static com.pm.projectmanager.common.response.ResponseCodeEnum.PROJECT_CREATE_SUCCESS;
import static com.pm.projectmanager.common.response.ResponseCodeEnum.PROJECT_DELETE_SUCCESS;
import static com.pm.projectmanager.common.response.ResponseCodeEnum.PROJECT_GET_SUCCESS;
import static com.pm.projectmanager.common.response.ResponseCodeEnum.PROJECT_UPDATE_SUCCESS;
import static com.pm.projectmanager.common.response.ResponseCodeEnum.USER_SIGNUP_SUCCESS;
import static com.pm.projectmanager.common.response.ResponseUtils.of;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pm.projectmanager.common.response.HttpResponseDto;
import com.pm.projectmanager.domain.project.dto.ProjectCreateRequestDto;
import com.pm.projectmanager.domain.project.dto.ProjectResponseDto;
import com.pm.projectmanager.domain.project.dto.ProjectUpdateDto;
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

	@GetMapping("/{projectId}")
	public ResponseEntity<HttpResponseDto> get(
		@PathVariable Long projectId,
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		ProjectResponseDto responseDto = projectService.get(projectId, userDetails);
		return of(PROJECT_GET_SUCCESS, responseDto);
	}

	@GetMapping
	public ResponseEntity<HttpResponseDto> getAll(
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		List<ProjectResponseDto> responseDto = projectService.getAll(userDetails);
		return of(PROJECT_GET_SUCCESS, responseDto);
	}

	@PutMapping("/{projectId}")
	public ResponseEntity<HttpResponseDto> update(
		@RequestBody ProjectUpdateDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@PathVariable Long projectId
	) {
		projectService.update(requestDto, userDetails, projectId);
		return of(PROJECT_UPDATE_SUCCESS);
	}

	@DeleteMapping("/{projectId}")
	public ResponseEntity<HttpResponseDto> delete(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@PathVariable Long projectId
	) {
		projectService.delete(userDetails, projectId);
		return of(PROJECT_DELETE_SUCCESS);
	}
}
