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

/**
 * A fragment representing a list of Items.
 */
public class HomeList extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private HomeScreen parent;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public HomeList() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static HomeList newInstance(int columnCount) {
        HomeList fragment = new HomeList();
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
        recyclerView.setAdapter(new ClassItemRecyclerViewAdapter(parent.getCourses(), parent));

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof HomeScreen) {
            parent = (HomeScreen)context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        parent = null;
    }
}
