package com.severynm.tracker.task.domain.service;

import com.severynm.tracker.task.domain.dto.CreateTaskDto;
import com.severynm.tracker.task.domain.dto.TaskDetailsDto;
import com.severynm.tracker.task.domain.dto.TaskDto;
import com.severynm.tracker.task.domain.dto.UpdateTaskDto;
import com.severynm.tracker.task.domain.dto.UserDto;
import com.severynm.tracker.task.domain.entity.Task;
import com.severynm.tracker.task.domain.entity.TaskStatus;
import com.severynm.tracker.task.domain.entity.User;
import com.severynm.tracker.task.domain.mapper.TaskMapper;
import com.severynm.tracker.task.integration.RatingService;
import com.severynm.tracker.task.repository.TaskRepository;
import feign.RetryableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TaskServiceImpl implements TaskService {

    private final static Logger LOG = LoggerFactory.getLogger(TaskServiceImpl.class);

    //TODO: move to properties or db
    private final static int DEFAULT_RATING = 2;

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final RatingService ratingService;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper, RatingService ratingService) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.ratingService = ratingService;
    }

    @Override
    public TaskDto createTask(CreateTaskDto createTaskDto) {
        //TODO:use Aspect for logging
        LOG.info("Creating task {}", createTaskDto);
        Task task = taskMapper.fromCreateDto(createTaskDto);
        task.setStatus(TaskStatus.CREATED);
        task = taskRepository.save(task);
        return getTaskDto(task.getTaskId());
    }

    @Override
    public TaskDto updateTask(Long taskId, UpdateTaskDto updateTaskDto) {
        LOG.info("Updating task #[{}] {}", taskId, updateTaskDto);
        Task task = taskRepository
                .findById(taskId)
                .orElseThrow(NoSuchElementException::new);
        task.setStatus(updateTaskDto.getStatus());
        task.setExecutor(new User(updateTaskDto.getExecutorId()));
        taskRepository.save(task);
        return getTaskDto(taskId);
    }

    @Override
    public Page<TaskDto> findTasks(List<Long> departments, Pageable pageable) {
        Page<Task> tasks;
        if (departments == null || departments.isEmpty()) {
            tasks = taskRepository.findAll(pageable);
        } else {
            tasks = taskRepository.findByAuthorDepartmentDepartmentIdIn(departments, pageable);
        }
        Set<Long> users = tasks.stream()
                .flatMap(task -> Stream.concat(
                        Stream.of(task.getAuthor().getUserId()),
                        Optional.ofNullable(task.getExecutor()).map(User::getUserId).stream()))
                .collect(Collectors.toSet());

        Map<Long, Integer> rating = getRating(users);

        return tasks.map(task -> {
            TaskDto dto = taskMapper.toTaskDto(task);
            UserDto author = dto.getAuthor();
            author.setRating(rating.getOrDefault(author.getUserId(), DEFAULT_RATING));
            UserDto executor = dto.getExecutor();
            executor.setRating(rating.getOrDefault(executor.getUserId(), DEFAULT_RATING));
            return dto;
        });
    }

    @Override
    public TaskDetailsDto getTaskDetails(Long taskId) {
        return taskRepository
                .findById(taskId)
                .map(taskMapper::toTaskDetailsDto)
                .orElseThrow(NoSuchElementException::new);

    }

    private Map<Long, Integer> getRating(Set<Long> users) {
        try {
            return ratingService.getRating(users);
        } catch (RetryableException e) {
            LOG.error("Rating service not available", e);
            return Collections.emptyMap();
        }
    }

    private TaskDto getTaskDto(Long taskId) {
        return taskRepository
                .findById(taskId)
                .map((Task task) -> {
                    Long executorId = task.getExecutor().getUserId();
                    Long authorId = task.getAuthor().getUserId();
                    Map<Long, Integer> rating = getRating(new HashSet<>(Arrays.asList(executorId, authorId)));
                    TaskDto dto = taskMapper.toTaskDto(task);
                    dto.getAuthor().setRating(rating.getOrDefault(authorId, DEFAULT_RATING));
                    dto.getExecutor().setRating(rating.getOrDefault(executorId, DEFAULT_RATING));
                    return dto;
                })
                .orElseThrow(NoSuchElementException::new);
    }
}
