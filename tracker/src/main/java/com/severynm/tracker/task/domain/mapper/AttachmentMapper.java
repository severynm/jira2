package com.severynm.tracker.task.domain.mapper;

import com.severynm.tracker.task.domain.dto.AttachmentDto;
import com.severynm.tracker.task.domain.dto.CreateAttachmentDto;
import com.severynm.tracker.task.domain.entity.Attachment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AttachmentMapper {

    AttachmentDto toAttachmentDto(Attachment attachment);

    Attachment fromCreateAttachmentDto(CreateAttachmentDto createAttachmentDto);
}
