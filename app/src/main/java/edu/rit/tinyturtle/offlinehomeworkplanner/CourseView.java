package edu.rit.tinyturtle.offlinehomeworkplanner;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * to handle interaction events.
 * Use the {@link CourseView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CourseView extends Fragment implements Parent {
    private static final String ARG_COURSE = "course";

    private Course course;

    private Parent parent;

    public CourseView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param course Course this view displays.
     * @return A new instance of fragment CourseView.
     */
    public static CourseView newInstance(Course course) {
        CourseView fragment = new CourseView();
        Bundle args = new Bundle();
        args.putSerializable(ARG_COURSE, course);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            course = (Course)getArguments().getSerializable(ARG_COURSE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_class_view, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Parent) {
            parent = (Parent) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        parent = null;
    }

    @Override
    public void openFragment(Fragment fragment) {
        parent.openFragment(fragment);
    }

    @Override
    public List<Course> getCourses() {
        return parent.getCourses();
    }

    @Override
    public List<Homework> getHomeworks() {
        return parent.getHomeworks();
    }

    @Override
    public List<Notes> getNotes() {
        return parent.getNotes();
    }

    @Override
    public HomeList getHomeListFrag() {
        return parent.getHomeListFrag();
    }

    @Override
    public NotesList getNotesListFrag() {
        return parent.getNotesListFrag();
    }

    @Override
    public HomeworkList getHomeworkListFrag() {
        return parent.getHomeworkListFrag();
    }
}
