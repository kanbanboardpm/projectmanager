package com.pm.projectmanager.domain.authority;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

	boolean existsByProjectIdAndUserId(Long projectId, Long userId);
}
