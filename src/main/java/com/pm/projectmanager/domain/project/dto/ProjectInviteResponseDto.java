package com.pm.projectmanager.domain.project.dto;

import com.pm.projectmanager.common.Color;

import lombok.Getter;

@Getter
public class ProjectInviteResponseDto {

	private Long projectId;
	private String name;
	private Color color;
}
