package com.example.homeschoolplanner;

import java.util.Date;

public class Assignment {
    Date due_date;
    String description;
    String title;
    String class_name;
    boolean marked_complete;
    boolean confirmed_complete;
    String owner;

    Assignment() {}

    Assignment(Date due_date, String description, String title, String class_name, boolean marked_complete, boolean confirmed_complete, String owner) {
        this.due_date = due_date;
        this.description = description;
        this.title = title;
        this.class_name = class_name;
        this.marked_complete = marked_complete;
        this.confirmed_complete = confirmed_complete;
        this.owner = owner;
    }

    public Date getDue_date() {
        return due_date;
    }

    public void setDue_date(Date due_date) {
        this.due_date = due_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public boolean isMarked_complete() {
        return marked_complete;
    }

    public void setMarked_complete(boolean marked_complete) {
        this.marked_complete = marked_complete;
    }

    public boolean isConfirmed_complete() {
        return confirmed_complete;
    }

    public void setConfirmed_complete(boolean confirmed_complete) {
        this.confirmed_complete = confirmed_complete;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }



}