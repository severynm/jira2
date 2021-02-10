package com.severynm.tracker.task.domain.mapper;

import com.severynm.tracker.task.domain.dto.CreateTaskDto;
import com.severynm.tracker.task.domain.dto.TaskDetailsDto;
import com.severynm.tracker.task.domain.dto.TaskDto;
import com.severynm.tracker.task.domain.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        uses = {CommentMapper.class, AttachmentMapper.class, UserMapper.class})
public interface TaskMapper {

    @Mapping(target = "author.userId", source = "authorId")
    @Mapping(target = "executor.userId", source = "executorId")
    Task fromCreateDto(CreateTaskDto dto);

    TaskDto toTaskDto(Task task);

    TaskDetailsDto toTaskDetailsDto(Task task);

}
