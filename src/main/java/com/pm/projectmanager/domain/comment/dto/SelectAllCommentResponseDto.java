package com.pm.projectmanager.domain.comment.dto;

import com.pm.projectmanager.domain.comment.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SelectAllCommentResponseDto {
    private final Long commentId;
    private final String content;
    private final String nickName;
    private final LocalDateTime createAt;
    private final String photoUrl;
    private final Long userId;

    public SelectAllCommentResponseDto(Comment comment, String photoUrl) {
        this.commentId = comment.getId();
        this.content = comment.getContent();
        this.nickName = comment.getUser().getNickname();
        this.createAt = comment.getCreatedAt();
        this.photoUrl = photoUrl;
        this.userId = comment.getUser().getId();
    }
}