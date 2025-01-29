package com.pm.projectmanager.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

	boolean existsByEmail(String email);

	boolean existsByNickname(String nickname);
}

