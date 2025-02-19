package com.pm.projectmanager.domain.comment;

import com.pm.projectmanager.common.response.HttpResponseDto;
import com.pm.projectmanager.common.response.ResponseExceptionEnum;
import com.pm.projectmanager.domain.card.CardRepository;
import com.pm.projectmanager.domain.comment.dto.CreateCommentRequestDto;
import com.pm.projectmanager.domain.comment.dto.SelectAllCommentResponseDto;
import com.pm.projectmanager.domain.comment.dto.UpdateCommentRequestDto;
import com.pm.projectmanager.exception.CardNotFoundException;
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
public class CommentController {

    private final CommentService commentService;
    private final CardRepository cardRepository;

    @PostMapping("/api/cards/{cardId}/comments")
    public ResponseEntity<HttpResponseDto> createComment(
            @PathVariable Long cardId,
            @RequestBody CreateCommentRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        commentService.createComment(requestDto, userDetails.getUser(), cardId);
        return of(COMMENT_CREATE_SUCCESS);
    }

    @GetMapping("/api/cards/{cardId}/comments")
    public ResponseEntity<HttpResponseDto> selectAllComment(
            @PathVariable Long cardId,
            @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        cardRepository.findById(cardId).orElseThrow(
                () -> new CardNotFoundException(ResponseExceptionEnum.CARD_NOT_FOUND)
        );
        List<SelectAllCommentResponseDto> responseDtoList = commentService.selectAllComment(userDetails.getUser(), cardId);
        return of(COMMENT_SELECT_SUCCESS, responseDtoList);
    }

    @PutMapping("/api/comments/{commentId}")
    public ResponseEntity<HttpResponseDto> updateComment(
            @PathVariable Long commentId,
            @RequestBody UpdateCommentRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        commentService.updateComment(requestDto, userDetails.getUser(), commentId);
        return of(COMMENT_UPDATE_SUCCESS);
    }

    @DeleteMapping("/api/comments/{commentId}")
    public ResponseEntity<HttpResponseDto> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        commentService.deleteComment(userDetails.getUser(), commentId);
        return of(COMMENT_DELETE_SUCCESS);
    }
}