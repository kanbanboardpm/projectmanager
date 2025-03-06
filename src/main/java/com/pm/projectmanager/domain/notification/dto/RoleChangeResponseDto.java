package com.pm.projectmanager.domain.notification.dto;

import com.pm.projectmanager.domain.authority.UserRole;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RoleChangeResponseDto {
	private Long projectId;
	private UserRole role;

	public RoleChangeResponseDto(final Long projectId, final UserRole role) {
		this.projectId = projectId;
		this.role = role;
	}
}
