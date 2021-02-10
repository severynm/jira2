package com.severynm.tracker.task.domain.mapper;

import com.severynm.tracker.task.domain.dto.CommentDto;
import com.severynm.tracker.task.domain.dto.CreateCommentDto;
import com.severynm.tracker.task.domain.entity.Comment;
import com.severynm.tracker.task.domain.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentMapperTest {

    @Autowired
    private CommentMapper commentMapper;

    @Test
    public void fromCreateCommentDtoTest() {
        CreateCommentDto createCommentDto = new CreateCommentDto();
        createCommentDto.setAuthorId(2L);
        createCommentDto.setContent("Dummy comment content");
        Comment comment = commentMapper.fromCreateCommentDto(createCommentDto);
        assertNotNull(comment);
        assertNull(comment.getCommentId());
        assertEquals(2L, comment.getAuthor().getUserId());
        assertEquals("Dummy comment content", comment.getContent());
    }

    @Test
    public void toCommentDtoTest() {
        Comment comment = new Comment();
        User author = new User();
        author.setUserId(4L);
        comment.setAuthor(author);
        comment.setContent("content");
        comment.setCommentId(42L);
        comment.setCreated(LocalDateTime.MIN);

        CommentDto commentDto = commentMapper.toCommentDto(comment);
        assertNotNull(commentDto);
        assertEquals(4L, commentDto.getAuthorId());
        assertEquals("content", commentDto.getContent());
        assertEquals(42L, comment.getCommentId());
        assertEquals(LocalDateTime.MIN, comment.getCreated());
    }

}