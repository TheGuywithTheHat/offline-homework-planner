package edu.rit.tinyturtle.offlinehomeworkplanner;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * to handle interaction events.
 * Use the {@link CreateHomework#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateHomework extends OnTouchHideFragment implements AdapterView.OnItemSelectedListener, TimePickerDialog.OnTimeSetListener {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_HOMEWORK = "homework";
    private static final String ARG_COURSE = "course";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");


    private Homework homework;
    private Course course;
    private Spinner spinner;
    private ArrayAdapter<String>  adapter;

    private Parent parent;


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
    public static CreateHomework newInstance(Homework hw, Course c) {
        CreateHomework fragment = new CreateHomework();
        Bundle args = new Bundle();
        args.putSerializable(ARG_HOMEWORK, hw);
        args.putSerializable(ARG_COURSE, c);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            homework = (Homework) getArguments().getSerializable(ARG_HOMEWORK);
            course = (Course) getArguments().getSerializable(ARG_COURSE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_create_homework, container, false);
        OnFocusOrClickListener dateClickListener = new OnFocusOrClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                Calendar c = Calendar.getInstance();
                try{
                    Date date = sdf.parse(((EditText) getActivity().findViewById(R.id.homework_create_due_date)).getText().toString());
                    c.setTime(date);
                } catch (ParseException e) {}
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                Bundle args = new Bundle();
                args.putInt(DatePickerFragment.ARG_YEAR, year);
                args.putInt(DatePickerFragment.ARG_MONTH, month);
                args.putInt(DatePickerFragment.ARG_DAY, day);
                datePicker.setArguments(args);
                datePicker.show(getFragmentManager(), "datepicker");
            }
        };
        ImageButton dateButton = (ImageButton) view.findViewById(R.id.open_date_button);
        EditText editDate = (EditText) view.findViewById(R.id.homework_create_due_date);
        dateButton.setOnClickListener(dateClickListener);
        editDate.setOnFocusChangeListener(dateClickListener);
        editDate.setOnClickListener(dateClickListener);
        editDate.setOnTouchListener(this);
        dateButton.setOnTouchListener(this);


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
        spinner.setOnTouchListener(this);
        Button saveButton = view.findViewById(R.id.homework_create_save);
        saveButton.setOnTouchListener(this);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String title = ((EditText) view.findViewById(R.id.homework_create_title)).getText().toString();
                if (title.equals("")){
                    Toast.makeText(getContext(), R.string.invalid_title, Toast.LENGTH_LONG).show();
                    return;
                }
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
                    parent.openFragment(parent.getHomeworkListFrag());
                } catch (ParseException e) {
                    Toast.makeText(getContext(), R.string.invalid_due_date, Toast.LENGTH_LONG).show();

                }
            }
        });
        if(homework != null) {
            ((EditText) view.findViewById(R.id.homework_create_title)).setText(homework.getName());
            ((EditText) view.findViewById(R.id.homework_create_due_date)).setText(homework.getDueDate());
            if(homework.getCourse() != null) {
                spinner.setSelection(adapter.getPosition(homework.getCourse().getName()));
            }
        } else if (course != null) {
            spinner.setSelection(adapter.getPosition(course.getName()));
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

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {

    }
}
