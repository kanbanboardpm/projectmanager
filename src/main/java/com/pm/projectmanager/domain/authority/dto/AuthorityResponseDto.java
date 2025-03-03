package com.pm.projectmanager.domain.authority.dto;

import com.pm.projectmanager.domain.authority.UserRole;

import lombok.Getter;

@Getter
public class AuthorityResponseDto {

	private UserRole userRole;

	public AuthorityResponseDto(UserRole userRole) {
		this.userRole = userRole;
	}
}
