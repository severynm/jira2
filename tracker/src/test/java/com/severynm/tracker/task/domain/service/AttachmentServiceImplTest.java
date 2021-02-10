package com.severynm.tracker.task.domain.service;

import com.severynm.tracker.task.domain.dto.CreateAttachmentDto;
import com.severynm.tracker.task.domain.entity.Attachment;
import com.severynm.tracker.task.domain.mapper.AttachmentMapper;
import com.severynm.tracker.task.repository.AttachmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
class AttachmentServiceImplTest {

    @Mock
    private AttachmentRepository attachmentRepository;

    @Autowired
    @Spy
    private AttachmentMapper attachmentMapper;

    AttachmentServiceImpl target;

    @BeforeEach
    void init() {
        target = new AttachmentServiceImpl(attachmentRepository, attachmentMapper);
    }

    @Test
    void createAttachment() throws MalformedURLException {
        CreateAttachmentDto createAttachmentDto = new CreateAttachmentDto();
        createAttachmentDto.setName("attached");
        URL link = new URL("https://google.com");
        createAttachmentDto.setLink(link);

        target.createAttachment(3L, createAttachmentDto);

        Attachment expectedAttachment = new Attachment();
        expectedAttachment.setLink(link);
        expectedAttachment.setName("attached");
        expectedAttachment.setTaskId(3L);
        verify(attachmentRepository).save(expectedAttachment);
    }
}