package com.pm.projectmanager.domain.project;

import java.security.SecureRandom;

import org.springframework.data.redis.core.RedisTemplate;

import com.pm.projectmanager.common.RedisService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InviteCodeGenerator {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int CODE_LENGTH = 6;
    private static final SecureRandom random = new SecureRandom();
    private final RedisTemplate<String, String> redisTemplate;


    public String generateUniqueCode() {
        String code;
        do {
            code = generateCode();
        } while (inviteCodeExists(code));
        return code;
    }

    private String generateCode() {
        StringBuilder sb = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }

    private boolean inviteCodeExists(String code) {
        return Boolean.TRUE.equals(redisTemplate.hasKey("invite:" + code));
    }
}
