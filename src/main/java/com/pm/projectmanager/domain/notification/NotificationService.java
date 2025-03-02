package com.pm.projectmanager.domain.notification;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.pm.projectmanager.common.RedisService;
import com.pm.projectmanager.domain.notification.dto.InviteResponseDto;
import com.pm.projectmanager.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {

	private final RedisService redisService;

	public List<InviteResponseDto> getInvites(UserDetailsImpl userDetails) {

		return redisService.getInvites(userDetails.getUsername())
			.stream()
			.map(InviteResponseDto::new)
			.collect(Collectors.toList());
	}
}
