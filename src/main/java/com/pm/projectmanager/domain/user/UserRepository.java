package com.pm.projectmanager.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);

	boolean existsByUsername(String username);

	boolean existsByNickname(String nickname);
}

