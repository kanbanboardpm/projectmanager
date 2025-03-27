package com.pm.projectmanager.domain.authority;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pm.projectmanager.domain.project.Project;
import com.pm.projectmanager.domain.user.User;

public interface AuthorityRepository extends JpaRepository<Authority, Long>, AuthorityRepositoryCustom {

	boolean existsByProjectIdAndUserId(Long projectId, Long userId);

	List<Authority> findByUserId(Long userId);

	Authority findByProjectIdAndUserId(Long projectId, Long userId);

	void deleteByUserId(Long id);

	List<Authority> findByProjectId(Long projectId);

	void deleteAllByProjectId(Long projectId);

	List<Authority> findByProjectIdAndUserIdIn(Long projectId, List<Long> userIds);

	List<Authority> findByProjectIdAndUserIn(Long projectId, List<User> users);
}
