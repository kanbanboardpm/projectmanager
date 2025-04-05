package com.pm.projectmanager.aop.activityLog;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityLogRepository extends JpaRepository<ActivityLog, ActivityLogId> {}