package edu.rit.tinyturtle.offlinehomeworkplanner;

import java.io.Serializable;

/**
 * Created by randy on 3/24/2018.
 */

public class Homework implements Serializable {
    private String name;

    public Homework() {

    }

    public Homework(String name) {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
