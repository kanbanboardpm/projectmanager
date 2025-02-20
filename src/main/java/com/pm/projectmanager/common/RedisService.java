package com.pm.projectmanager.common;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RedisService {

	private final RedisTemplate<String, String> redisTemplate;
	private final Long refreshTokenExpiration = 14 * 24 * 60 * 60 * 1000L; // 14일
	private final Long inviteExpiration = 3 * 24 * 60 * 60 * 1000L; // 3일

	@Transactional
	public void saveRefreshToken(String email, String refreshToken) {
		redisTemplate.opsForValue().set(email, refreshToken, refreshTokenExpiration, TimeUnit.MILLISECONDS);
	}

	public String getUsername(String username) {
		return redisTemplate.opsForValue().get(username);
	}

	public void invite(String email, Long projectId) {
		String stringProjectId = String.valueOf(projectId);
		redisTemplate.opsForSet().add(inviteKey(email), stringProjectId);
	}

	public boolean checkInvite(String email, Long projectId) {
		return redisTemplate.opsForSet().isMember(inviteKey(email), String.valueOf(projectId));
	}

	private String inviteKey(String email) {
		return "invite:" + email;
	}

	public void deleteRefreshToken(String email) {
		redisTemplate.delete(email);
	}

	public void deleteInvite(String email, Long projectId) {
		redisTemplate.opsForSet().remove((inviteKey(email)), String.valueOf(projectId));
	}
}
