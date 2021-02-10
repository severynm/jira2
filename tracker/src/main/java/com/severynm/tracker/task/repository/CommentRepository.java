package com.severynm.tracker.task.repository;

import com.severynm.tracker.task.domain.entity.Comment;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    Optional<Comment> findByTaskIdAndCommentId(Long taskId, Long commentId);
}
