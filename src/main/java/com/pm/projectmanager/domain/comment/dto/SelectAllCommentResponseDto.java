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

    public SelectAllCommentResponseDto(Comment comment) {
        this.commentId = comment.getId();
        this.content = comment.getContent();
        this.nickName = comment.getUser().getNickname();
        this.createAt = comment.getCreatedAt();
    }
}