package com.pm.projectmanager.domain.project;

import static com.pm.projectmanager.common.response.ResponseCodeEnum.PROJECT_ACCEPT_SUCCESS;
import static com.pm.projectmanager.common.response.ResponseCodeEnum.PROJECT_CREATE_SUCCESS;
import static com.pm.projectmanager.common.response.ResponseCodeEnum.PROJECT_DELETE_SUCCESS;
import static com.pm.projectmanager.common.response.ResponseCodeEnum.PROJECT_GET_SUCCESS;
import static com.pm.projectmanager.common.response.ResponseCodeEnum.PROJECT_INVITE_GET_SUCCESS;
import static com.pm.projectmanager.common.response.ResponseCodeEnum.PROJECT_INVITE_SUCCESS;
import static com.pm.projectmanager.common.response.ResponseCodeEnum.PROJECT_REFUSE_SUCCESS;
import static com.pm.projectmanager.common.response.ResponseCodeEnum.PROJECT_UPDATE_SUCCESS;
import static com.pm.projectmanager.common.response.ResponseCodeEnum.PROJECT_USER_DELETE_SUCCESS;
import static com.pm.projectmanager.common.response.ResponseCodeEnum.PROJECT_USER_GET_SUCCESS;
import static com.pm.projectmanager.common.response.ResponseUtils.of;

import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pm.projectmanager.common.response.HttpResponseDto;
import com.pm.projectmanager.domain.project.dto.ProjectCreateDto;
import com.pm.projectmanager.domain.project.dto.ProjectCreateResponseDto;
import com.pm.projectmanager.domain.project.dto.ProjectInviteDto;
import com.pm.projectmanager.domain.project.dto.ProjectResponseDto;
import com.pm.projectmanager.domain.project.dto.ProjectUpdateDto;
import com.pm.projectmanager.domain.user.dto.UserResponseDto;
import com.pm.projectmanager.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects")
public class ProjectController {

	private final ProjectService projectService;

	@PostMapping
	public ResponseEntity<HttpResponseDto> create(
		@RequestBody ProjectCreateDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		ProjectCreateResponseDto responseDto = projectService.create(requestDto, userDetails);
		return of(PROJECT_CREATE_SUCCESS, responseDto);
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

	@PostMapping("/invites/{projectId}")
	public ResponseEntity<HttpResponseDto> invite(
		@RequestBody ProjectInviteDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@PathVariable Long projectId
	) {
		projectService.invite(requestDto, userDetails, projectId);
		return of(PROJECT_INVITE_SUCCESS);
	}

	@DeleteMapping("/{projectId}/{userId}")
	public ResponseEntity<HttpResponseDto> deleteUser(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@PathVariable Long userId,
		@PathVariable Long projectId
	) {
		projectService.deleteUser(projectId, userDetails, userId);
		return of(PROJECT_USER_DELETE_SUCCESS);
	}

	@PostMapping("/accept/{projectId}")
	public ResponseEntity<HttpResponseDto> accept(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@PathVariable Long projectId
	) {
		projectService.inviteAccept(projectId, userDetails);
		return of(PROJECT_ACCEPT_SUCCESS);
	}

	@PostMapping("/refuse/{projectId}")
	public ResponseEntity<HttpResponseDto> refuse(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@PathVariable Long projectId
	) {
		projectService.inviteRefuse(projectId, userDetails);
		return of(PROJECT_REFUSE_SUCCESS);
	}

	@GetMapping("/{projectId}/users")
	public ResponseEntity<HttpResponseDto> getUsers(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@PathVariable Long projectId
	) {
		List<UserResponseDto> responseDto = projectService.getUsers(userDetails, projectId);
		return of(PROJECT_USER_GET_SUCCESS, responseDto);
	}
}
