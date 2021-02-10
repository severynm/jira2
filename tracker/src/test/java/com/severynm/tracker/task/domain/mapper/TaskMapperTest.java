package com.severynm.tracker.task.domain.mapper;

import com.severynm.tracker.task.domain.dto.CreateTaskDto;
import com.severynm.tracker.task.domain.dto.TaskDto;
import com.severynm.tracker.task.domain.entity.Department;
import com.severynm.tracker.task.domain.entity.Task;
import com.severynm.tracker.task.domain.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

    }

    @Test
    void toTaskDetailsDto() {
    }
}