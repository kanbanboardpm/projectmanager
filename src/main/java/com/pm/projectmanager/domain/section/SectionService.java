package com.pm.projectmanager.domain.section;

import static com.pm.projectmanager.common.response.ResponseCodeEnum.PROJECT_CREATE_SUCCESS;
import static com.pm.projectmanager.common.response.ResponseUtils.of;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.pm.projectmanager.common.response.HttpResponseDto;
import com.pm.projectmanager.common.response.ResponseExceptionEnum;
import com.pm.projectmanager.domain.project.Project;
import com.pm.projectmanager.domain.project.ProjectRepository;
import com.pm.projectmanager.domain.project.dto.ProjectCreateDto;
import com.pm.projectmanager.domain.section.dto.SectionCreateDto;
import com.pm.projectmanager.exception.ProjectNullException;
import com.pm.projectmanager.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SectionService {

	private final SectionRepository sectionRepository;
	private final ProjectRepository projectRepository;

	public void create(Long projectId, SectionCreateDto requestDto, UserDetailsImpl userDetails) {

		Project project = projectRepository.findById(projectId)
			.orElseThrow(() -> new ProjectNullException(ResponseExceptionEnum.PROJECT_NOT_FOUND));

		Section section = Section.builder()
			.name(requestDto.getName())
			.project(project)
			.build();

		sectionRepository.save(section);

	}
}
