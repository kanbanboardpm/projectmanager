package com.pm.projectmanager.domain.card.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class CreateCardRequestDto {
    private String title;
    private String content;

    @Getter(AccessLevel.NONE)
    private String startDate; // ex) 2024-01-31T10:00:00

    @Getter(AccessLevel.NONE)
    private String endDate; // ex) 2024-01-31T10:00:00

    private Long categoryId;
    private Long projectId;
    private Long sectionId;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    private LocalDateTime toLocalDateTime(String date) {
        return LocalDateTime.parse(date, FORMATTER);
    }

    public LocalDateTime getStartDate() {
        return toLocalDateTime(startDate);
    }

    public LocalDateTime getEndDate() {
        return toLocalDateTime(endDate);
    }
}
