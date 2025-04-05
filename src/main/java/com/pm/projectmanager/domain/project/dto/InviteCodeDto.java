package com.pm.projectmanager.domain.project.dto;

import lombok.Getter;

@Getter
public class InviteCodeDto {
	private String code;

	public InviteCodeDto(String code) {
		this.code = code;
	}
}
