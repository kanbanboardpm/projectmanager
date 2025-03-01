package com.pm.projectmanager.domain.authority;

import lombok.Getter;

@Getter
public enum UserRole {
    USER("USER"),
    MANAGER("MANAGER");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }
}
