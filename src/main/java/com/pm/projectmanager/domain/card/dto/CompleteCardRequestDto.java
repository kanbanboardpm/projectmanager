package com.pm.projectmanager.domain.card.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CompleteCardRequestDto {
    private String completeDate;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    private LocalDateTime toLocalDateTime(String date) {

        if (date == null || date.isEmpty()) {
            return null;
        }
        try {
            return LocalDateTime.parse(date, FORMATTER);
        } catch (java.time.format.DateTimeParseException e) {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay();
        }
    }

    public LocalDateTime getCompleteDate() {
        return toLocalDateTime(completeDate);
    }
}
