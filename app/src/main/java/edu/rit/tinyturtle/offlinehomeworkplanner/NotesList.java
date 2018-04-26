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

/**
 * A fragment representing a list of Items.
 */
public class NotesList extends Fragment implements Titleable {
    private Parent parent;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public NotesList() {
    }

    public static NotesList newInstance() {
        NotesList fragment = new NotesList();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notes_list, container, false);

        FloatingActionButton newNotesFab = view.findViewById(R.id.create_notes_button);
        newNotesFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                parent.openFragment(CreateNotes.newInstance(null, null));
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.notes_list);
        Context context = view.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        List<Notes> notesList = new ArrayList<>(parent.getNotes());
        ListIterator<Notes> nIter = notesList.listIterator();
        while (nIter.hasNext()) {
            Notes n = nIter.next();
            if (n.getCourse().isArchived())
                nIter.remove();
        }
        recyclerView.setAdapter(new NotesItemRecyclerViewAdapter(notesList, parent));


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
        return "Notes";
    }
}
