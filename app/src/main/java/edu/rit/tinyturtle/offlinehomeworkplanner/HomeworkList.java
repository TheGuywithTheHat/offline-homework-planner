package edu.rit.tinyturtle.offlinehomeworkplanner;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A fragment representing a list of Items.
 */
public class HomeworkList extends Fragment {
    private static final String ARG_COLUMN_COUNT = "column-count";

    private int mColumnCount = 1;
    private Parent parent;

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
        View view = inflater.inflate(R.layout.homework_list, container, false);

        // Set the adapter
        FloatingActionButton newHWFab = view.findViewById(R.id.create_homework_button);
        newHWFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                parent.openFragment(CreateHomework.newInstance(null));
            }
        });
        Context context = view.getContext();
        RecyclerView recyclerView = view.findViewById(R.id.homework_list);
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        HomeworkItemRecyclerViewAdapter adapter = new HomeworkItemRecyclerViewAdapter(parent.getHomeworks(), parent);
        recyclerView.setAdapter(adapter);
        ItemTouchHelper.Callback callback = new SwipeHelper(adapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);
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
}
