package com.pm.projectmanager.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pm.projectmanager.domain.notification.CommentNotificationDto;
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
    private final ObjectMapper objectMapper;

	@Transactional
	public void saveRefreshToken(String email, String refreshToken) {
		redisTemplate.opsForValue().set(email, refreshToken, refreshTokenExpiration, TimeUnit.MILLISECONDS);
	}

	public String getUsername(String username) {
		return redisTemplate.opsForValue().get(username);
	}

	public void invite(String email, Long projectId) {
		redisTemplate.opsForList().rightPush(inviteKey(email), String.valueOf(projectId));
	}

	public boolean checkInvite(String email, Long projectId) {
		List<String> invites = redisTemplate.opsForList().range(inviteKey(email), 0, -1);
		return invites != null && invites.contains(String.valueOf(projectId));
	}

	public List<String> getInvites(String email) {
		return redisTemplate.opsForList().range(inviteKey(email), 0, -1);
	}

	private String inviteKey(String email) {
		return "invite:" + email;
	}

	public void deleteRefreshToken(String email) {
		redisTemplate.delete(email);
	}

	public void deleteInvite(String email, Long projectId) {
		redisTemplate.opsForList().remove(inviteKey(email), 1, String.valueOf(projectId));
	}

    public void commentNotifications(Long cardMasterUser,
                                     String cardMasterNickName,
                                     String projectName,
                                     String cardName,
                                     String nickName) throws JsonProcessingException
    {
        String key = "notification:" + cardMasterUser;

        // 현재 날짜와 시간 가져오기
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String createAt = now.format(formatter);  // "yyyy-MM-dd HH:mm:ss" 형식으로 포맷

        if (!Objects.equals(cardMasterNickName, nickName)) {
            // JSON 형태로 알림 데이터 저장
            CommentNotificationDto notification = new CommentNotificationDto(projectName, cardName, nickName, createAt, "uncheck");
            String messageJson = objectMapper.writeValueAsString(notification);

            redisTemplate.opsForList().rightPush(key, messageJson);
            redisTemplate.expire(key, 3, TimeUnit.DAYS);
        }
    }

    public List<CommentNotificationDto> getCommentNotifications(Long cardMasterUser) throws JsonProcessingException {
        String key = "notification:" + cardMasterUser;

        // Redis 리스트에서 전체 데이터 가져오기
        List<String> jsonList = redisTemplate.opsForList().range(key, 0, -1);

        // JSON 문자열을 Java 객체로 변환
        List<CommentNotificationDto> notifications = new ArrayList<>();
        if (jsonList != null) {
            for (String json : jsonList) {
                CommentNotificationDto notification = objectMapper.readValue(json, CommentNotificationDto.class);
                notifications.add(notification);
            }
        }
        return notifications;
    }

    public void updateCommentNotificationStatusChecked(Long cardMasterUserId, String notificationId) throws JsonProcessingException {
        String key = "notification:" + cardMasterUserId;

        // 현재 Redis에 저장된 모든 알림 가져오기
        List<String> jsonList = redisTemplate.opsForList().range(key, 0, -1);
        if (jsonList == null || jsonList.isEmpty()) {
            return;
        }

        List<String> updatedList = new ArrayList<>();

        for (String json : jsonList) {
            CommentNotificationDto notification = objectMapper.readValue(json, CommentNotificationDto.class);

            // 특정 notificationId가 일치하면 상태 변경
            if (notification.getNotificationId().equals(notificationId)) {
                notification.setStatus("check");  // ✅ 상태 변경
            }

            // JSON 다시 변환해서 리스트에 추가
            updatedList.add(objectMapper.writeValueAsString(notification));
        }

        // 기존 데이터 삭제 후 업데이트된 데이터 다시 저장
        redisTemplate.delete(key);
        redisTemplate.opsForList().rightPushAll(key, updatedList);
        redisTemplate.expire(key, 3, TimeUnit.DAYS); // 만료 시간 유지
    }

    public int getUncheckNotificationCount(Long cardMasterUserId) throws JsonProcessingException {
        String key = "notification:" + cardMasterUserId;

        // Redis 리스트에서 전체 데이터 가져오기
        List<String> jsonList = redisTemplate.opsForList().range(key, 0, -1);
        if (jsonList == null || jsonList.isEmpty()) {
            return 0; // 알림이 없으면 0 반환
        }

        // "uncheck" 상태인 알림 개수 세기
        int count = 0;
        for (String json : jsonList) {
            CommentNotificationDto notification = objectMapper.readValue(json, CommentNotificationDto.class);
            if ("uncheck".equals(notification.getStatus())) {
                count++;
            }
        }

        return count;
    }
}
