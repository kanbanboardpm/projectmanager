package com.pm.projectmanager.domain.authority;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.pm.projectmanager.domain.project.Project;
import com.pm.projectmanager.domain.user.User;
import com.pm.projectmanager.security.UserDetailsImpl;

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
}
