package edu.rit.tinyturtle.offlinehomeworkplanner;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by iceem on 3/26/2018.
 */

public abstract class OnTouchHideFragment extends Fragment implements View.OnTouchListener {
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        return false;
    }
}
