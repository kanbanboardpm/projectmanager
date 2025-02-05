package com.pm.projectmanager.domain.project.dto;

import com.pm.projectmanager.common.Color;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectCreateDto {

	private String name;
	private Color color;
}
