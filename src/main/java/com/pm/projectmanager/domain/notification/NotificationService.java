package com.pm.projectmanager.domain.notification;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.pm.projectmanager.common.RedisService;
import com.pm.projectmanager.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {

	private final RedisService redisService;

	public List<String> getInvites(UserDetailsImpl userDetails) {
		return redisService.getInvites(userDetails.getUsername());
	}
}
