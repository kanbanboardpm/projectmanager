package com.pm.projectmanager.domain.notification;

import com.pm.projectmanager.common.RedisService;
import com.pm.projectmanager.common.response.HttpResponseDto;
import com.pm.projectmanager.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

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
            @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        List<String> notifications = redisService.getCommentNotifications(userDetails.getUser().getId());
        return of(COMMENT_NOTIFICATION_SELECT_SUCCESS, notifications);
    }

    @GetMapping("/invites")
    public ResponseEntity<HttpResponseDto> getInvite(
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        List<String> projectIds = notificationService.getInvites(userDetails);
        return of(PROJECT_INVITE_GET_SUCCESS, projectIds);
    }
}
