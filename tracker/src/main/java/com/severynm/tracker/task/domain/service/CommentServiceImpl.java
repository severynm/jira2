package com.severynm.tracker.task.domain.service;

import com.severynm.tracker.task.domain.dto.CommentDto;
import com.severynm.tracker.task.domain.dto.CreateCommentDto;
import com.severynm.tracker.task.domain.entity.Comment;
import com.severynm.tracker.task.domain.mapper.CommentMapper;
import com.severynm.tracker.task.repository.CommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class CommentServiceImpl implements CommentService {

    private final static Logger LOG = LoggerFactory.getLogger(CommentServiceImpl.class);

    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    public CommentServiceImpl(CommentRepository commentRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    @Override
    public CommentDto createComment(Long taskId, CreateCommentDto commentDto) {
        LOG.info("Creating comment {}", commentDto);
        Comment comment = commentMapper.fromCreateCommentDto(commentDto);
        comment.setTaskId(taskId);
        comment = commentRepository.save(comment);
        return commentRepository
                .findById(comment.getCommentId())
                .map(commentMapper::toCommentDto)
                .orElseThrow(NoSuchFieldError::new);
    }

    @Override
    public void deleteComment(Long taskId, Long commentId) {
        LOG.info("Deleting comment #[{}]", commentId);
        Comment comment = commentRepository
                .findByTaskIdAndCommentId(taskId, commentId)
                .orElseThrow(NoSuchElementException::new);
        commentRepository.delete(comment);
    }
}
