package com.pm.projectmanager.domain.authority;

import javax.persistence.Entity;

import com.pm.projectmanager.domain.project.Project;
import com.pm.projectmanager.domain.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "authorities")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
