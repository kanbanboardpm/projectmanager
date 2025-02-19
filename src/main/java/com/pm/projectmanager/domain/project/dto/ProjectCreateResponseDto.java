package com.pm.projectmanager.domain.project.dto;

import com.pm.projectmanager.domain.project.Project;

import lombok.Getter;

@Getter
public class ProjectCreateResponseDto {

	private Long id;

	public ProjectCreateResponseDto(Project project) {
		this.id = project.getId();
	}
}
