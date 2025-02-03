package com.pm.projectmanager.domain.section;

import static com.pm.projectmanager.common.response.ResponseUtils.of;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.pm.projectmanager.common.response.ResponseExceptionEnum;
import com.pm.projectmanager.domain.project.Project;
import com.pm.projectmanager.domain.project.ProjectRepository;
import com.pm.projectmanager.domain.section.dto.SectionCreateDto;
import com.pm.projectmanager.domain.section.dto.SectionResponseDto;
import com.pm.projectmanager.exception.ProjectNullException;
import com.pm.projectmanager.exception.SectionException;
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

		if (sectionRepository.existsByProjectIdAndName(projectId, requestDto.getName())) {
			throw new SectionException(ResponseExceptionEnum.SECTION_ALREADY_EXISTS);
		}

		Section section = Section.builder()
			.name(requestDto.getName())
			.project(project)
			.build();

		sectionRepository.save(section);

	}

	public SectionResponseDto get(Long sectionId, UserDetailsImpl userDetails) {

		Section section = sectionRepository.findById(sectionId)
			.orElseThrow(() -> new SectionException(ResponseExceptionEnum.SECTION_NOT_FOUND));

		return new SectionResponseDto(section);
	}

	public List<SectionResponseDto> getAll(Long projectId, UserDetailsImpl userDetails) {

		List<Section> sections = sectionRepository.findByProjectId(projectId);

		return sections.stream()
			.map(SectionResponseDto::new)
			.collect(Collectors.toList());
	}
}
