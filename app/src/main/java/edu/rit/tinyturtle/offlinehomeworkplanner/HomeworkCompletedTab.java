package edu.rit.tinyturtle.offlinehomeworkplanner;

import android.content.Context;
import android.os.Bundle;
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


public class HomeworkCompletedTab extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_COMPLETED = "completed";

    private boolean completed;
    private Parent parent;

    public HomeworkCompletedTab() {
        // Required empty public constructor
    }

    /**
     * @param completed type of homework to display
     * @return A new instance of fragment CourseHomeworkTab.
     */
    public static HomeworkCompletedTab newInstance(boolean completed) {
        HomeworkCompletedTab fragment = new HomeworkCompletedTab();
        Bundle args = new Bundle();
        args.putBoolean(ARG_COMPLETED, completed);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            completed = getArguments().getBoolean(ARG_COMPLETED);
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
            if (hIter.next().isCompleted() != completed)
                hIter.remove();
        }
        HomeworkItemRecyclerViewAdapter adapter = new HomeworkItemRecyclerViewAdapter(homeworkList, parent);
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
        List<Fragment> l = getActivity().getSupportFragmentManager().getFragments();
        Parent f = (Parent) context;
        for (Fragment frag: l){
            if (frag.getClass() == HomeworkList.class){
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
