package com.pm.projectmanager.domain.project;

import com.pm.projectmanager.common.Color;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@Enumerated(EnumType.STRING)
	private Color color;

	@Builder
	public Project(String name, Color color) {
		this.name = name;
		this.color = color;
	}

	public void updateName(String name) {
		this.name = name;
	}

	public void updateColor(Color color) {
		this.color = color;
	}
}
