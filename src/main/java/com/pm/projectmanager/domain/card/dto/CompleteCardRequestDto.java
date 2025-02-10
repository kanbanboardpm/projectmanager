package com.pm.projectmanager.domain.card.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CompleteCardRequestDto {
    private LocalDateTime completeDate;
}
