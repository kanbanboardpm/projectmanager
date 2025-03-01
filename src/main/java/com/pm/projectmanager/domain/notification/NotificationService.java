package com.pm.projectmanager.domain.notification;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
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

    public void updateCommentNotificationStatusChecked(Long cardMasterUserId, String notificationId) throws JsonProcessingException {
        redisService.updateCommentNotificationStatusChecked(cardMasterUserId, notificationId);
    }

    public int getUncheckNotificationCount(Long cardMasterUserId) throws JsonProcessingException {
        return redisService.getUncheckNotificationCount(cardMasterUserId);
    }
}
