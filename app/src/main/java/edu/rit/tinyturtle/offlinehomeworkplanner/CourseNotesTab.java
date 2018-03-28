package edu.rit.tinyturtle.offlinehomeworkplanner;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;


public class CourseNotesTab extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_COURSE = "course";

    private Course course;
    private Parent parent;

    public CourseNotesTab() {
        // Required empty public constructor
    }

    /**
     * @param course course to set
     * @return A new instance of fragment CourseNotesTab.
     */
    public static CourseNotesTab newInstance(Course course) {
        CourseNotesTab fragment = new CourseNotesTab();
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
        final View view = inflater.inflate(R.layout.notes_list, container, false);

        List<Notes> notesList = new ArrayList<>(parent.getNotes());
        ListIterator<Notes> nIter = notesList.listIterator();
        while (nIter.hasNext()) {
            if (nIter.next().getCourse() != course)
                nIter.remove();
        }

        FloatingActionButton newNotesFab = view.findViewById(R.id.create_notes_button);
        container.removeView(newNotesFab);
        newNotesFab.setVisibility(View.GONE);

        RecyclerView recyclerView = view.findViewById(R.id.notes_list);
        Context context = view.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new NotesItemRecyclerViewAdapter(notesList, parent));
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
