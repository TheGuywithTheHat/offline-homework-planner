package edu.rit.tinyturtle.offlinehomeworkplanner;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * A fragment representing a list of Items.
 */
public class HomeworkList extends Fragment implements Titleable {
    private static final String ARG_COMPLETED = "completed";

    private boolean completed;
    private Parent parent;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public HomeworkList() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static HomeworkList newInstance(boolean completed) {
        HomeworkList fragment = new HomeworkList();
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
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homework_list, container, false);

        // Set the adapter
        FloatingActionButton newHWFab = view.findViewById(R.id.create_homework_button);
        if (completed){
            container.removeView(newHWFab);
            newHWFab.setVisibility(View.GONE);
        } else {
            newHWFab.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    parent.openFragment(CreateHomework.newInstance(null, null));
                }
            });
        }
        Context context = view.getContext();
        RecyclerView recyclerView = view.findViewById(R.id.homework_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        List<Homework> homeworkList = new ArrayList<>(parent.getHomeworks());
        ListIterator<Homework> hIter = homeworkList.listIterator();
        while (hIter.hasNext()) {
            Homework h = hIter.next();
            if (h.isCompleted() != completed || h.getCourse().isArchived())
                hIter.remove();
        }
        HomeworkItemRecyclerViewAdapter adapter = new HomeworkItemRecyclerViewAdapter(homeworkList, parent, completed);
        recyclerView.setAdapter(adapter);
        ItemTouchHelper.Callback callback = new SwipeHelper(adapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);

        parent.changeTitle(getTitle());

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        int menuId = completed ? R.menu.complete_menu : R.menu.due_menu;
        menuInflater.inflate(menuId, menu);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_complete:
                completeAllHomeworks();
                parent.openFragment(HomeworkList.newInstance(completed));
                return true;
            case R.id.menu_delete:
                deleteAllHomeworks();
                parent.openFragment(HomeworkList.newInstance(completed));

                return true;
        }
        return false;
    }

    private void completeAllHomeworks(){
        List<Homework> homeworkList = parent.getHomeworks();
        ListIterator<Homework> hIter = homeworkList.listIterator();
        while (hIter.hasNext()) {
           hIter.next().setCompleted(true);
        }
    }

    private void deleteAllHomeworks(){
        List<Homework> homeworkList = parent.getHomeworks();
        ListIterator<Homework> hIter = homeworkList.listIterator();
        while (hIter.hasNext()) {
            if (hIter.next().isCompleted()) {
                hIter.remove();
            }
        }
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
    public String getTitle() {
        String title = completed ? getString(R.string.completed_homework_title) : getString(R.string.homework_title);
        return title;
    }
}
