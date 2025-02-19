package com.pm.projectmanager.domain.comment;

import com.pm.projectmanager.common.response.ResponseExceptionEnum;
import com.pm.projectmanager.domain.card.Card;
import com.pm.projectmanager.domain.card.CardRepository;
import com.pm.projectmanager.domain.comment.dto.CreateCommentRequestDto;
import com.pm.projectmanager.domain.comment.dto.SelectAllCommentResponseDto;
import com.pm.projectmanager.domain.comment.dto.UpdateCommentRequestDto;
import com.pm.projectmanager.domain.user.User;
import com.pm.projectmanager.exception.CardNotFoundException;
import com.pm.projectmanager.exception.CommentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CardRepository cardRepository;

    public void createComment(CreateCommentRequestDto requestDto, User user, Long cardId) {
        Card card = cardRepository.findById(cardId).orElseThrow(
                () -> new CardNotFoundException(ResponseExceptionEnum.CARD_NOT_FOUND)
        );
        Comment comment = Comment.builder()
                .content(requestDto.getContent())
                .card(card)
                .user(user).build();

        commentRepository.save(comment);
    }

    public List<SelectAllCommentResponseDto> selectAllComment(User user, Long cardId) {
        List<Comment> comments = commentRepository.findByCardIdAndUserId(cardId, user.getId());
        return comments.stream().map(SelectAllCommentResponseDto::new).toList();
    }

    @Transactional
    public void updateComment(UpdateCommentRequestDto requestDto, User user, Long commentId) {
        Comment comment = commentRepository.findByIdAndUserId(commentId, user.getId()).orElseThrow(
                () -> new CommentNotFoundException(ResponseExceptionEnum.COMMENT_NOT_FOUND)
        );
        comment.update(requestDto);
    }

    public void deleteComment(User user, Long commentId) {
        Comment comment = commentRepository.findByIdAndUserId(commentId, user.getId()).orElseThrow(
                () -> new CommentNotFoundException(ResponseExceptionEnum.COMMENT_NOT_FOUND)
        );
        commentRepository.delete(comment);
    }
}
