package edu.rit.tinyturtle.offlinehomeworkplanner;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.pes.androidmaterialcolorpickerdialog.ColorPicker;
import com.pes.androidmaterialcolorpickerdialog.ColorPickerCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotesPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotesPage extends Fragment {
    private static final String ARG_NOTES = "notes";

    private Notes notes;

    private Parent parent;

    public NotesPage() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param notes Notes to edit, if any.
     * @return A new instance of fragment NotesPage.
     */
    public static NotesPage newInstance(Notes notes) {
        NotesPage fragment = new NotesPage();
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
        final View view = inflater.inflate(R.layout.fragment_notes_page, container, false);
        if(null != notes) {
            ((EditText) view.findViewById(R.id.note_text)).setText(notes.getText());
        }
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        notes.setText(((EditText)getView().findViewById(R.id.note_text)).getText().toString());

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
