package edu.rit.tinyturtle.offlinehomeworkplanner;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateHomework.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateHomework#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateHomework extends Fragment implements AdapterView.OnItemSelectedListener {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_HOMEWORK = "homework";

    private Homework homework;
    private Course course;
    private Spinner spinner;
    private ArrayAdapter<String>  adapter;

    private HomeScreen parent;


    public CreateHomework() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param hw homework to edit, if any
     * @return A new instance of fragment CreateHomework.
     */
    public static CreateHomework newInstance(Homework hw) {
        CreateHomework fragment = new CreateHomework();
        Bundle args = new Bundle();
        args.putSerializable(ARG_HOMEWORK, hw);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            homework = (Homework) getArguments().getSerializable(ARG_HOMEWORK);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_create_homework, container, false);
        View.OnClickListener dateClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getFragmentManager(), "datepicker");
            }
        };
        ImageButton dateButton = (ImageButton) view.findViewById(R.id.open_date_button);
        EditText editDate = (EditText) view.findViewById(R.id.homework_create_due_date);
        dateButton.setOnClickListener(dateClickListener);
        editDate.setOnClickListener(dateClickListener);

        spinner = (Spinner) view.findViewById(R.id.homework_create_course);
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
        view.findViewById(R.id.homework_create_save).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String dueDate = ((EditText) (view.findViewById(R.id.homework_create_due_date))).getText().toString();
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                try {
                    sdf.parse(dueDate);
                    if (homework == null) {
                        homework = new Homework();
                        parent.getHomeworks().add(homework);
                    }

                    homework.setName(((EditText) (view.findViewById(R.id.homework_create_title))).getText().toString());
                    homework.setCourse(course);
                    homework.setDueDate(((EditText) (view.findViewById(R.id.homework_create_due_date))).getText().toString());
                    parent.openFragment(parent.homeListFrag);
                } catch (ParseException e) {
                    Toast.makeText(getContext(), R.string.invalid_due_date, Toast.LENGTH_LONG).show();

                }
            }
        });
        if(homework != null) {
            ((EditText) view.findViewById(R.id.homework_create_title)).setText(homework.getName());
            ((EditText) view.findViewById(R.id.homework_create_due_date)).setText(homework.getDueDate());
            spinner.setSelection(adapter.getPosition(homework.getCourse().getName()));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HomeScreen) {
            parent = (HomeScreen) context;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
