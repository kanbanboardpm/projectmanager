package com.pm.projectmanager.domain.card.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SelectSectionCardRequestDto {
    private Long projectId;
    private Long sectionId;
}
