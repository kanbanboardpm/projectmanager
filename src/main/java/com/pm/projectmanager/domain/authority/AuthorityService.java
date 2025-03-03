package com.pm.projectmanager.domain.authority;

import org.springframework.stereotype.Service;

import com.pm.projectmanager.common.response.ResponseExceptionEnum;
import com.pm.projectmanager.domain.project.Project;
import com.pm.projectmanager.domain.user.User;
import com.pm.projectmanager.exception.AuthorityNullException;
import com.pm.projectmanager.exception.UserRoleException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorityService {

	private final AuthorityRepository authorityRepository;

	@Transactional
	public void create(Project project, User user, UserRole userRole) {

		Authority authority = Authority.builder()
			.project(project)
			.user(user)
			.userRole(userRole)
			.build();

		authorityRepository.save(authority);
	}

	public boolean adminCheck(Long projectId, Long userId) {
		UserRole role = getUserRole(projectId, userId);
		return role.equals(UserRole.ADMIN);
	}

	public UserRole getUserRole(Long projectId, Long userId) {
		Authority authority = authorityRepository.findByProjectIdAndUserId(projectId, userId);
		return authority.getUserRole();
	}
}
