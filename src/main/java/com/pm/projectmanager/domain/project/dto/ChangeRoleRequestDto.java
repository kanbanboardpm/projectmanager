package com.pm.projectmanager.domain.project.dto;

import com.pm.projectmanager.domain.authority.UserRole;

import lombok.Getter;

@Getter
public class ChangeRoleRequestDto {
	private UserRole role;
	private String email;
}
