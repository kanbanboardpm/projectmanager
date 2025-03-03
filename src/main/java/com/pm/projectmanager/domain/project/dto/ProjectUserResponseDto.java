package com.pm.projectmanager.domain.project.dto;

import com.pm.projectmanager.domain.authority.Authority;
import com.pm.projectmanager.domain.authority.UserRole;
import com.pm.projectmanager.domain.project.Project;
import com.pm.projectmanager.domain.user.User;

import lombok.Getter;

@Getter
public class ProjectUserResponseDto {

	private Long id;
	private String email;
	private String nickname;
	private String image_url;
	private UserRole role;

	public ProjectUserResponseDto(User user, Authority authority) {
		this.id = user.getId();
		this.email = user.getEmail();
		this.nickname = user.getNickname();
		this.image_url = user.getPhotoUrl();
		this.role = authority.getUserRole();
	}
}
