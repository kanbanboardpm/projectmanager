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
@RequestMapping("/api/cards")
public class CardController {

    private final CardService cardService;

    // 카드 생성 -> section(섹션)을 넣어줘야함.
    @PostMapping
    public ResponseEntity<HttpResponseDto> createCard(
            @RequestBody CreateCardRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        cardService.createCard(requestDto, userDetails.getUser());
        return of(CARD_CREATE_SUCCESS);
    }

    // 카드 전체 조회( 완료 된 카드는 조회 x )
    @GetMapping
    public ResponseEntity<HttpResponseDto> selectAllCard(
            @RequestBody SelectAllCardRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        List<SelectAllCardResponseDto> responseDto = cardService.selectAllCard(requestDto, userDetails.getUser());
        return of(CARD_SELECT_ALL_SUCCESS, responseDto);
    }

    // 특정 섹션에 대한 카드 전체 조회( 완료 된 카드 포함 조회 )
    @GetMapping("/section")
    public ResponseEntity<HttpResponseDto> selectSectionCard(
            @RequestBody SelectSectionCardRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        List<SelectSectionCardResponseDto> responseDto = cardService.selectSectionCard(requestDto, userDetails.getUser());
        return of(CARD_SELECT_SUCCESS, responseDto);
    }

    @PutMapping("/{cardId}")
    public ResponseEntity<HttpResponseDto> updateCard(
            @PathVariable Long cardId,
            @RequestBody UpdateCardRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        cardService.updateCard(requestDto, userDetails.getUser(), cardId);
        return of(CARD_UPDATE_SUCCESS);
    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity<HttpResponseDto> deleteCard(
            @PathVariable Long cardId,
            @RequestBody DeleteCardRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        cardService.deleteCard(requestDto, userDetails.getUser(), cardId);
        return of(CARD_DELETE_SUCCESS);
    }

}
