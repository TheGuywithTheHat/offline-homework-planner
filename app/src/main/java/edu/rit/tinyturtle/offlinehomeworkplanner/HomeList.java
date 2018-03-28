package edu.rit.tinyturtle.offlinehomeworkplanner;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * A fragment representing a list of Items.
 */
public class HomeList extends Fragment implements Titleable {
    static final String ARG_ARCHIVED = "archived";

    private int mColumnCount = 1;
    private Parent parent;
    private boolean archived;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public HomeList() {
        archived = false;
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static HomeList newInstance(boolean archived) {
        HomeList fragment = new HomeList();
        Bundle args = new Bundle();
        args.putBoolean(ARG_ARCHIVED, archived);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            archived = getArguments().getBoolean(ARG_ARCHIVED);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.class_list, container, false);


        FloatingActionButton newCourseFab = view.findViewById(R.id.create_course_button);
        newCourseFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                parent.openFragment(CreateCourse.newInstance(null));
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.class_list);
        Context context = view.getContext();
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        List<Course> courseList = new ArrayList<>(parent.getCourses());
        ListIterator<Course> cIter = courseList.listIterator();
        while (cIter.hasNext()) {
            if (cIter.next().isArchived() != archived)
                cIter.remove();
        }
        recyclerView.setAdapter(new CourseItemRecyclerViewAdapter(courseList, parent, archived));

        parent.changeTitle(getTitle());

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Parent) {
            parent = (Parent)context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        parent = null;
    }

    @Override
    public String getTitle() {
        return "Classes";
    }
}
