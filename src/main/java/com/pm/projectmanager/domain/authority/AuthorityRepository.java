package com.pm.projectmanager.domain.authority;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pm.projectmanager.domain.project.Project;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

	boolean existsByProjectIdAndUserId(Long projectId, Long userId);

	List<Authority> findByUserId(Long userId);

	Authority findByProjectIdAndUserId(Long projectId, Long userId);
}
