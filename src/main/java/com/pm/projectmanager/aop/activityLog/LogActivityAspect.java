package com.pm.projectmanager.aop.activityLog;

import java.time.LocalDateTime;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.pm.projectmanager.aop.activityLog.ActivityLog;
import com.pm.projectmanager.aop.activityLog.ActivityLogRepository;
import com.pm.projectmanager.aop.activityLog.LogActivity;
import com.pm.projectmanager.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class LogActivityAspect {

    private final ActivityLogRepository activityLogRepository;
    private final SpelExpressionParser parser = new SpelExpressionParser();

    @Around("@annotation(logActivity)")
    public Object logActivity(ProceedingJoinPoint joinPoint, LogActivity logActivity) throws Throwable {
        Object result = joinPoint.proceed();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) return result;

        Object principal = auth.getPrincipal();

        if (principal instanceof UserDetailsImpl user) {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            String[] paramNames = signature.getParameterNames();
            Object[] args = joinPoint.getArgs();

            EvaluationContext context = new StandardEvaluationContext();
            for (int i = 0; i < paramNames.length; i++) {
                context.setVariable(paramNames[i], args[i]);
            }
            context.setVariable("user", user);

            String detailSpel = logActivity.detail();
            String detail = parser.parseExpression(detailSpel, new TemplateParserContext()).getValue(context, String.class);

            activityLogRepository.save(new ActivityLog(
                user.getUser().getId(),
                LocalDateTime.now(),
                logActivity.value(),
                detail
            ));
        }

        return result;
    }
}
