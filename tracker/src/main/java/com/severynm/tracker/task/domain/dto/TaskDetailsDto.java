package com.severynm.tracker.task.domain.dto;

import java.util.List;

public class TaskDetailsDto extends BaseTaskDto {

    private List<CommentDto> comments;
    private List<AttachmentDto> attachments;

    public List<CommentDto> getComments() {
        return comments;
    }

    public void setComments(List<CommentDto> comments) {
        this.comments = comments;
    }

    public List<AttachmentDto> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<AttachmentDto> attachments) {
        this.attachments = attachments;
    }

    @Override
    public String toString() {
        return "TaskDetailsDto{" +
                "comments=" + comments +
                ", attachments=" + attachments +
                '}';
    }
}
