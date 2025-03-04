package com.pm.projectmanager.domain.project.dto;

import com.pm.projectmanager.common.Color;
import com.pm.projectmanager.domain.project.Project;
import com.pm.projectmanager.domain.user.User;

import lombok.Getter;

@Getter
public class ProjectInviteResponseDto {

	private Long id;
	private String name;
	private Color color;
	private String nickname;

	public ProjectInviteResponseDto(Project project, User user) {
		this.id = project.getId();
		this.name = project.getName();
		this.color = project.getColor();
		this.nickname = user.getNickname();
	}
}
