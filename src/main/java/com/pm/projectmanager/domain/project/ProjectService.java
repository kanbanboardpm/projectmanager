package com.pm.projectmanager.domain.project;

import org.springframework.stereotype.Service;

import com.pm.projectmanager.domain.authority.AuthorityRepository;
import com.pm.projectmanager.domain.authority.AuthorityService;
import com.pm.projectmanager.domain.project.dto.ProjectCreateRequestDto;
import com.pm.projectmanager.security.UserDetailsImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {

	private final ProjectRepository projectRepository;
	private final AuthorityService authorityService;

	@Transactional
	public void create(ProjectCreateRequestDto requestDto, UserDetailsImpl userDetails) {

		Project project = Project.builder()
			.name(requestDto.getName())
			.color(requestDto.getColor())
			.build();

		projectRepository.save(project);

		authorityService.create(project, userDetails.getUser());
	}
}
