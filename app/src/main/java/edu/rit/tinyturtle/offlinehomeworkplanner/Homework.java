package edu.rit.tinyturtle.offlinehomeworkplanner;


import java.io.Serializable;

/**
 * Created by iceem on 3/24/2018.
 */

public class Homework implements Serializable {
    private String name;
    private String dueDate;
    private boolean completed;
    private Course course;

    public Homework() {

    }

    public Homework(String name, String dueDate, Course course, boolean completed) {
        this.name = name;
        this.dueDate = dueDate;
        this.course = course;
        this.completed = completed;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
