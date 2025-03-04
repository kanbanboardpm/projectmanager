package com.pm.projectmanager.domain.comment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pm.projectmanager.common.RedisService;
import com.pm.projectmanager.common.response.ResponseExceptionEnum;
import com.pm.projectmanager.domain.authority.AuthorityService;
import com.pm.projectmanager.domain.card.Card;
import com.pm.projectmanager.domain.card.CardRepository;
import com.pm.projectmanager.domain.comment.dto.CreateCommentRequestDto;
import com.pm.projectmanager.domain.comment.dto.SelectAllCommentResponseDto;
import com.pm.projectmanager.domain.comment.dto.UpdateCommentRequestDto;
import com.pm.projectmanager.domain.project.ProjectService;
import com.pm.projectmanager.domain.user.User;
import com.pm.projectmanager.domain.user.UserRepository;
import com.pm.projectmanager.exception.AuthorityNullException;
import com.pm.projectmanager.exception.CardNotFoundException;
import com.pm.projectmanager.exception.CommentNotFoundException;
import com.pm.projectmanager.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final RedisService redisService;
    private final AuthorityService authorityService;

    public void createComment(CreateCommentRequestDto requestDto, User user, Long cardId) throws JsonProcessingException {
        Card card = cardRepository.findById(cardId).orElseThrow(
                () -> new CardNotFoundException(ResponseExceptionEnum.CARD_NOT_FOUND)
        );
        Comment comment = Comment.builder()
                .content(requestDto.getContent())
                .card(card)
                .user(user).build();

        commentRepository.save(comment);
        User cardMasterUser = userRepository.findById(card.getUser().getId()).orElseThrow(
                () -> new UserNotFoundException(ResponseExceptionEnum.USER_NOT_FOUND)
        );
        redisService.commentNotifications(cardMasterUser.getId(), cardMasterUser.getNickname(), card.getSection().getProject().getName(), card.getTitle(), user.getNickname());
    }

    public List<SelectAllCommentResponseDto> selectAllComment(User user, Long cardId) {
        cardRepository.findById(cardId).orElseThrow(
                () -> new CardNotFoundException(ResponseExceptionEnum.CARD_NOT_FOUND)
        );
        List<Comment> comments = commentRepository.findByCardId(cardId);
        return comments.stream()
                .map(comment -> new SelectAllCommentResponseDto(comment, comment.getUser().getPhotoUrl()))
                .toList();
    }

    @Transactional
    public void updateComment(UpdateCommentRequestDto requestDto, User user, Long commentId) {

        Comment comment = commentRepository.findByIdAndUserId(commentId, user.getId()).orElseThrow(
                () -> new CommentNotFoundException(ResponseExceptionEnum.COMMENT_NOT_FOUND)
        );
        if (!authorityService.adminCheck(comment.getCard().getSection().getProject().getId(), user.getId())
            && comment.getUser().getId().equals(user.getId())) {
            throw new AuthorityNullException(ResponseExceptionEnum.AUTHORITY_NULL_EXCEPTION);
        }
        comment.update(requestDto);
    }

    @Transactional
    public void deleteComment(User user, Long commentId) {
        Comment comment = commentRepository.findByIdAndUserId(commentId, user.getId()).orElseThrow(
                () -> new CommentNotFoundException(ResponseExceptionEnum.COMMENT_NOT_FOUND)
        );
        if (!authorityService.adminCheck(comment.getCard().getSection().getProject().getId(), user.getId())
            && comment.getUser().getId().equals(user.getId())) {
            throw new AuthorityNullException(ResponseExceptionEnum.AUTHORITY_NULL_EXCEPTION);
        }
        commentRepository.delete(comment);
    }
}
