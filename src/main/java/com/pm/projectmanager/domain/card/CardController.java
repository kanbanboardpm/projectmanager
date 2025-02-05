package com.pm.projectmanager.domain.card;

import com.pm.projectmanager.common.response.HttpResponseDto;
import com.pm.projectmanager.domain.card.dto.CreateCardRequestDto;
import com.pm.projectmanager.domain.card.dto.SelectAllCardRequestDto;
import com.pm.projectmanager.domain.card.dto.SelectAllCardResponseDto;
import com.pm.projectmanager.domain.card.dto.SelectCardRequestDto;
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
@RequestMapping("/api")
public class CardController {

    private final CardService cardService;

    // 카드 생성 -> section(섹션)을 넣어줘야함.
    @PostMapping("/cards")
    public ResponseEntity<HttpResponseDto> createCard(
            @RequestBody CreateCardRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        cardService.createCard(requestDto, userDetails.getUser());
        return of(CARD_CREATE_SUCCESS);
    }

    // 카드 전체 조회 -> section(섹션)을 넣어줘야함.
    // 프로젝트id, 섹션id 를 통해서 조회를 해야함.
    @GetMapping("/cards")
    public ResponseEntity<HttpResponseDto> selectAllCard(
            @RequestBody SelectAllCardRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        List<SelectAllCardResponseDto> responseDto = cardService.selectAllCard(requestDto, userDetails.getUser());
        return of(CARD_SELECT_ALL_SUCCESS, responseDto);
    }

    // 카드 section별 조회
    // 완료랑 진행중 카드 섹션별로 조회
//    @GetMapping("/cards")
//    public ResponseEntity<HttpResponseDto> selectCard(
//            @RequestBody SelectCardRequestDto requestDto,
//            @AuthenticationPrincipal UserDetailsImpl userDetails)
//    {
//        return of(CARD_SELECT_SUCCESS)
//    }

//    @PutMapping("/cards")
//    public ResponseEntity<HttpResponseDto> updateCard(UpdateCardRequestDto requestDto) {
//        return of(CARD_UPDATE_SUCCESS)
//    }

}
