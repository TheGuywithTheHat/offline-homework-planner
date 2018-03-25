package edu.rit.tinyturtle.offlinehomeworkplanner;

import android.widget.TimePicker;

/**
 * Created by iceem on 3/25/2018.
 */

public interface OnStartEndTimeSetListener {
    public void onTimeSet(TimePicker timePicker, int id, int hours, int minutes);
}
