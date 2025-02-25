package com.pm.projectmanager.common;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.pm.projectmanager.common.response.ResponseExceptionEnum;
import com.pm.projectmanager.exception.NotificationNotFoundException;
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

    public void commentNotifications(Long cardMasterUser, String cardMasterNickName, String projectName, String cardName, String nickName) {
        String key = "notification:" + cardMasterUser;
        String message = String.format("%s의 %s 카드에 %s님이 댓글을 달았습니다.", projectName, cardName, nickName);
        if(!Objects.equals(cardMasterNickName, nickName)) {
            redisTemplate.opsForList().rightPush(key, message);
            redisTemplate.expire(key, 3, TimeUnit.DAYS);
        }
    }

    public List<String> getCommentNotifications(Long cardMasterUser) {
        String key = "notification:" + cardMasterUser;
        List<String> userNotifications = redisTemplate.opsForList().range(key, 0, -1);
        if (userNotifications == null || userNotifications.isEmpty()) {
            throw new NotificationNotFoundException(ResponseExceptionEnum.NOTIFICATION_NOT_FOUND);
        }
        return userNotifications;
    }
}
