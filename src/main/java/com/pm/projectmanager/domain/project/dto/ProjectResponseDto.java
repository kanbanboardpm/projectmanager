package com.pm.projectmanager.domain.project.dto;

import com.pm.projectmanager.domain.project.Color;
import com.pm.projectmanager.domain.project.Project;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProjectResponseDto {
	private Long id;
	private String name;
	private Color color;

	public ProjectResponseDto(Project project) {
		this.id = project.getId();
		this.name = project.getName();
		this.color = project.getColor();
	}
}
