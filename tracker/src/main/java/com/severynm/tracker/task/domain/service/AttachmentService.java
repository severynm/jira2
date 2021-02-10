package com.severynm.tracker.task.domain.service;

import com.severynm.tracker.task.domain.dto.AttachmentDto;
import com.severynm.tracker.task.domain.dto.CreateAttachmentDto;

public interface AttachmentService {

    AttachmentDto createAttachment(Long taskId, CreateAttachmentDto createAttachmentDto);

}
