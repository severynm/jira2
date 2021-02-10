package com.severynm.tracker.task.repository;

import com.severynm.tracker.task.domain.entity.Attachment;
import org.springframework.data.repository.CrudRepository;

public interface AttachmentRepository extends CrudRepository<Attachment, Long> {

}
