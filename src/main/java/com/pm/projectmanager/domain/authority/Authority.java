package com.pm.projectmanager.domain.authority;

import com.pm.projectmanager.domain.project.Project;
import com.pm.projectmanager.domain.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "authorities")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Authority {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private User user;

	@ManyToOne
	private Project project;

	@Builder
	public Authority(User user, Project project) {
		this.user = user;
		this.project = project;
	}
}
