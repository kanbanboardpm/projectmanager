package com.pm.projectmanager.domain.card;

import com.pm.projectmanager.common.PageableResponse;
import com.pm.projectmanager.common.response.HttpResponseDto;
import com.pm.projectmanager.domain.card.dto.CompleteCardRequestDto;
import com.pm.projectmanager.domain.card.dto.DeleteCardRequestDto;
import com.pm.projectmanager.domain.card.dto.UpdateCardRequestDto;
import com.pm.projectmanager.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.pm.projectmanager.common.response.ResponseCodeEnum.*;
import static com.pm.projectmanager.common.response.ResponseUtils.of;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cards")
public class CardController {

    private final CardService cardService;

    // 카드 수정
    @PutMapping("/{cardId}")
    public ResponseEntity<HttpResponseDto> updateCard(
            @PathVariable Long cardId,
            @RequestBody UpdateCardRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        cardService.updateCard(requestDto, userDetails.getUser(), cardId);
        return of(CARD_UPDATE_SUCCESS);
    }
    
    // 카드 삭제
    @DeleteMapping("/{cardId}")
    public ResponseEntity<HttpResponseDto> deleteCard(
            @PathVariable Long cardId,
            @RequestBody DeleteCardRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        cardService.deleteCard(requestDto, userDetails.getUser(), cardId);
        return of(CARD_DELETE_SUCCESS);
    }

    /**
     * < Card 완료 기능 >
     * @param cardId : api -> 카드번호
     * @param requestDto : 요청 request 데이터(완료일)
     * @param userDetails : 현재 요청을 보내는 유저의 대한 정보
     * @return : 카드 성공 -> 상태 코드 및 상태 메시지 리턴
     */
    @PutMapping("/{cardId}/complete")
    public ResponseEntity<HttpResponseDto> completeCard(
            @PathVariable Long cardId,
            @RequestBody CompleteCardRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        cardService.completeCard(requestDto, userDetails.getUser(), cardId);
        return of(CARD_COMPLETE_SUCCESS);
    }

    @PutMapping("/{cardId}/progress")
    public ResponseEntity<HttpResponseDto> progressCard(
            @PathVariable Long cardId,
            @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        cardService.progressCard(cardId, userDetails.getUser());
        return of(CARD_PROGRESS_SUCCESS);
    }

    // 개인의 진행 중인 카드 조회
    @GetMapping("/progress")
    public ResponseEntity<HttpResponseDto> selectProgressCard(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        Page<Card> cards = cardService.selectProgressCard(userDetails.getUser(), page, size);
        return of(CARD_PROGRESS_SELECT_SUCCESS, new PageableResponse<>(cards));
    }

}
