package com.pm.projectmanager.domain.notification.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InviteResponseDto {
	private String projectId;

	public InviteResponseDto(String projectId) {
		this.projectId = projectId;
	}

}
