package com.pm.projectmanager.domain.project.dto;

import lombok.Getter;

@Getter
public class InviteCodeRequestDto {

	private Long projectId;
	private String projectName;
	private String nickname;

	public InviteCodeRequestDto(Long projectId, String projectName, String nickname) {
		this.projectId = projectId;
		this.projectName = projectName;
		this.nickname = nickname;
	}
}
