package com.severynm.tracker.task.domain.service;

import com.severynm.tracker.task.domain.dto.CreateTaskDto;
import com.severynm.tracker.task.domain.dto.TaskDetailsDto;
import com.severynm.tracker.task.domain.dto.TaskDto;
import com.severynm.tracker.task.domain.dto.UpdateTaskDto;
import com.severynm.tracker.task.domain.entity.Attachment;
import com.severynm.tracker.task.domain.entity.Comment;
import com.severynm.tracker.task.domain.entity.Task;
import com.severynm.tracker.task.domain.entity.TaskStatus;
import com.severynm.tracker.task.domain.entity.User;
import com.severynm.tracker.task.domain.mapper.TaskMapper;
import com.severynm.tracker.task.integration.RatingService;
import com.severynm.tracker.task.repository.TaskRepository;
import feign.RetryableException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;
    @Mock
    private RatingService ratingService;
    @Spy
    @Autowired
    private TaskMapper taskMapper;

    private TaskServiceImpl target;

    @BeforeEach
    void init() {
        target = new TaskServiceImpl(taskRepository, taskMapper, ratingService);
    }

    @Test
    void createTask() {
        CreateTaskDto dto = new CreateTaskDto();
        dto.setTheme("theme");
        dto.setDescription("desc");
        dto.setAuthorId(1L);
        dto.setExecutorId(2L);

        Task expectedToBeSave = new Task();
        User author = new User();
        User executor = new User();
        author.setUserId(1L);
        executor.setUserId(2L);
        expectedToBeSave.setTheme("theme");
        expectedToBeSave.setDescription("desc");
        expectedToBeSave.setAuthor(author);
        expectedToBeSave.setExecutor(executor);
        expectedToBeSave.setStatus(TaskStatus.CREATED);

        Task saved = new Task();
        saved.setTaskId(42L);
        saved.setAuthor(author);
        saved.setExecutor(executor);

        when(taskRepository.save(expectedToBeSave)).thenReturn(saved);
        when(taskRepository.findById(42L)).thenReturn(Optional.of(saved));
        when(ratingService.getRating(Set.of(1L, 2L))).thenReturn(Map.of(1L, 2, 2L, 3));

        TaskDto result = target.createTask(dto);
        assertNotNull(result);
        assertEquals(42L, result.getTaskId());
        assertEquals(2, result.getAuthor().getRating());
        assertEquals(3, result.getExecutor().getRating());

        verify(taskRepository).save(expectedToBeSave);
        verify(ratingService).getRating(Set.of(1L, 2L));
    }

    @Test
    void updateTask() {
        UpdateTaskDto dto = new UpdateTaskDto();
        dto.setExecutorId(1L);
        dto.setStatus(TaskStatus.IN_PROGRESS);

        User user1 = new User();
        user1.setUserId(1L);
        User user2 = new User();
        user2.setUserId(2L);

        Task persisted = new Task();
        persisted.setAuthor(user1);
        persisted.setExecutor(user2);
        persisted.setStatus(TaskStatus.CREATED);

        Task updated = new Task();
        updated.setTaskId(42L);
        updated.setAuthor(user1);
        updated.setExecutor(user1);

        when(taskRepository.findById(42L)).thenReturn(Optional.of(persisted));
        when(taskRepository.findById(42L)).thenReturn(Optional.of(updated));
        when(ratingService.getRating(Set.of(1L))).thenReturn(Map.of(1L, 2));

        TaskDto result = target.updateTask(42L, dto);
        assertNotNull(result);
        assertEquals(42L, result.getTaskId());
        assertEquals(2, result.getAuthor().getRating());
        assertEquals(2, result.getExecutor().getRating());

        verify(taskRepository).save(updated);
        verify(ratingService).getRating(Set.of(1L));
    }

    @Test
    void findTasks() {
        User user1 = new User();
        user1.setUserId(1L);
        User user2 = new User();
        user2.setUserId(2L);
        Pageable pageable = PageRequest.of(0, 10);

        Task task1 = new Task();
        task1.setTaskId(101L);
        task1.setAuthor(user1);
        task1.setExecutor(user2);

        Task task2 = new Task();
        task2.setTaskId(102L);
        task2.setAuthor(user2);
        task2.setExecutor(user1);

        PageImpl<Task> page = new PageImpl<>(List.of(task1, task2), pageable, 2);
        when(taskRepository.findAll(pageable)).thenReturn(page);
        when(ratingService.getRating(Set.of(1L, 2L))).thenReturn(Map.of(1L, 1, 2L, 3));

        Page<TaskDto> result = target.findTasks(null, pageable);

        assertNotNull(result);

        List<TaskDto> content = result.getContent();
        assertEquals(2, content.size());
        TaskDto dto1 = content.get(0);
        TaskDto dto2 = content.get(1);
        assertEquals(101L, dto1.getTaskId());
        assertEquals(1, dto1.getAuthor().getRating());
        assertEquals(3, dto1.getExecutor().getRating());

        assertEquals(102L, dto2.getTaskId());
        assertEquals(3, dto2.getAuthor().getRating());
        assertEquals(1, dto2.getExecutor().getRating());

        verify(taskRepository).findAll(pageable);
    }

    @Test
    void findTasks_whenDepartmentPassed_shouldFilterByDepartment() {
        User user1 = new User();
        user1.setUserId(1L);
        User user2 = new User();
        user2.setUserId(2L);
        Pageable pageable = PageRequest.of(0, 10);

        Task task1 = new Task();
        task1.setTaskId(101L);
        task1.setAuthor(user1);
        task1.setExecutor(user2);

        Task task2 = new Task();
        task2.setTaskId(102L);
        task2.setAuthor(user2);
        task2.setExecutor(user1);

        PageImpl<Task> page = new PageImpl<>(List.of(task1, task2), pageable, 2);
        when(taskRepository.findByAuthorDepartmentDepartmentIdIn(Collections.singletonList(2L), pageable)).thenReturn(page);
        when(ratingService.getRating(Set.of(1L, 2L))).thenReturn(Map.of(1L, 1, 2L, 3));

        Page<TaskDto> result = target.findTasks(Collections.singletonList(2L), pageable);

        assertNotNull(result);

        List<TaskDto> content = result.getContent();
        assertEquals(2, content.size());
        TaskDto dto1 = content.get(0);
        TaskDto dto2 = content.get(1);
        assertEquals(101L, dto1.getTaskId());
        assertEquals(1, dto1.getAuthor().getRating());
        assertEquals(3, dto1.getExecutor().getRating());

        assertEquals(102L, dto2.getTaskId());
        assertEquals(3, dto2.getAuthor().getRating());
        assertEquals(1, dto2.getExecutor().getRating());

        taskRepository.findByAuthorDepartmentDepartmentIdIn(Collections.singletonList(2L), pageable);

    }

    @Test
    void findTasks_whenRatingServiceNotAvailable_shouldReturnTaskWithDefaultRating() {
        User user1 = new User();
        user1.setUserId(1L);
        User user2 = new User();
        user2.setUserId(2L);
        Pageable pageable = PageRequest.of(0, 10);

        Task task1 = new Task();
        task1.setTaskId(101L);
        task1.setAuthor(user1);
        task1.setExecutor(user2);

        Task task2 = new Task();
        task2.setTaskId(102L);
        task2.setAuthor(user2);
        task2.setExecutor(user1);

        PageImpl<Task> page = new PageImpl<>(List.of(task1, task2), pageable, 2);
        when(taskRepository.findAll(pageable)).thenReturn(page);
        when(ratingService.getRating(Set.of(1L, 2L))).thenThrow(RetryableException.class);

        Page<TaskDto> result = target.findTasks(null, pageable);

        assertNotNull(result);

        List<TaskDto> content = result.getContent();
        assertEquals(2, content.size());
        TaskDto dto1 = content.get(0);
        TaskDto dto2 = content.get(1);
        assertEquals(101L, dto1.getTaskId());
        assertEquals(2, dto1.getAuthor().getRating());
        assertEquals(2, dto1.getExecutor().getRating());

        assertEquals(102L, dto2.getTaskId());
        assertEquals(2, dto2.getAuthor().getRating());
        assertEquals(2, dto2.getExecutor().getRating());
    }

    @Test
    void getTaskDetails() {
        Comment comment = new Comment();
        comment.setCommentId(11L);
        Attachment attachment = new Attachment();
        attachment.setAttachmentId(22L);
        Task task = new Task();
        task.setTaskId(42L);
        task.setComments(Collections.singletonList(comment));
        task.setAttachments(Collections.singletonList(attachment));

        when(taskRepository.findById(42L)).thenReturn(Optional.of(task));

        TaskDetailsDto result = target.getTaskDetails(42L);

        assertEquals(42L, result.getTaskId());
        assertEquals(1, result.getComments().size());
        assertEquals(11L, result.getComments().get(0).getCommentId());
        assertEquals(1, result.getAttachments().size());
        assertEquals(22L, result.getAttachments().get(0).getAttachmentId());
    }
}