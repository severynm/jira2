package com.severynm.tracker.task.domain.service;

import com.severynm.tracker.task.domain.dto.CreateTaskDto;
import com.severynm.tracker.task.domain.dto.TaskDetailsDto;
import com.severynm.tracker.task.domain.dto.TaskDto;
import com.severynm.tracker.task.domain.dto.UpdateTaskDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaskService {

    Page<TaskDto> findTasks(List<Long> departments, Pageable page);

    TaskDto createTask(CreateTaskDto createTaskDto);

    TaskDto updateTask(Long taskId, UpdateTaskDto updateTaskDto);

    TaskDetailsDto getTaskDetails(Long taskId);
}
