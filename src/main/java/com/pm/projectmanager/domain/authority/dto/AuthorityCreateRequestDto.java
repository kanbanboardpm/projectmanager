package com.pm.projectmanager.domain.authority.dto;

import com.pm.projectmanager.domain.project.Project;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorityCreateRequestDto {

	private Project project;
}
