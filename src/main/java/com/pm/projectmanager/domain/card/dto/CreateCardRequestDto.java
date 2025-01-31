package com.pm.projectmanager.domain.card.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateCardRequestDto {
    private String title;
    private String content;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long categoryId;
    private Long projectId;
}
