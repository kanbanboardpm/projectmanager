package com.pm.projectmanager.domain.authority;

import jakarta.persistence.*;

import com.pm.projectmanager.domain.project.Project;
import com.pm.projectmanager.domain.user.User;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "authorities",
	indexes = @Index(name = "idx_project_user", columnList = "project_id, user_id"))
public class Authority {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "project_id", nullable = false)
	private Project project;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private UserRole userRole;

	@Builder
	public Authority(User user, Project project, UserRole userRole) {
		this.user = user;
		this.project = project;
		this.userRole = userRole;
	}

	public void updateRole(UserRole newRole) {
		this.userRole = newRole;
	}
}
