package com.severynm.tracker.task.domain.mapper;

import com.severynm.tracker.task.domain.dto.AttachmentDto;
import com.severynm.tracker.task.domain.dto.CreateAttachmentDto;
import com.severynm.tracker.task.domain.entity.Attachment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AttachmentMapperTest {

    @Autowired
    private AttachmentMapper target;

    @Test
    void toAttachmentDto() throws MalformedURLException {
        Attachment attachment = new Attachment();
        attachment.setAttachmentId(3L);
        URL link = new URL("https://www.blablacar.com.ua/some-image");
        attachment.setLink(link);
        attachment.setName("some image");
        AttachmentDto attachmentDto = target.toAttachmentDto(attachment);
        assertNotNull(attachmentDto);
        assertEquals(3L, attachmentDto.getAttachmentId());
        assertEquals(link, attachmentDto.getLink());
        assertEquals("some image", attachmentDto.getName());
    }

    @Test
    void fromCreateAttachmentDto() throws MalformedURLException {
        URL link = new URL("https://www.blablacar.com.ua/some-image");
        CreateAttachmentDto createAttachmentDto = new CreateAttachmentDto();
        createAttachmentDto.setName("some image");
        createAttachmentDto.setLink(link);
        Attachment attachment = target.fromCreateAttachmentDto(createAttachmentDto);
        assertNotNull(attachment);
        assertNull(attachment.getAttachmentId());
        assertEquals("some image", attachment.getName());
        assertEquals(link, attachment.getLink());

    }
}