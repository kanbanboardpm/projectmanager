package com.pm.projectmanager.domain.notification;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pm.projectmanager.common.RedisService;
import com.pm.projectmanager.common.response.HttpResponseDto;
import com.pm.projectmanager.domain.notification.dto.InviteResponseDto;
import com.pm.projectmanager.domain.project.dto.ProjectInviteResponseDto;
import com.pm.projectmanager.domain.project.dto.ProjectResponseDto;
import com.pm.projectmanager.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.pm.projectmanager.common.response.ResponseCodeEnum.*;
import static com.pm.projectmanager.common.response.ResponseUtils.of;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notification")
public class NotificationController {

    private final RedisService redisService;
    private final NotificationService notificationService;

    @GetMapping("/comment")
    public ResponseEntity<HttpResponseDto> getCommentNotification(
            @AuthenticationPrincipal UserDetailsImpl userDetails) throws JsonProcessingException {
        List<CommentNotificationDto> notifications = redisService.getCommentNotifications(userDetails.getUser().getId());
        return of(COMMENT_NOTIFICATION_SELECT_SUCCESS, notifications);
    }

    // check 로 변경
    @PutMapping("/comment/{notificationId}")
    public ResponseEntity<HttpResponseDto> updateCommentNotificationStatusChecked(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable String notificationId) throws JsonProcessingException {
        notificationService.updateCommentNotificationStatusChecked(userDetails.getUser().getId(), notificationId);
        return of(UPDATE_COMMENT_NOTIFICATION_STATUS_CHECK);
    }

    @DeleteMapping("/comment/{notificationId}")
    public ResponseEntity<HttpResponseDto> deleteCommentNotification(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable String notificationId) throws JsonProcessingException {
        notificationService.deleteCommentNotification(userDetails.getUser().getId(), notificationId);
        return of(DELETE_COMMENT_NOTIFICATION);
    }

    @GetMapping("/count")
    public ResponseEntity<HttpResponseDto> getUncheckNotificationCount(
            @AuthenticationPrincipal UserDetailsImpl userDetails) throws JsonProcessingException {
        int count = notificationService.getUncheckNotificationCount(userDetails.getUser().getId());
        return of(NOTIFICATION_COUNT_SELECT_SUCCESS, count);
    }

    @GetMapping("/invites")
    public ResponseEntity<HttpResponseDto> getInvite(
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        List<ProjectInviteResponseDto> projects = notificationService.getInvites(userDetails);
        return of(PROJECT_INVITE_GET_SUCCESS, projects);
    }
}
