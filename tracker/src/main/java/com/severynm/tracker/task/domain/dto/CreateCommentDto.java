package com.severynm.tracker.task.domain.dto;

public class CreateCommentDto {

    private Long authorId;
    private String content;

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "CreateCommentDto{" +
                "authorId=" + authorId +
                ", content='" + content + '\'' +
                '}';
    }
}
