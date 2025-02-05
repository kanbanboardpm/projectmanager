package com.pm.projectmanager.domain.card.dto;

import com.pm.projectmanager.domain.card.Card;
import com.pm.projectmanager.domain.project.Color;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SelectAllCardResponseDto {
    private final Long cardId;
    private final String title;
    private final String content;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final LocalDateTime completeDate;
    private final String categoryName;
    private final String color;

    public SelectAllCardResponseDto(Card card) {
        this.cardId = card.getId();
        this.title = card.getTitle();
        this.content = card.getContent();
        this.startDate = card.getStartDate();
        this.endDate = card.getEndDate();
        this.completeDate = card.getCompleteDate();
        this.categoryName = card.getCategory().getName();
        this.color = card.getCategory().getColor().getHexCode();
    }
}
