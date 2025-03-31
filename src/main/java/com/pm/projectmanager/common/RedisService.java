package com.pm.projectmanager.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pm.projectmanager.domain.authority.UserRole;
import com.pm.projectmanager.common.response.ResponseExceptionEnum;
import com.pm.projectmanager.domain.comment.CommentRepository;
import com.pm.projectmanager.domain.notification.CommentNotificationDto;
import com.pm.projectmanager.domain.notification.NotificationService;
import com.pm.projectmanager.domain.notification.dto.InviteResponseDto;
import com.pm.projectmanager.domain.notification.dto.RoleChangeResponseDto;
import com.pm.projectmanager.domain.project.Project;
import com.pm.projectmanager.domain.project.ProjectRepository;
import com.pm.projectmanager.domain.project.dto.InviteDto;
import com.pm.projectmanager.domain.project.dto.ProjectInviteResponseDto;
import com.pm.projectmanager.domain.user.User;
import com.pm.projectmanager.domain.user.UserRepository;

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
	private final ObjectMapper objectMapper;
	private final ProjectRepository projectRepository;
	private final UserRepository userRepository;

	@Transactional
	public void saveRefreshToken(String email, String refreshToken) {
		redisTemplate.opsForValue().set(email, refreshToken, refreshTokenExpiration, TimeUnit.MILLISECONDS);
	}

	public String getUsername(String username) {
		return redisTemplate.opsForValue().get(username);
	}

	public boolean checkInvite(String email, Long projectId) {
		List<String> inviteJsonList = redisTemplate.opsForList().range(inviteKey(email), 0, -1);

		if (inviteJsonList == null || inviteJsonList.isEmpty()) {
			return false;
		}

		ObjectMapper objectMapper = new ObjectMapper();

		for (String inviteJson : inviteJsonList) {
			try {
				InviteDto invite = objectMapper.readValue(inviteJson, InviteDto.class);
				if (invite.getProjectId().equals(projectId)) {
					return true;
				}
			} catch (JsonProcessingException e) {
				throw new RuntimeException("Redis 초대 데이터 파싱 오류", e);
			}
		}

		return false;
	}

	public List<ProjectInviteResponseDto> getInvites(String email) {
		List<ProjectInviteResponseDto> responseDtos = new ArrayList<>();
		ObjectMapper objectMapper = new ObjectMapper();
		List<String> invitesString = redisTemplate.opsForList().range(inviteKey(email), 0, -1);
		for (String inviteJson : invitesString) {
			try {
				InviteDto invite = objectMapper.readValue(inviteJson, InviteDto.class);
				Project project = projectRepository.findById(invite.getProjectId()).orElse(null);
				User user = userRepository.findById(invite.getUserId()).orElse(null);
				if (project != null && user != null) {
					responseDtos.add(new ProjectInviteResponseDto(project, user));
				}
			} catch (JsonProcessingException e) {
				throw new RuntimeException(e);
			}
		}
		return responseDtos;
	}

	private String inviteKey(String email) {
		return "invite:" + email;
	}

	public void deleteRefreshToken(String email) {
		redisTemplate.delete(email);
	}

	public void deleteInvite(String email, Long projectId) {
		List<String> inviteJsonList = redisTemplate.opsForList().range(inviteKey(email), 0, -1);

		if (inviteJsonList == null || inviteJsonList.isEmpty()) {
			return;
		}

		ObjectMapper objectMapper = new ObjectMapper();

		for (String inviteJson : inviteJsonList) {
			try {
				InviteDto invite = objectMapper.readValue(inviteJson, InviteDto.class);
				if (invite.getProjectId().equals(projectId)) {
					redisTemplate.opsForList().remove(inviteKey(email), 1, inviteJson);
					return;
				}
			} catch (JsonProcessingException e) {
				throw new RuntimeException("Redis 초대 확인 오류", e);
			}
		}
	}

    public void commentNotifications(Long userId,
                                     String cardMasterNickName,
                                     String projectName,
                                     String cardName,
                                     String nickName,
                                     String content) throws JsonProcessingException
    {
        String key = "notification:" + userId;

		// 현재 날짜와 시간 가져오기
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String createAt = now.format(formatter);  // "yyyy-MM-dd HH:mm:ss" 형식으로 포맷

        if (!Objects.equals(cardMasterNickName, nickName)) {
            // JSON 형태로 알림 데이터 저장
            CommentNotificationDto notification = new CommentNotificationDto(projectName, cardName, nickName, createAt, content, "uncheck");
            String messageJson = objectMapper.writeValueAsString(notification);

            redisTemplate.opsForList().leftPush(key, messageJson);
            redisTemplate.expire(key, 3, TimeUnit.DAYS);
        }
    }

    public List<CommentNotificationDto> getCommentNotifications(Long cardMasterUser) throws JsonProcessingException {
        String key = "notification:" + cardMasterUser;
		clearNotificationCount(cardMasterUser);

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
		clearNotificationCount(cardMasterUser);
        return notifications;
    }

    public void deleteCommentNotification(Long cardMasterUserId, String notificationId) throws JsonProcessingException {
        String key = "notification:" + cardMasterUserId;

        // Redis에서 모든 알림 가져오기
        List<String> notifications = redisTemplate.opsForList().range(key, 0, -1);

        if (notifications == null || notifications.isEmpty()) {
            throw new NotificationNotFoundException(ResponseExceptionEnum.NOTIFICATION_NOT_FOUND);
        }

        // 특정 notificationId를 가진 알림 찾기
        for (String json : notifications) {
            CommentNotificationDto notification = objectMapper.readValue(json, CommentNotificationDto.class);

            if (notificationId.equals(notification.getNotificationId())) {
                // 해당 알림을 삭제 (LREM 사용)
                redisTemplate.opsForList().remove(key, 1, json);
                break;
            }
        }

    }

    public void updateCommentNotificationStatusChecked(Long userId, String notificationId) throws JsonProcessingException {
        String key = "notification:" + userId;

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

	public void saveInvite(String email, String inviteJson) {
		redisTemplate.opsForList().rightPush(inviteKey(email), inviteJson);
	}

	public void roleChangeNotifications(Long userId, UserRole role, Long projectId) {
		String key = "roleNotification:" + userId;

		RoleChangeResponseDto dto = new RoleChangeResponseDto(projectId, role);
		try {
			String roleChangeJson = objectMapper.writeValueAsString(dto);
			redisTemplate.opsForList().rightPush(key, roleChangeJson);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	public List<RoleChangeResponseDto> getRoleChangeNotifications(Long userId) {

		List<String> jsonList = redisTemplate.opsForList().range("roleNotification:" + userId, 0, -1);
		List<RoleChangeResponseDto> roleChangeList = new ArrayList<>();
		for (String json : jsonList) {
			try {
				RoleChangeResponseDto dto = objectMapper.readValue(json, RoleChangeResponseDto.class);
				roleChangeList.add(dto);
			} catch (JsonProcessingException e) {
				throw new RuntimeException(e);
			}
		}
		return roleChangeList;
	}

	// 댓글이 달릴 때
	public void increaseNotificationCount(Long userId) {
		String key = "notificationCount:" + userId;

		String value = redisTemplate.opsForValue().get(key);
		int intValue = 0;
		try {
			intValue = Integer.parseInt(value);
		} catch (NumberFormatException e) {
			intValue = 0;
		}
		intValue++;
		redisTemplate.opsForValue().set(key, String.valueOf(intValue));
	}

	// 알림페이지를 들어가서 카운트가 없어질 때
	private void clearNotificationCount(Long userId) {
		String key = "notificationCount:" + userId;
		redisTemplate.delete(key);
	}

	// 사이드탭에서 알림 카운트를 가져올 때
	public int getNotificationCount(Long userId) {
		String key = "notificationCount:" + userId;
		String count = redisTemplate.opsForValue().get(key);
		if (count != null) {
			return Integer.parseInt(count);
		} else {
			return 0;
		}

	}

	public void deleteRoleChangeNotification(User user, RoleChangeResponseDto responseDto) {

		redisTemplate.opsForList().remove("roleNotification:" + user.getId(), 0, responseDto.getRole());


	}
}
