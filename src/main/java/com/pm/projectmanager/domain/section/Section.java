package com.pm.projectmanager.domain.section;

import com.pm.projectmanager.domain.project.Project;

import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Section {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Project project;

	@Builder
	public Section(String name, Project project) {
		this.name = name;
		this.project = project;
	}

	public void update(String name) {
		this.name = name;
	}
}
