package edu.rit.tinyturtle.offlinehomeworkplanner;

import android.support.v4.app.Fragment;

import java.util.List;

/**
 * Created by randy on 3/25/2018.
 */

public interface Parent {
    void openFragment(Fragment fragment);
    void changeTitle(String title);
    List<Course> getCourses();
    List<Homework> getHomeworks();
    List<Notes> getNotes();
    boolean deleteCourse(Course course);
    boolean deleteHomework(Homework homework);
    boolean deleteNote(Notes notes);
}
