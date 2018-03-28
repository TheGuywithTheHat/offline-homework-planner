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
 * A fragment representing a list of Items.
 */
public class HomeworkList extends Fragment  implements Parent{
    private static final String ARG_COLUMN_COUNT = "column-count";

    private int mColumnCount = 1;
    private Parent parent;

    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public HomeworkList() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static HomeworkList newInstance(int columnCount) {
        HomeworkList fragment = new HomeworkList();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_view, container, false);

        mSectionsPageAdapter = new SectionsPageAdapter(getActivity().getSupportFragmentManager());
        mViewPager = (ViewPager) view.findViewById(R.id.course_view_pager);
        setupViewPager(mViewPager);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.course_view_tabs);
        tabLayout.setupWithViewPager(mViewPager);

        // Set the adapter
        FloatingActionButton newHWFab = view.findViewById(R.id.tab_floating_action_button);
        newHWFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openFragment(CreateHomework.newInstance(null, null));
            }
        });
        Context context = view.getContext();
        return view;
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

    private void setupViewPager(ViewPager viewPager) {
        HomeworkCompletedTab dueTab = HomeworkCompletedTab.newInstance(false);
        HomeworkCompletedTab completedTab = HomeworkCompletedTab.newInstance(true);
        mSectionsPageAdapter.addFragment(dueTab, getResources().getString(R.string.title_due));
        mSectionsPageAdapter.addFragment(completedTab, getResources().getString(R.string.title_completed));
        viewPager.setAdapter(mSectionsPageAdapter);
    }

    @Override
    public void openFragment(Fragment fragment) {

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.tab_view_frame_layout, fragment);
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
}
