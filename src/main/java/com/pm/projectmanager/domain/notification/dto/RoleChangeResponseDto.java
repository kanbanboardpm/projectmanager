package com.pm.projectmanager.domain.notification.dto;

import com.pm.projectmanager.domain.authority.UserRole;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RoleChangeResponseDto {
	private Long userId;
	private UserRole role;

	public RoleChangeResponseDto(final Long userId, final UserRole role) {
		this.userId = userId;
		this.role = role;
	}
}
