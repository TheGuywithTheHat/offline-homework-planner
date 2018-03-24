package edu.rit.tinyturtle.offlinehomeworkplanner;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateCourse#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateCourse extends Fragment {
    private static final String ARG_COURSE = "course";

    private Course course;

    private HomeScreen parent;

    public CreateCourse() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param course Course to edit, if any.
     * @return A new instance of fragment CreateCourse.
     */
    public static CreateCourse newInstance(Course course) {
        CreateCourse fragment = new CreateCourse();
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
        final View view = inflater.inflate(R.layout.fragment_create_course, container, false);
        view.findViewById(R.id.create_course_save_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(course == null) {
                    course = new Course();
                    parent.getCourses().add(course);
                }

                course.setName(((EditText)(view.findViewById(R.id.course_create_name))).getText().toString());
                //course.setStart(LocalTime.parse(((EditText)(view.findViewById(R.id.course_create_start))).getText().toString()));
                parent.openFragment(parent.homeListFrag);
            }
        });

        if(null != course) {
            ((EditText) view.findViewById(R.id.course_create_name)).setText(course.getName());
        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HomeScreen) {
            parent = (HomeScreen) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        parent = null;
    }
}
