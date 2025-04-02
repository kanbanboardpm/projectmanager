package com.pm.projectmanager.aop.activityLog;

import java.time.LocalDateTime;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.pm.projectmanager.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class LogActivityAspect {

    private final ActivityLogRepository activityLogRepository;

    @Around("@annotation(logActivity)")
    public Object logActivity(ProceedingJoinPoint joinPoint, LogActivity logActivity) throws Throwable {
        Object result = joinPoint.proceed();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            UserDetailsImpl user = (UserDetailsImpl) auth.getPrincipal();

            activityLogRepository.save(new ActivityLog(
                user.getUser().getId(),
                LocalDateTime.now(),
                logActivity.value(),
                logActivity.detail()
            ));
        }

        return result;
    }
}