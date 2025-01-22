package com.pm.projectmanager.domain.project;

import org.springframework.stereotype.Service;

import com.pm.projectmanager.common.response.ResponseExceptionEnum;
import com.pm.projectmanager.domain.authority.AuthorityRepository;
import com.pm.projectmanager.domain.authority.AuthorityService;
import com.pm.projectmanager.domain.project.dto.ProjectCreateRequestDto;
import com.pm.projectmanager.domain.project.dto.ProjectResponseDto;
import com.pm.projectmanager.exception.AuthorityNullException;
import com.pm.projectmanager.exception.ProjectNullException;
import com.pm.projectmanager.security.UserDetailsImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {

	private final ProjectRepository projectRepository;
	private final AuthorityService authorityService;
	private final AuthorityRepository authorityRepository;

	@Transactional
	public void create(ProjectCreateRequestDto requestDto, UserDetailsImpl userDetails) {

		Project project = Project.builder()
			.name(requestDto.getName())
			.color(requestDto.getColor())
			.build();

		projectRepository.save(project);

		authorityService.create(project, userDetails.getUser());
	}

	public ProjectResponseDto get(Long projectId, UserDetailsImpl userDetails) {

		Project project = projectRepository.findById(projectId)
			.orElseThrow(() -> new ProjectNullException(ResponseExceptionEnum.PROJECT_NOT_FOUND));

		if (!authorityRepository.existsByProjectIdAndUserId(projectId, userDetails.getUser().getId())) {
			throw new AuthorityNullException(ResponseExceptionEnum.AUTHORITY_NULL_EXCEPTION);
		}

		return new ProjectResponseDto(project);
	}
}
