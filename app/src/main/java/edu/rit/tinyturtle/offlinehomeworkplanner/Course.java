package edu.rit.tinyturtle.offlinehomeworkplanner;

import java.io.Serializable;
import java.time.LocalTime;

public class Course implements Serializable {
    private String name;
    private LocalTime start;
    private LocalTime end;
    private boolean[] days;
    private int colorHex;

    public Course() {

    }

    public Course(String name, LocalTime start, LocalTime end, boolean[] days) {
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

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
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
}
