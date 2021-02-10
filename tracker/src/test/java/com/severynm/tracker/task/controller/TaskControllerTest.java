package com.severynm.tracker.task.controller;

import com.severynm.tracker.task.domain.dto.AttachmentDto;
import com.severynm.tracker.task.domain.dto.CreateCommentDto;
import com.severynm.tracker.task.domain.dto.CreateTaskDto;
import com.severynm.tracker.task.domain.dto.TaskDetailsDto;
import com.severynm.tracker.task.domain.dto.TaskDto;
import com.severynm.tracker.task.domain.dto.UpdateTaskDto;
import com.severynm.tracker.task.domain.entity.TaskStatus;
import com.severynm.tracker.task.domain.service.AttachmentService;
import com.severynm.tracker.task.domain.service.CommentService;
import com.severynm.tracker.task.domain.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @Mock
    private TaskService taskService;
    @Mock
    private CommentService commentService;
    @Mock
    private AttachmentService attachmentService;

    @InjectMocks
    private TaskController target;

    @Test
    void createTask() {
        CreateTaskDto dto = new CreateTaskDto();
        dto.setTheme("some task");

        TaskDto saved = new TaskDto();
        saved.setTaskId(42L);
        when(taskService.createTask(dto)).thenReturn(saved);

        TaskDto result = target.createTask(dto);

        assertNotNull(result);
        assertEquals(42L, result.getTaskId());

        verify(taskService).createTask(dto);
    }

    @Test
    void getTasksList() {
        PageRequest pageable = PageRequest.of(0, 10);
        List<Long> departments = Collections.singletonList(5L);
        TaskDto task = new TaskDto();
        Page<TaskDto> page = new PageImpl<>(List.of(task), pageable, 1);

        when(taskService.findTasks(departments, pageable)).thenReturn(page);

        Page<TaskDto> result = target.getTasksList(departments, pageable);

        assertEquals(page,result);
        verify(taskService).findTasks(departments, pageable);

    }

    @Test
    void getTask() {
        TaskDetailsDto details = new TaskDetailsDto();
        details.setTheme("someTask");
        when(taskService.getTaskDetails(42L)).thenReturn(details);

        TaskDetailsDto result = target.getTask(42L);
        assertEquals(details,result);

        verify(taskService).getTaskDetails(42L);
    }

    @Test
    void updateTask() {

        UpdateTaskDto update = new UpdateTaskDto();
        update.setStatus(TaskStatus.COMPLETE);

        target.updateTask(42L, update);

        verify(taskService).updateTask(42L, update);
    }

    @Test
    void createComment() {
        CreateCommentDto dto = new CreateCommentDto();
        dto.setContent("some text");

        target.createComment(42L, dto);

        verify(commentService).createComment(42L, dto);
    }

    @Test
    void deleteComment() {
        target.deleteComment(42L, 1024L);

        verify(commentService).deleteComment(42L, 1024L);
    }

    @Test
    void createAttachment() {
        AttachmentDto dto = new AttachmentDto();
        dto.setName("some file");

        target.createAttachment(42L, dto);

        verify(attachmentService).createAttachment(42L, dto);
    }

}