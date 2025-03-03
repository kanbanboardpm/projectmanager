package com.pm.projectmanager.domain.notification;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import com.pm.projectmanager.common.RedisService;
import com.pm.projectmanager.domain.notification.dto.InviteResponseDto;
import com.pm.projectmanager.domain.project.Project;
import com.pm.projectmanager.domain.project.ProjectService;
import com.pm.projectmanager.domain.project.dto.ProjectResponseDto;
import com.pm.projectmanager.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {

	private final RedisService redisService;
	private final ProjectService projectService;

	public List<ProjectResponseDto> getInvites(UserDetailsImpl userDetails) {


		List<Long> ids = redisService.getInvites(userDetails.getUsername())
			.stream().map(Long::parseLong).collect(Collectors.toList());

		List<Project> projects = projectService.getProjects(ids);
		return projects.stream().map(project -> new ProjectResponseDto(project)).collect(Collectors.toList());
	}

    public void updateCommentNotificationStatusChecked(Long cardMasterUserId, String notificationId) throws JsonProcessingException {
        redisService.updateCommentNotificationStatusChecked(cardMasterUserId, notificationId);
    }

    public int getUncheckNotificationCount(Long cardMasterUserId) throws JsonProcessingException {
        return redisService.getUncheckNotificationCount(cardMasterUserId);
    }
}
