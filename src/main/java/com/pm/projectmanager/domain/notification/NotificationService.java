package com.pm.projectmanager.domain.notification;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.stereotype.Service;

import com.pm.projectmanager.common.RedisService;
import com.pm.projectmanager.domain.project.ProjectService;
import com.pm.projectmanager.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {

	private final RedisService redisService;

	// public List<ProjectInviteResponseDto> getInvites(UserDetailsImpl userDetails) {
	// 	List<String> ids = redisService.getInvites(userDetails.getUsername());
	//
	// 	List<Long> projectIds = new ArrayList<>();
	// 	Map<Long, Long> projectUserMap = new HashMap<>(); // projectId -> userId 매핑 저장
	//
	// 	for (String id : ids) {
	// 		String[] parts = id.split(":");
	// 		Long projectId = Long.parseLong(parts[0]);
	// 		Long userId = Long.parseLong(parts[1]);
	//
	// 		projectIds.add(projectId);
	// 		projectUserMap.put(projectId, userId);
	// 	}
	//
	// 	List<Project> projects = projectService.getProjects(projectIds);
	//
	// 	return projects.stream()
	// 		.map(project -> {
	// 			Long userId = projectUserMap.get(project.getId());
	// 			User user = userRepository.findById(userId)
	// 				.orElseThrow(() -> new UserNotFoundException(ResponseExceptionEnum.USER_NOT_FOUND));
	// 			return new ProjectInviteResponseDto(project, user);
	// 		})
	// 		.collect(Collectors.toList());
	// }

    public void deleteCommentNotification(Long cardMasterUserId, String notificationId) throws JsonProcessingException {
        redisService.deleteCommentNotification(cardMasterUserId, notificationId);
    }

	public void updateCommentNotificationStatusChecked(Long cardMasterUserId, String notificationId) throws JsonProcessingException {
		redisService.updateCommentNotificationStatusChecked(cardMasterUserId, notificationId);
	}

	public int getUncheckNotificationCount(Long cardMasterUserId) throws JsonProcessingException {
		return redisService.getUncheckNotificationCount(cardMasterUserId);
	}

	public int getNotificationCount(Long userId) {
		return redisService.getNotificationCount(userId);
	}

	public void increaseNotificationCount(Long userId) {
		redisService.increaseNotificationCount(userId);
	}

}
