package com.severynm.tracker.task.repository;

import com.severynm.tracker.task.domain.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TaskRepository extends PagingAndSortingRepository<Task, Long> {

    Page<Task> findByAuthorDepartmentDepartmentIdIn(List<Long> departmentIds, Pageable pageable);

}
