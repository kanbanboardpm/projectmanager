package com.pm.projectmanager.domain.comment;

import com.pm.projectmanager.common.TimeStamp;
import com.pm.projectmanager.domain.card.Card;
import com.pm.projectmanager.domain.comment.dto.UpdateCommentRequestDto;
import com.pm.projectmanager.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    @Builder
    public Comment(String content,
                   User user,
                   Card card) {
        this.content = content;
        this.user = user;
        this.card = card;
    }

    public void update(UpdateCommentRequestDto requestDto) {
        this.content = requestDto.getContent();
    }

}