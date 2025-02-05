package com.pm.projectmanager.domain.section;

import static com.pm.projectmanager.common.response.ResponseCodeEnum.PROJECT_CREATE_SUCCESS;
import static com.pm.projectmanager.common.response.ResponseCodeEnum.SECTION_CREATE_SUCCESS;
import static com.pm.projectmanager.common.response.ResponseCodeEnum.SECTION_DELETE_SUCCESS;
import static com.pm.projectmanager.common.response.ResponseCodeEnum.SECTION_GET_SUCCESS;
import static com.pm.projectmanager.common.response.ResponseCodeEnum.SECTION_UPDATE_SUCCESS;
import static com.pm.projectmanager.common.response.ResponseUtils.of;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pm.projectmanager.common.response.HttpResponseDto;
import com.pm.projectmanager.domain.section.dto.SectionCreateDto;
import com.pm.projectmanager.domain.section.dto.SectionResponseDto;
import com.pm.projectmanager.domain.section.dto.SectionUpdateDto;
import com.pm.projectmanager.security.UserDetailsImpl;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects/{projectId}/sections")
public class SectionController {

	private final SectionService sectionService;

	@PostMapping
	public ResponseEntity<HttpResponseDto> create(
		@PathVariable Long projectId,
		@RequestBody SectionCreateDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		sectionService.create(projectId, requestDto, userDetails);
		return of(SECTION_CREATE_SUCCESS);
	}

	@GetMapping("/{sectionId}")
	public ResponseEntity<HttpResponseDto> get(
		@PathVariable Long projectId,
		@PathVariable Long sectionId,
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		SectionResponseDto response = sectionService.get(projectId, sectionId, userDetails);
		return of(SECTION_GET_SUCCESS, response);
	}

	@GetMapping()
	public ResponseEntity<HttpResponseDto> getAll(
		@PathVariable Long projectId,
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		List<SectionResponseDto> response = sectionService.getAll(projectId, userDetails);
		return of(SECTION_GET_SUCCESS, response);
	}

	@PutMapping("/{sectionId}")
	public ResponseEntity<HttpResponseDto> update(
		@PathVariable Long projectId,
		@PathVariable Long sectionId,
		@RequestBody SectionUpdateDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		sectionService.update(projectId, sectionId, requestDto, userDetails);
		return of(SECTION_UPDATE_SUCCESS);
	}

	@DeleteMapping("/{sectionId}")
	public ResponseEntity<HttpResponseDto> delete(
		@PathVariable Long projectId,
		@PathVariable Long sectionId,
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		sectionService.delete(projectId, sectionId, userDetails);
		return of(SECTION_DELETE_SUCCESS);
	}
}
