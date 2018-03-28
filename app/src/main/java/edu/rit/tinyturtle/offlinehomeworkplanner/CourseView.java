package edu.rit.tinyturtle.offlinehomeworkplanner;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
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
public class CourseView extends Fragment implements Parent, Titleable {
    private static final String ARG_COURSE = "course";

    private Course course;

    private Parent parent;
    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;

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
        View view = inflater.inflate(R.layout.fragment_class_view, container, false);
        mSectionsPageAdapter = new SectionsPageAdapter(getActivity().getSupportFragmentManager());
        mViewPager = (ViewPager) view.findViewById(R.id.course_view_pager);
        setupViewPager(mViewPager);
        final TabLayout tabLayout = (TabLayout) view.findViewById(R.id.course_view_tabs);
        tabLayout.setupWithViewPager(mViewPager);
        FloatingActionButton newHWFab = view.findViewById(R.id.tab_floating_action_button);
        newHWFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (tabLayout.getSelectedTabPosition() == 0)
                    openFragment(CreateHomework.newInstance(null, course));
                else
                    openFragment(CreateNotes.newInstance(null, course));
            }
        });

        parent.changeTitle(getTitle());

        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        CourseHomeworkTab homeworkTab = CourseHomeworkTab.newInstance(course);
        CourseNotesTab notesTab = CourseNotesTab.newInstance(course);
        mSectionsPageAdapter.addFragment(homeworkTab, getResources().getString(R.string.title_homework));
        mSectionsPageAdapter.addFragment(notesTab, getResources().getString(R.string.title_notes));
        viewPager.setAdapter(mSectionsPageAdapter);
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

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.course_view_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();    }

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
    public boolean deleteCourse(Course course) {
        return parent.deleteCourse(course);
    }

    @Override
    public boolean deleteHomework(Homework homework) {
        return parent.deleteHomework(homework);
    }

    @Override
    public boolean deleteNote(Notes notes) {
        return parent.deleteNote(notes);
    }

    @Override
    public String getTitle() {
        return course.getName();
    }

    @Override
    public void changeTitle(String title) {
        parent.changeTitle(title);
    }
}
