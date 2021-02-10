package com.severynm.tracker.task.domain.service;

import com.severynm.tracker.task.domain.dto.CommentDto;
import com.severynm.tracker.task.domain.dto.CreateCommentDto;
import com.severynm.tracker.task.domain.entity.Comment;
import com.severynm.tracker.task.domain.entity.User;
import com.severynm.tracker.task.domain.mapper.CommentMapper;
import com.severynm.tracker.task.repository.CommentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.MalformedURLException;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @Mock
    private CommentRepository repository;

    @Autowired
    @Spy
    private CommentMapper mapper;

    CommentServiceImpl target;

    @BeforeEach
    void init() {
        target = new CommentServiceImpl(repository, mapper);
    }

    @Test
    void createComment() throws MalformedURLException {
        CreateCommentDto dto = new CreateCommentDto();
        dto.setAuthorId(1L);
        dto.setContent("comment");

        Comment expectedComment = new Comment();
        User author = new User();
        author.setUserId(1L);
        expectedComment.setAuthor(author);
        expectedComment.setContent("comment");
        expectedComment.setTaskId(3L);

        Comment savedComment = new Comment();
        savedComment.setCommentId(5L);
        expectedComment.setAuthor(author);
        expectedComment.setContent("comment");
        expectedComment.setTaskId(3L);

        when(repository.save(expectedComment)).thenReturn(savedComment);
        when(repository.findById(5L)).thenReturn(Optional.of(savedComment));

        CommentDto result = target.createComment(3L, dto);

        Assertions.assertEquals(5L, result.getCommentId());

        verify(repository).save(expectedComment);
    }

    @Test
    void deleteComment() throws MalformedURLException {
        Comment comment = new Comment();
        comment.setCommentId(42L);
        when(repository.findByTaskIdAndCommentId(4L, 42L))
                .thenReturn(Optional.of(comment));

        target.deleteComment(4L, 42L);

        verify(repository).delete(comment);
    }
}