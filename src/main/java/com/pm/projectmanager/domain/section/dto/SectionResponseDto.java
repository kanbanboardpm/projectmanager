package com.pm.projectmanager.domain.section.dto;

import com.pm.projectmanager.domain.section.Section;

import lombok.Getter;

@Getter
public class SectionResponseDto {
	private Long id;
	private String name;

	public SectionResponseDto(Section section) {
		this.id = section.getId();
		this.name = section.getName();
	}
}
