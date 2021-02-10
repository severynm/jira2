package com.severynm.tracker.task.domain.mapper;

import com.severynm.tracker.task.domain.dto.CommentDto;
import com.severynm.tracker.task.domain.dto.CreateCommentDto;
import com.severynm.tracker.task.domain.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "author.userId", source = "authorId")
    Comment fromCreateCommentDto(CreateCommentDto commentDto);

    @Mapping(target = "authorId", source = "author.userId")
    CommentDto toCommentDto(Comment comment);


}
