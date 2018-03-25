package edu.rit.tinyturtle.offlinehomeworkplanner;

import android.support.v4.app.Fragment;

import java.util.List;

import edu.rit.tinyturtle.offlinehomeworkplanner.Course;
import edu.rit.tinyturtle.offlinehomeworkplanner.HomeList;
import edu.rit.tinyturtle.offlinehomeworkplanner.Homework;
import edu.rit.tinyturtle.offlinehomeworkplanner.HomeworkList;
import edu.rit.tinyturtle.offlinehomeworkplanner.Notes;
import edu.rit.tinyturtle.offlinehomeworkplanner.NotesList;

/**
 * Created by randy on 3/25/2018.
 */

public interface Parent {
    void openFragment(Fragment fragment);
    List<Course> getCourses();
    List<Homework> getHomeworks();
    List<Notes> getNotes();
    HomeList getHomeListFrag();
    NotesList getNotesListFrag();
    HomeworkList getHomeworkListFrag();
}
