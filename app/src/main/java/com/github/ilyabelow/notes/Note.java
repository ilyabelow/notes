package com.github.ilyabelow.notes;

import java.util.Date;

public class Note {
    private int id;
    private String title;
    private String text;
    private Date date;
    private int color;


    public Note(int id, String name, String text, Date date, int color) {
        this.id = id;
        this.title = name;
        this.text = text;
        this.date = date;
        this.color = color;
    }

    public String getText() {
        return text;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Date getDate() {
        return date;
    }

    public int getColor() {
        return color;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
