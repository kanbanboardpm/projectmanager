package com.pm.projectmanager.domain.project.dto;

import lombok.Getter;

@Getter
public class InviteCodeResponseDto {

	private Long projectId;
	private String projectName;
	private String nickname;

	public InviteCodeResponseDto(Long projectId, String projectName, String nickname) {
		this.projectId = projectId;
		this.projectName = projectName;
		this.nickname = nickname;
	}
}
