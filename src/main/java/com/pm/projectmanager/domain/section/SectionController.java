package com.pm.projectmanager.domain.section;

import static com.pm.projectmanager.common.response.ResponseCodeEnum.PROJECT_CREATE_SUCCESS;
import static com.pm.projectmanager.common.response.ResponseCodeEnum.SECTION_CREATE_SUCCESS;
import static com.pm.projectmanager.common.response.ResponseUtils.of;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pm.projectmanager.common.response.HttpResponseDto;
import com.pm.projectmanager.domain.section.dto.SectionCreateDto;
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
}
