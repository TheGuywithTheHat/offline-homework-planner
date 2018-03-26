package edu.rit.tinyturtle.offlinehomeworkplanner;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by iceem on 3/26/2018.
 */

public class SwipeHelper  extends ItemTouchHelper.SimpleCallback{
    private HomeworkItemRecyclerViewAdapter adapter;

    public SwipeHelper(int dragData, int swipeData) {
        super(dragData, swipeData);
    }

    public SwipeHelper(HomeworkItemRecyclerViewAdapter adapter){
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.adapter = adapter;
    }
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        adapter.completeHomework(viewHolder.getAdapterPosition());
    }
}
