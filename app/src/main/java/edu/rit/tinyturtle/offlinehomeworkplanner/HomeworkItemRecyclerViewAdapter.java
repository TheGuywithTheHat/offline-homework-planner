package edu.rit.tinyturtle.offlinehomeworkplanner;

import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;


/**
 * {@link RecyclerView.Adapter} that can display a {@link Homework}
 */
public class HomeworkItemRecyclerViewAdapter extends RecyclerView.Adapter<HomeworkItemRecyclerViewAdapter.ViewHolder> {

    private final List<Homework> mValues;
    private boolean completed;
    private final Parent parent;

    public HomeworkItemRecyclerViewAdapter(List<Homework> items, Parent parent, boolean completed) {
        mValues = items;
        this.parent = parent;
        this.completed = completed;
    }

    public void completeHomework(int pos){
        Homework h = mValues.get(pos);
        h.setCompleted(true);
        parent.openFragment(new HomeworkList());

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parentGroup, int viewType) {
        View view = LayoutInflater.from(parentGroup.getContext())
                .inflate(R.layout.homework_list_item, parentGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        ImageButton overflow = ((ImageButton) view.findViewById(R.id.homework_list_overflow_button));
        overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
                int menu = completed? R.menu.homework_complete_overflow : R.menu.homework_overflow;
                popupMenu.getMenuInflater().inflate(menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.homework_overflow_complete:
                                viewHolder.mItem.setCompleted(true);
                                if (parent instanceof CourseView)
                                    parent.getFragmentParent().openFragment(CourseView.newInstance(viewHolder.mItem.getCourse()));
                                else
                                    parent.openFragment(HomeworkList.newInstance(completed));
                                return true;
                            case R.id.homework_overflow_delete:
                                parent.deleteHomework(viewHolder.mItem);
                                if (parent instanceof CourseView)
                                    parent.getFragmentParent().openFragment(CourseView.newInstance(viewHolder.mItem.getCourse()));
                                else
                                    parent.openFragment(HomeworkList.newInstance(completed));
                                return true;
                            case R.id.homework_overflow_uncomplete:
                                viewHolder.mItem.setCompleted(false);
                                parent.openFragment(HomeworkList.newInstance(completed));
                                return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mNameView.setText(mValues.get(position).getName());
        holder.mDueDateView.setText(mValues.get(position).getDueDate());

        View colorSlot = holder.mView.findViewById(R.id.background_color_rectangle);
        Course course = holder.mItem.getCourse();
        if (course != null) {
            int color = course.getColor();
            colorSlot.setBackgroundColor(color);
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != parent) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    parent.openFragment(CreateHomework.newInstance(holder.mItem, null));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNameView;
        public final TextView mDueDateView;
        public Homework mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = (TextView) view.findViewById(R.id.title);
            mDueDateView = (TextView) view.findViewById(R.id.dueDate);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + ", " + mDueDateView.getText() + "'";
        }
    }
}
