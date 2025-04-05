package com.pm.projectmanager.aop.activityLog;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class ActivityLogId implements Serializable {
    private Long userId;
    private LocalDateTime createdAt;

    public ActivityLogId() {}

    public ActivityLogId(Long userId, LocalDateTime createdAt) {
        this.userId = userId;
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActivityLogId)) return false;
        ActivityLogId that = (ActivityLogId) o;
        return Objects.equals(userId, that.userId) &&
               Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, createdAt);
    }
}