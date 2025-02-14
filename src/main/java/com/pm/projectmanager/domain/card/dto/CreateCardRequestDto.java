package com.pm.projectmanager.domain.card.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreateCardRequestDto {
    private String title;
    private String content;
    private LocalDateTime startDate; // ex) 2024-01-31T10:00:00
    private LocalDateTime endDate; // ex) 2024-01-31T10:00:00
    private Long categoryId;
    private Long projectId;
    private Long sectionId;
}
