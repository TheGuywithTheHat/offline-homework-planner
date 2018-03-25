package edu.rit.tinyturtle.offlinehomeworkplanner;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by iceem on 3/24/2018.
 */

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    static final String ARG_ID = "id";
    static final String ARG_HOURS = "hours";
    static final String ARG_MINUTES = "minutes";

    private static final SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
    private int timeId;
    private int hours;
    private int minutes;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            timeId = (int) getArguments().getSerializable(ARG_ID);
            hours = (int) getArguments().getSerializable(ARG_HOURS);
            minutes = (int) getArguments().getSerializable(ARG_MINUTES);

        }
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new TimePickerDialog(getActivity(), R.style.DatepickerTheme, this, hours, minutes, false);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
        String time = hours + ":" + minutes;
        sdf.applyPattern("hh:mm");
        try{
            Date date = sdf.parse(time);
            EditText editText = ((EditText) getActivity().findViewById(timeId));
            sdf.applyPattern("hh:mm a");
            if (editText != null) {
                editText.setText(sdf.format(date));
            }
        } catch (ParseException e){
            Toast.makeText(getContext(), R.string.invalid_start_end, Toast.LENGTH_LONG).show();
            sdf.applyPattern("hh:mm a");
        }
    }

}
