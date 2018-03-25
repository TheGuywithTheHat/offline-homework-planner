package edu.rit.tinyturtle.offlinehomeworkplanner;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

/**
 * Created by iceem on 3/24/2018.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    static final String ARG_YEAR = "year";
    static final String ARG_MONTH = "month";
    static final String ARG_DAY = "day";

    private int year;
    private int month;
    private int day;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            year = (int) getArguments().getSerializable(ARG_YEAR);
            month = (int) getArguments().getSerializable(ARG_MONTH);
            day = (int) getArguments().getSerializable(ARG_DAY);
        }
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new DatePickerDialog(getActivity(), R.style.DatepickerTheme, this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        TextView dueDate = (TextView)getActivity().findViewById(R.id.homework_create_due_date);
        month++;
        dueDate.setText(month + "/" + day + "/" + year);
    }
}
