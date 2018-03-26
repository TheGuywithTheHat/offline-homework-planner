package edu.rit.tinyturtle.offlinehomeworkplanner;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
 * Use the {@link CreateCourse#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateCourse extends OnTouchHideFragment implements ColorPickerCallback{
    static final String TAG = "create_course";
    private static final String ARG_COURSE = "course";
    private static final int[] DAY_BUTTONS = {R.id.sun_button, R.id.mon_button,
            R.id.tue_button, R.id.wed_button, R.id.thu_button, R.id.fri_button, R.id.sat_button};
    private static final SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");



    private Course course;
    private int colorHex;
    private View colorView;
    private ColorPicker cp;


    private Parent parent;

    public CreateCourse() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param course Course to edit, if any.
     * @return A new instance of fragment CreateCourse.
     */
    public static CreateCourse newInstance(Course course) {
        CreateCourse fragment = new CreateCourse();
        Bundle args = new Bundle();
        args.putSerializable(ARG_COURSE, course);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            course = (Course)getArguments().getSerializable(ARG_COURSE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_create_course, container, false);
        colorHex = getResources().getColor(R.color.colorAccent);
        for (final int i: new int[] {R.id.course_create_start, R.id.course_create_end}) {
            OnFocusOrClickListener focusOrClickListener = new OnFocusOrClickListener() {
                @Override
                public void onClick(View view) {
                    DialogFragment timePicker = new TimePickerFragment();
                    Bundle args = new Bundle();
                    Calendar c = Calendar.getInstance();
                    int hours = 0;
                    int minutes = 0;
                    try{
                        c.setTime(sdf.parse(((EditText) view.findViewById(i)).getText().toString()));
                        hours = c.get(Calendar.HOUR_OF_DAY);
                        minutes = c.get(Calendar.MINUTE);
                    } catch (ParseException e) { }
                    args.putInt(TimePickerFragment.ARG_ID, i);
                    args.putInt(TimePickerFragment.ARG_HOURS, hours);
                    args.putInt(TimePickerFragment.ARG_MINUTES, minutes);
                    timePicker.setArguments(args);
                    timePicker.show(getFragmentManager(), "datepicker");
                }
            };
            EditText editText = ((EditText) view.findViewById(i));
            editText.setOnFocusChangeListener(focusOrClickListener);
            editText.setOnClickListener(focusOrClickListener);
            editText.setOnTouchListener(this);
            view.setOnTouchListener(this);

        }
        View.OnClickListener colorPickerClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cp = new ColorPicker(getActivity(), Color.red(colorHex), Color.green(colorHex), Color.blue(colorHex));
                cp.show();
                cp.setCallback(CreateCourse.this);
            }
        };
        ImageButton pickerButton = (ImageButton) view.findViewById(R.id.color_picker_button);
        colorView = (View) view.findViewById(R.id.color_rectangle);
        pickerButton.setOnClickListener(colorPickerClickListener);
        colorView.setOnClickListener(colorPickerClickListener);
        colorView.setOnTouchListener(this);
        for (int i = 0; i < DAY_BUTTONS.length; i++) {
            view.findViewById(DAY_BUTTONS[i]).setOnTouchListener(this);
        }
        Button saveButton = view.findViewById(R.id.create_course_save_button);
        saveButton.setOnTouchListener(this);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                try{
                    Date start = sdf.parse(((EditText)(view.findViewById(R.id.course_create_start))).getText().toString());
                    Date end = sdf.parse(((EditText)(view.findViewById(R.id.course_create_end))).getText().toString());
                    if (start.compareTo(end) > 0)
                        throw new ParseException("", 0);
                    if(course == null) {
                        course = new Course();
                        parent.getCourses().add(course);
                    }
                    course.setName(((EditText)(view.findViewById(R.id.course_create_name))).getText().toString());
                    course.setStart(sdf.format(start));
                    course.setEnd(sdf.format(end));
                    course.setDays(getDays(view));
                    course.setColor(colorHex);
                    parent.openFragment(parent.getHomeListFrag());
                } catch (ParseException e) {
                    Toast.makeText(getContext(), R.string.invalid_start_end, Toast.LENGTH_LONG).show();
                }
            }
        });

        if(null != course) {
            ((EditText) view.findViewById(R.id.course_create_name)).setText(course.getName());
            ((View) view.findViewById(R.id.color_rectangle)).setBackgroundColor(course.getColor());
            ((EditText) view.findViewById(R.id.course_create_start)).setText(course.getStart());
            ((EditText) view.findViewById(R.id.course_create_end)).setText(course.getEnd());
            setDays(view, course.getDays());
        }

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
    public void onColorChosen(int color) {
        colorHex = color;
        colorView.setBackgroundColor(color);
        cp.dismiss();

    }

    /**
     * Get which toggle day buttons are selected
     * @param view current view
     * @return boolean array of values checked
     */
    public boolean[] getDays(View view){
        boolean[] values = new boolean[7];
        for (int i = 0; i < DAY_BUTTONS.length; i++) {
            values[i] = ((ToggleButton) view.findViewById(DAY_BUTTONS[i])).isChecked();
        }
        return values;
    }

    /**
     * Set checked for the course's days
     * @param view current view
     * @param values boolean array of values checked
     */
    public void setDays(View view, boolean[] values){
        for (int i = 0; i < DAY_BUTTONS.length; i++) {
            ((ToggleButton) view.findViewById(DAY_BUTTONS[i])).setChecked(values[i]);
        }
    }



}
