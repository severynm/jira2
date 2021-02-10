package com.severynm.tracker.task.domain.dto;

import java.time.LocalDateTime;

public class CommentDto extends CreateCommentDto {

    private Long commentId;
    private LocalDateTime created;

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "CommentDto{" +
                "commentId=" + commentId +
                ", created=" + created +
                '}';
    }
}
