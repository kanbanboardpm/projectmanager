package com.pm.projectmanager.domain.card;

import com.pm.projectmanager.common.response.HttpResponseDto;
import com.pm.projectmanager.domain.card.dto.CreateCardRequestDto;
import com.pm.projectmanager.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.pm.projectmanager.common.response.ResponseCodeEnum.*;
import static com.pm.projectmanager.common.response.ResponseUtils.of;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CardController {

    private final CardService cardService;

    @PostMapping("/cards")
    public ResponseEntity<HttpResponseDto> createCard(
            @RequestBody CreateCardRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        cardService.createCard(requestDto, userDetails.getUser());
        return of(CARD_CREATE_SUCCESS);
    }

}
