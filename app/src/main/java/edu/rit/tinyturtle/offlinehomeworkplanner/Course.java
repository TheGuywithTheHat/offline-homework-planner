package edu.rit.tinyturtle.offlinehomeworkplanner;

import java.io.Serializable;

public class Course implements Serializable {
    private String name;
    private String start;
    private String end;
    private boolean[] days;
    private int colorHex;
    private boolean archived;

    public Course() {

    }

    public Course(String name, String start, String end, boolean[] days) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.days = days;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public boolean[] getDays() {
        return days;
    }

    public void setDays(boolean[] days) {
        this.days = days;
    }

    public boolean getDay(int i) {
        return days[i];
    }

    public void setDay(int i, boolean b) {
        this.days[i] = b;
    }

    public int getColor() {
        return colorHex;
    }

    public void setColor(int colorHex) {
        this.colorHex = colorHex;
    }


    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }
}
