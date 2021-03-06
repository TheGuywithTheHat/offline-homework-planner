package edu.rit.tinyturtle.offlinehomeworkplanner;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;


public class CourseHomeworkTab extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_COURSE = "course";

    private Course course;

    private Parent parent;

    public CourseHomeworkTab() {
        // Required empty public constructor
    }

    /**
     * @param course course to set
     * @return A new instance of fragment CourseHomeworkTab.
     */
    // TODO: Rename and change types and number of parameters
    public static CourseHomeworkTab newInstance(Course course) {
        CourseHomeworkTab fragment = new CourseHomeworkTab();
        Bundle args = new Bundle();
        args.putSerializable(ARG_COURSE, course);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            course = (Course) getArguments().getSerializable(ARG_COURSE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.homework_list, container, false);
        List<Homework> homeworkList = new ArrayList<>(parent.getHomeworks());
        ListIterator<Homework> hIter = homeworkList.listIterator();
        while (hIter.hasNext()) {
            Homework h = hIter.next();
            if (h.getCourse() != course || h.isCompleted())
                hIter.remove();
        }
        HomeworkItemRecyclerViewAdapter adapter = new HomeworkItemRecyclerViewAdapter(homeworkList, parent, false);
        FloatingActionButton newHWFab = view.findViewById(R.id.create_homework_button);
        container.removeView(newHWFab);
        newHWFab.setVisibility(View.GONE);
        Context context = view.getContext();
        RecyclerView recyclerView = view.findViewById(R.id.homework_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
        ItemTouchHelper.Callback callback = new SwipeHelper(adapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        List<Fragment> l = getFragmentManager().getFragments();
        Parent f = (Parent) context;
        for (Fragment frag: l){
            if (frag instanceof CourseView){
                f = (Parent) frag;
                break;
            }
        }
        if (context instanceof Parent) {
            parent = f;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        parent = null;
    }
}
