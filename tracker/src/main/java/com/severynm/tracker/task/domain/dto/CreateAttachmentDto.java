package com.severynm.tracker.task.domain.dto;

import java.net.URL;
import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateAttachmentDto that = (CreateAttachmentDto) o;
        return Objects.equals(name, that.name) && Objects.equals(link, that.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, link);
    }

    @Override
    public String toString() {
        return "CreateAttachmentDto{" +
                "name='" + name + '\'' +
                ", link=" + link +
                '}';
    }
}
