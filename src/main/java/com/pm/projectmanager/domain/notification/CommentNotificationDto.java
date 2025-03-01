package com.pm.projectmanager.domain.notification;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentNotificationDto {
    public String notificationId;
    public String projectName;
    public String cardName;
    public String nickName;
    public String createAt;
    @Setter
    public String status;

    public CommentNotificationDto(String projectName, String cardName, String nickName, String createAt, String status) {
        this.notificationId = UUID.randomUUID().toString();
        this.projectName = projectName;
        this.cardName = cardName;
        this.nickName = nickName;
        this.createAt = createAt;
        this.status = status;
    }

    public CommentNotificationDto(String notificationId, String projectName, String cardName, String nickName, String createAt, String status) {
        this.notificationId = notificationId;
        this.projectName = projectName;
        this.cardName = cardName;
        this.nickName = nickName;
        this.createAt = createAt;
        this.status = status;
    }
}