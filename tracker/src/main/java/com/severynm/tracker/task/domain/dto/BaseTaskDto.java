package com.severynm.tracker.task.domain.dto;

import com.severynm.tracker.task.domain.entity.TaskStatus;

import java.time.LocalDateTime;

public class BaseTaskDto {

    private Long taskId;
    private LocalDateTime created;
    private String theme;
    private String description;
    private TaskStatus status;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

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

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BaseTaskDto{" +
                "taskId=" + taskId +
                ", created=" + created +
                ", theme='" + theme + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
