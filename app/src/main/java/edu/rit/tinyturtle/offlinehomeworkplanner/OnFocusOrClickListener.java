package edu.rit.tinyturtle.offlinehomeworkplanner;

import android.view.View;

/**
 * Created by iceem on 3/25/2018.
 * This abstract class is used to call 'onClick' when either
 * the view is focused, or it is clicked
 */

public abstract class OnFocusOrClickListener implements View.OnFocusChangeListener, View.OnClickListener {
    @Override
    public void onFocusChange(View view, boolean b) {
        if (b){
            onClick(view);
        }
    }
}
