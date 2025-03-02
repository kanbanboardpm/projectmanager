package com.pm.projectmanager.domain.notification;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
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

    public void updateCommentNotificationStatusChecked(Long cardMasterUserId, String notificationId) throws JsonProcessingException {
        redisService.updateCommentNotificationStatusChecked(cardMasterUserId, notificationId);
    }

    public int getUncheckNotificationCount(Long cardMasterUserId) throws JsonProcessingException {
        return redisService.getUncheckNotificationCount(cardMasterUserId);
    }
}
