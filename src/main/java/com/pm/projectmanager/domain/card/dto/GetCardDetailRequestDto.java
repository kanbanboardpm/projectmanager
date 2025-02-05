package com.pm.projectmanager.domain.card.dto;

import lombok.Getter;

@Getter
public class GetCardDetailRequestDto {
    private Long projectId;
    private Long sectionId;
}
