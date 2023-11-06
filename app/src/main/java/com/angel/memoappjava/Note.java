package com.angel.memoappjava;

import java.util.Date;

public class Note {
    private String title;
    private String description;
    private Date date;

    public Note() {
        //empty constructor needed
    }

    public Note(String title, String description, Date date) {
        this.title = title;
        this.description = description;
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public Date getDate(){
        return date;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
