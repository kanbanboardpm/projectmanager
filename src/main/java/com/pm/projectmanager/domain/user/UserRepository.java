package com.pm.projectmanager.domain.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);

	boolean existsByEmail(String email);

	boolean existsByNickname(String nickname);

	List<User> findByEmailIn(List<String> emails);

	User findByKakaoId(Long kakaoId);
}

