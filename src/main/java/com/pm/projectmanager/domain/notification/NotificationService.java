package com.pm.projectmanager.domain.notification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.stereotype.Service;

import com.pm.projectmanager.common.RedisService;
import com.pm.projectmanager.common.response.ResponseExceptionEnum;
import com.pm.projectmanager.domain.notification.dto.InviteResponseDto;
import com.pm.projectmanager.domain.project.Project;
import com.pm.projectmanager.domain.project.ProjectService;
import com.pm.projectmanager.domain.project.dto.ProjectInviteResponseDto;
import com.pm.projectmanager.domain.project.dto.ProjectResponseDto;
import com.pm.projectmanager.domain.user.User;
import com.pm.projectmanager.domain.user.UserRepository;
import com.pm.projectmanager.domain.user.UserService;
import com.pm.projectmanager.exception.UserNotFoundException;
import com.pm.projectmanager.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {

	private final RedisService redisService;
	private final ProjectService projectService;
	private final UserRepository userRepository;

	public List<ProjectInviteResponseDto> getInvites(UserDetailsImpl userDetails) {
		List<String> ids = redisService.getInvites(userDetails.getUsername());

		List<Long> projectIds = new ArrayList<>();
		Map<Long, Long> projectUserMap = new HashMap<>(); // projectId -> userId 매핑 저장

		for (String id : ids) {
			String[] parts = id.split(":");
			Long projectId = Long.parseLong(parts[0]);
			Long userId = Long.parseLong(parts[1]);

			projectIds.add(projectId);
			projectUserMap.put(projectId, userId);
		}

		List<Project> projects = projectService.getProjects(projectIds);

		return projects.stream()
			.map(project -> {
				Long userId = projectUserMap.get(project.getId());
				User user = userRepository.findById(userId)
					.orElseThrow(() -> new UserNotFoundException(ResponseExceptionEnum.USER_NOT_FOUND));
				return new ProjectInviteResponseDto(project, user);
			})
			.collect(Collectors.toList());
	}

    public void deleteCommentNotification(Long cardMasterUserId, String notificationId) throws JsonProcessingException {
        redisService.deleteCommentNotification(cardMasterUserId, notificationId);
    }

	public void updateCommentNotificationStatusChecked(Long cardMasterUserId, String notificationId) throws JsonProcessingException {
		redisService.updateCommentNotificationStatusChecked(cardMasterUserId, notificationId);
	}

	public int getUncheckNotificationCount(Long cardMasterUserId) throws JsonProcessingException {
		return redisService.getUncheckNotificationCount(cardMasterUserId);
	}

}
