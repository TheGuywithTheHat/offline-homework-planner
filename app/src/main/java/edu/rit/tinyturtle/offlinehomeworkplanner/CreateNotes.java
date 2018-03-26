package edu.rit.tinyturtle.offlinehomeworkplanner;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateNotes#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateNotes extends OnTouchHideFragment implements AdapterView.OnItemSelectedListener{
    private static final String ARG_NOTES = "notes";

    private Notes notes;
    private Course course;
    private Spinner spinner;
    private ArrayAdapter<String> adapter;

    private Parent parent;

    public CreateNotes() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param notes Notes to edit, if any.
     * @return A new instance of fragment CreateNotes.
     */
    public static CreateNotes newInstance(Notes notes) {
        CreateNotes fragment = new CreateNotes();
        Bundle args = new Bundle();
        args.putSerializable(ARG_NOTES, notes);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            notes = (Notes)getArguments().getSerializable(ARG_NOTES);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_create_notes, container, false);
        spinner = (Spinner) view.findViewById(R.id.notes_create_course);
        // Create an ArrayAdapter using the string array and a default spinner layout
        List<Course> courses = parent.getCourses();
        List<String> courseNames = new ArrayList<>();
        for (Course course : courses) {
            courseNames.add(course.getName());
        }

        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, courseNames);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.add(getString(R.string.select_class));
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setSelection(adapter.getCount()-1);
        spinner.setOnItemSelectedListener(this);
        spinner.setOnTouchListener(this);
        Button saveButton = view.findViewById(R.id.create_notes_save_button);
        saveButton.setOnTouchListener(this);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(notes == null) {
                    notes = new Notes();
                    parent.getNotes().add(notes);
                }

                notes.setName(((EditText)(view.findViewById(R.id.notes_create_name))).getText().toString());
                notes.setCourse(course);
                parent.openFragment(NotesPage.newInstance(notes));
            }
        });

        //TODO: finish spinner adapter
        //((Spinner)view.findViewById(R.id.notes_create_course)).set

        if(notes != null) {
            if(notes.getCourse() != null) {
                ((EditText) view.findViewById(R.id.notes_create_name)).setText(notes.getName());
            }

        }
        view.setOnTouchListener(this);

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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (!spinner.getSelectedItem().equals(getString(R.string.select_class))) {
            course = parent.getCourses().get(i);
            adapter.remove(getString(R.string.select_class));

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
