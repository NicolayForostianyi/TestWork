package com.example.testTask.model;

import java.io.Serializable;

public class Note implements Serializable {
    private String ID;
    private String title;
    private String content;
public Note(){
ID = new String("");
title = new String ("");
content = new String("");

}
    public String getID() {
        return ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
