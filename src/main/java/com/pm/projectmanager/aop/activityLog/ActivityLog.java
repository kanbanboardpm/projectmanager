package com.pm.projectmanager.aop.activityLog;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Lob;

@Entity
@IdClass(ActivityLogId.class)
public class ActivityLog {

    @Id
    private Long userId;

    @Id
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private ActionType actionType;

    @Lob
    private String actionDetail;

    public ActivityLog() {}

    public ActivityLog(Long userId, LocalDateTime createdAt, ActionType actionType, String actionDetail) {
        this.userId = userId;
        this.createdAt = createdAt;
        this.actionType = actionType;
        this.actionDetail = actionDetail;
    }
}