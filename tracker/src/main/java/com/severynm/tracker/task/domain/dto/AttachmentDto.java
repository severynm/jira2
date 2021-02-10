package com.severynm.tracker.task.domain.dto;

public class AttachmentDto extends CreateAttachmentDto {

    private Long attachmentId;

    public Long getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Long attachmentId) {
        this.attachmentId = attachmentId;
    }

    @Override
    public String toString() {
        return "AttachmentDto{" +
                "attachmentId=" + attachmentId +
                '}';
    }
}
