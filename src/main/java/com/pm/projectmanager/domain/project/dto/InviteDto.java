package com.pm.projectmanager.domain.project.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InviteDto {

	private Long projectId;
	private Long userId;

	public InviteDto(Long projectId, Long userId) {
		this.projectId = projectId;
		this.userId = userId;
	}
}
