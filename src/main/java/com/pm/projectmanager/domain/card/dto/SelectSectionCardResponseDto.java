package com.pm.projectmanager.domain.card.dto;

import com.pm.projectmanager.domain.card.Card;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SelectSectionCardResponseDto {
    private final Long cardId;
    private final Long sectionId;
    private final String title;
    private final String content;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final LocalDateTime completeDate;
    private final String categoryName;
    private final String color;
    private final String nickName;
    private final String photoUrl;

    public SelectSectionCardResponseDto(Card card) {
        this.cardId = card.getId();
        this.sectionId = card.getSection().getId();
        this.title = card.getTitle();
        this.content = card.getContent();
        this.startDate = card.getStartDate();
        this.endDate = card.getEndDate();
        this.completeDate = card.getCompleteDate();
        this.categoryName = card.getCategory().getName();
        this.color = card.getCategory().getColor().getHexCode();
        this.nickName = card.getUser().getNickname();
        this.photoUrl = card.getUser().getPhotoUrl();
    }
}
