package com.severynm.tracker.task.domain.dto;

public class TaskDto extends BaseTaskDto {

    private UserDto author;
    private UserDto executor;

    public UserDto getAuthor() {
        return author;
    }

    public void setAuthor(UserDto author) {
        this.author = author;
    }

    public UserDto getExecutor() {
        return executor;
    }

    public void setExecutor(UserDto executor) {
        this.executor = executor;
    }

    @Override
    public String toString() {
        return "TaskDto{" +
                "author=" + author +
                ", executor=" + executor +
                '}';
    }
}
