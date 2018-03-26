package edu.rit.tinyturtle.offlinehomeworkplanner;

import java.io.Serializable;

/**
 * Created by randy on 3/24/2018.
 */

public class Notes implements Serializable {
    private String name;
    private Course course;
    private String text;

    public Notes() {

    }

    public Notes(String name, Course course, String text) {
        this.name = name;
        this.course = course;
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
