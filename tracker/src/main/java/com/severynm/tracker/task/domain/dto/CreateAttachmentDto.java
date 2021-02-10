package com.severynm.tracker.task.domain.dto;

import java.net.URL;

public class CreateAttachmentDto {

    private String name;
    private URL link;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public URL getLink() {
        return link;
    }

    public void setLink(URL link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "CreateAttachmentDto{" +
                "name='" + name + '\'' +
                ", link=" + link +
                '}';
    }
}
