package com.severynm.tracker.task.domain.dto;

public class CreateTaskDto {

    private String theme;
    private String description;
    private Long authorId;
    private Long executorId;

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getExecutorId() {
        return executorId;
    }

    public void setExecutorId(Long executorId) {
        this.executorId = executorId;
    }

    @Override
    public String toString() {
        return "CreateTaskDto{" +
                "theme='" + theme + '\'' +
                ", description='" + description + '\'' +
                ", authorId=" + authorId +
                ", executorId=" + executorId +
                '}';
    }
}
