package com.severynm.tracker.task.domain.dto;

import com.severynm.tracker.task.domain.entity.TaskStatus;

public class UpdateTaskDto {

    private TaskStatus status;
    private long executorId;

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public long getExecutorId() {
        return executorId;
    }

    public void setExecutorId(long executorId) {
        this.executorId = executorId;
    }

    @Override
    public String toString() {
        return "UpdateTaskDto{" +
                "status=" + status +
                ", executorId=" + executorId +
                '}';
    }
}
