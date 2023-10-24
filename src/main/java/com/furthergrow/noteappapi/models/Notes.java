package com.furthergrow.noteappapi.models;

import java.time.LocalDateTime;
import java.util.Date;

public class Notes {
    Long id;
    String title;
    String contents;
    String userId;
    String createdDate;

    public Notes(Long id, String title, String contents, String userId, String createdDate) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.userId = userId;
        this.createdDate = createdDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String  getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
