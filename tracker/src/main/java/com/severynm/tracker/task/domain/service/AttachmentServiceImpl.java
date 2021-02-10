package com.severynm.tracker.task.domain.service;

import com.severynm.tracker.task.domain.dto.AttachmentDto;
import com.severynm.tracker.task.domain.dto.CreateAttachmentDto;
import com.severynm.tracker.task.domain.entity.Attachment;
import com.severynm.tracker.task.domain.mapper.AttachmentMapper;
import com.severynm.tracker.task.repository.AttachmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AttachmentServiceImpl implements AttachmentService {

    private final static Logger LOG = LoggerFactory.getLogger(AttachmentServiceImpl.class);

    private final AttachmentRepository attachmentRepository;
    private final AttachmentMapper attachmentMapper;

    public AttachmentServiceImpl(AttachmentRepository attachmentRepository, AttachmentMapper attachmentMapper) {
        this.attachmentRepository = attachmentRepository;
        this.attachmentMapper = attachmentMapper;
    }

    @Override
    public AttachmentDto createAttachment(Long taskId, CreateAttachmentDto createAttachmentDto) {
        LOG.info("Creating attachment {}", createAttachmentDto);
        Attachment attachment = attachmentMapper.fromCreateAttachmentDto(createAttachmentDto);
        attachment.setTaskId(taskId);
        attachment = attachmentRepository.save(attachment);
        return attachmentMapper.toAttachmentDto(attachment);
    }
}
