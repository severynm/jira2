package com.severynm.tracker.task.domain.service;

import com.severynm.tracker.task.domain.dto.CommentDto;
import com.severynm.tracker.task.domain.dto.CreateCommentDto;

public interface CommentService {

    CommentDto createComment(Long taskId, CreateCommentDto createCommentDto);

    void deleteComment(Long taskId, Long commentId);
}
