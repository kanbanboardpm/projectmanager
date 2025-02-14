package com.pm.projectmanager.domain.card;

import com.pm.projectmanager.common.response.HttpResponseDto;
import com.pm.projectmanager.domain.card.dto.*;
import com.pm.projectmanager.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.pm.projectmanager.common.response.ResponseCodeEnum.*;
import static com.pm.projectmanager.common.response.ResponseUtils.of;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects/{projectId}")
public class ProjectCardController {

    private final CardService cardService;

    // 카드 생성 -> section(섹션)을 넣어줘야함.
    @PostMapping("/sections/{sectionId}/cards")
    public ResponseEntity<HttpResponseDto> createCard(
            @PathVariable Long projectId,
            @PathVariable Long sectionId,
            @RequestBody CreateCardRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        requestDto.setProjectId(projectId);
        requestDto.setSectionId(sectionId);
        cardService.createCard(requestDto, userDetails.getUser());
        return of(CARD_CREATE_SUCCESS);
    }

    // 카드 전체 조회( 완료 된 카드는 조회 x )
    @GetMapping("/cards")
    public ResponseEntity<HttpResponseDto> selectAllCard(
            @PathVariable Long projectId,
            @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        List<SelectAllCardResponseDto> responseDto = cardService.selectAllCard(projectId, userDetails.getUser());
        return of(CARD_SELECT_ALL_SUCCESS, responseDto);
    }

    // 특정 섹션에 대한 카드 전체 조회( 완료 된 카드 포함 조회 )
    @GetMapping("/sections/{sectionId}/cards")
    public ResponseEntity<HttpResponseDto> selectSectionCard(
            @PathVariable Long projectId,
            @PathVariable Long sectionId,
            @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        List<SelectSectionCardResponseDto> responseDto = cardService.selectSectionCard(projectId, sectionId, userDetails.getUser());
        return of(CARD_SELECT_SUCCESS, responseDto);
    }

    // 카드 상세 조회
    @GetMapping("/sections/{sectionId}/cards/{cardId}")
    public ResponseEntity<HttpResponseDto> getCardDetail(
            @PathVariable Long cardId,
            @PathVariable Long projectId,
            @PathVariable Long sectionId,
            @RequestBody GetCardDetailRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        requestDto.setProjectId(projectId);
        requestDto.setSectionId(sectionId);
        GetCardDetailResponseDto responseDto = cardService.getCardDetail(requestDto, userDetails.getUser(), cardId);
        return of(CARD_SELECT_DETAIL_SUCCESS, responseDto);
    }
}