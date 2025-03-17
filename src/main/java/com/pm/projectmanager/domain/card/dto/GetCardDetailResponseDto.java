package com.pm.projectmanager.domain.card.dto;

import com.pm.projectmanager.domain.card.Card;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetCardDetailResponseDto {

    private final Long cardId;
    private final String title;
    private final String content;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final LocalDateTime completeDate;
    private final String categoryColor;
    private final String categoryName;
    private final String nickName;
    private final String photoUrl;
    private final Long userId;

    public GetCardDetailResponseDto(Card card) {
        this.cardId = card.getId();
        this.title = card.getTitle();
        this.content = card.getContent();
        this.startDate = card.getStartDate();
        this.endDate = card.getEndDate();
        this.completeDate = card.getCompleteDate();
        this.categoryColor = card.getCategory().getColor().getHexCode();
        this.categoryName = card.getCategory().getName();
        this.nickName = card.getUser().getNickname();
        this.photoUrl = card.getUser().getPhotoUrl();
        this.userId = card.getUser().getId();
    }

}
