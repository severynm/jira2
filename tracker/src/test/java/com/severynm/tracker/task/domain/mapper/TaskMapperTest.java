package com.severynm.tracker.task.domain.mapper;

import com.severynm.tracker.task.domain.dto.AttachmentDto;
import com.severynm.tracker.task.domain.dto.CommentDto;
import com.severynm.tracker.task.domain.dto.CreateTaskDto;
import com.severynm.tracker.task.domain.dto.TaskDetailsDto;
import com.severynm.tracker.task.domain.dto.TaskDto;
import com.severynm.tracker.task.domain.entity.Attachment;
import com.severynm.tracker.task.domain.entity.Comment;
import com.severynm.tracker.task.domain.entity.Department;
import com.severynm.tracker.task.domain.entity.Task;
import com.severynm.tracker.task.domain.entity.TaskStatus;
import com.severynm.tracker.task.domain.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TaskMapperTest {

    @Autowired
    private TaskMapper taskMapper;

    @Test
    void fromCreateDto() {
        CreateTaskDto dto = new CreateTaskDto();
        dto.setAuthorId(2L);
        dto.setDescription("Descr");
        dto.setExecutorId(3L);
        dto.setTheme("Title");
        Task task = taskMapper.fromCreateDto(dto);
        assertNotNull(task);
        assertNull(task.getTaskId());
        assertEquals(2L, task.getAuthor().getUserId());
        assertEquals(3L, task.getExecutor().getUserId());
        assertEquals("Descr", task.getDescription());
        assertEquals("Title", task.getTheme());
    }

    @Test
    void toTaskDto() {
        Task task = new Task();
        User author = createUser(1L, "IT", "Ivan", "Moody");
        User executor = createUser(2L, "FINANCE", "Tailor", "Momsen");
        task.setAuthor(author);
        task.setExecutor(executor);
        task.setTaskId(5L);
        task.setCreated(LocalDateTime.MIN);
        task.setTheme("Do something");
        task.setDescription("...");
        task.setStatus(TaskStatus.CREATED);
        TaskDto dto = taskMapper.toTaskDto(task);

        assertNotNull(dto);
        assertEquals(5L, dto.getTaskId());
        assertEquals(LocalDateTime.MIN, dto.getCreated());
        assertEquals("Do something", dto.getTheme());
        assertEquals("...", dto.getDescription());
        assertEquals(TaskStatus.CREATED, dto.getStatus());

        assertEquals(1L, dto.getAuthor().getUserId());
        assertEquals("Ivan", dto.getAuthor().getFirstName());
        assertEquals("Moody", dto.getAuthor().getLastName());
        assertEquals("IT", dto.getAuthor().getDepartmentName());

        assertEquals(2L, dto.getExecutor().getUserId());
        assertEquals("Tailor", dto.getExecutor().getFirstName());
        assertEquals("Momsen", dto.getExecutor().getLastName());
        assertEquals("FINANCE", dto.getExecutor().getDepartmentName());
    }

    @Test
    void toTaskDetailsDto() throws MalformedURLException {
        Task task = new Task();
        URL link = new URL("https://www.blablacar.com.ua/some-image");
        User commentAuthor = new User();
        commentAuthor.setUserId(42L);
        Attachment attachment = new Attachment();
        attachment.setAttachmentId(55L);
        attachment.setName("attached");
        attachment.setLink(link);
        Comment comment = new Comment();
        comment.setCommentId(11L);
        comment.setCreated(LocalDateTime.MAX);
        comment.setContent("do it in the best way");
        comment.setAuthor(commentAuthor);
        task.setTaskId(5L);
        task.setCreated(LocalDateTime.MIN);
        task.setTheme("Do something");
        task.setDescription("...");
        task.setStatus(TaskStatus.CREATED);
        task.setAttachments(Collections.singletonList(attachment));
        task.setComments(Collections.singletonList(comment));

        TaskDetailsDto dto = taskMapper.toTaskDetailsDto(task);

        assertNotNull(dto);
        assertEquals(5L, dto.getTaskId());
        assertEquals(LocalDateTime.MIN, dto.getCreated());
        assertEquals("Do something", dto.getTheme());
        assertEquals("...", dto.getDescription());
        assertEquals(TaskStatus.CREATED, dto.getStatus());
        assertEquals(1, dto.getComments().size());
        CommentDto commentDto = dto.getComments().get(0);
        assertEquals(11L, commentDto.getCommentId());
        assertEquals(42L, commentDto.getAuthorId());
        assertEquals("do it in the best way", commentDto.getContent());
        assertEquals(LocalDateTime.MAX, commentDto.getCreated());
        assertEquals(1, dto.getAttachments().size());
        AttachmentDto attachmentDto = dto.getAttachments().get(0);
        assertEquals("attached", attachmentDto.getName());
        assertEquals(link, attachmentDto.getLink());
        assertEquals(55L, attachmentDto.getAttachmentId());
    }

    private User createUser(long userId, String departmentName, String firstName, String lastName) {
        User user = new User();
        Department department = new Department();
        department.setName(departmentName);
        user.setUserId(userId);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setDepartment(department);
        return user;
    }
}