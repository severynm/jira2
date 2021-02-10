package com.severynm.tracker.task.controller;

import com.severynm.tracker.task.domain.dto.AttachmentDto;
import com.severynm.tracker.task.domain.dto.CreateAttachmentDto;
import com.severynm.tracker.task.domain.dto.CreateCommentDto;
import com.severynm.tracker.task.domain.dto.CommentDto;
import com.severynm.tracker.task.domain.dto.TaskDetailsDto;
import com.severynm.tracker.task.domain.dto.CreateTaskDto;
import com.severynm.tracker.task.domain.dto.TaskDto;
import com.severynm.tracker.task.domain.dto.UpdateTaskDto;
import com.severynm.tracker.task.domain.service.AttachmentService;
import com.severynm.tracker.task.domain.service.CommentService;
import com.severynm.tracker.task.domain.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("tasks")
public class TaskController {

    private final static Logger LOG = LoggerFactory.getLogger(TaskController.class);

    private final TaskService taskService;
    private final CommentService commentService;
    private final AttachmentService attachmentService;

    @Autowired
    public TaskController(TaskService taskService, CommentService commentService, AttachmentService attachmentService) {
        this.taskService = taskService;
        this.commentService = commentService;
        this.attachmentService = attachmentService;
    }

    @PostMapping
    public TaskDto createTask(@RequestBody CreateTaskDto task) {
        return taskService.createTask(task);
    }

    @GetMapping
    public Page<TaskDto> getTasksList(@RequestParam(required = false) List<Long> departments, Pageable pageable) {
        return taskService.findTasks(departments, pageable);
    }

    @GetMapping("{taskId}")
    public TaskDetailsDto getTask(@PathVariable long taskId) {
        return taskService.getTaskDetails(taskId);
    }

    @PutMapping("{taskId}")
    public TaskDto updateTask(@PathVariable long taskId, @RequestBody UpdateTaskDto task) {
        return taskService.updateTask(taskId, task);
    }

    @PostMapping("{taskId}/comments")
    public CommentDto createComment(@PathVariable long taskId, @RequestBody CreateCommentDto comment) {
        return commentService.createComment(taskId, comment);
    }

    @DeleteMapping("{taskId}/comments/{commentId}")
    public void deleteComment(@PathVariable Long taskId, @PathVariable Long commentId) {
        commentService.deleteComment(taskId, commentId);
    }

    @PostMapping("{taskId}/attachments")
    public AttachmentDto createAttachment(@PathVariable long taskId, @RequestBody CreateAttachmentDto createAttachmentDto) {
        return attachmentService.createAttachment(taskId, createAttachmentDto);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleException(Exception e) {
        LOG.error("Catch unhandled exception", e);
        String message = String.format("%s : %s", e.getClass().getSimpleName(), e.getMessage());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
