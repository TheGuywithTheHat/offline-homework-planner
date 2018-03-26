package edu.rit.tinyturtle.offlinehomeworkplanner;

import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Course} and makes a call to the
 * specified {@link Parent}.
 */
public class CourseItemRecyclerViewAdapter extends RecyclerView.Adapter<CourseItemRecyclerViewAdapter.ViewHolder> {

    private final List<Course> mValues;
    private final boolean archived;
    private final Parent parent;

    public CourseItemRecyclerViewAdapter(List<Course> items, Parent parent, boolean archived) {
        mValues = new ArrayList<>();
        for (Course item: items){
            if (item.isArchived() == archived){
                mValues.add(item);
            }
        }
        this.archived = archived;
        this.parent = parent;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parentGroup, int viewType) {
        View view = LayoutInflater.from(parentGroup.getContext())
                .inflate(R.layout.class_list_item, parentGroup, false);
        final CourseItemRecyclerViewAdapter.ViewHolder viewHolder = new CourseItemRecyclerViewAdapter.ViewHolder(view);
        ImageButton overflow = ((ImageButton) view.findViewById(R.id.class_list_overflow_button));
        overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
                int menu = archived? R.menu.archive_overflow : R.menu.class_overflow;
                popupMenu.getMenuInflater().inflate(menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.class_overflow_edit:
                                // Remove the item from the adapter
                                parent.openFragment(CreateCourse.newInstance(viewHolder.mItem));
                                return true;
                            case R.id.class_overflow_archive:
                                viewHolder.mItem.setArchived(true);
                                parent.openFragment(new HomeList());
                                return true;
                            case R.id.class_overflow_delete:
                                parent.deleteCourse(viewHolder.mItem);
                                parent.openFragment(new HomeList());
                                return true;
                            case R.id.archive_overflow_delete:
                                parent.deleteCourse(viewHolder.mItem);
                                parent.openFragment(new HomeList());
                                return true;
                            case R.id.archive_overflow_unarchive:
                                viewHolder.mItem.setArchived(false);
                                HomeList homeList = new HomeList();
                                Bundle args = new Bundle();
                                args.putBoolean(HomeList.ARG_ARCHIVED, true);
                                homeList.setArguments(args);
                                parent.openFragment(homeList);
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
        View colorSlot = holder.mView.findViewById(R.id.class_item_box);
        int color = holder.mItem.getColor();
        colorSlot.setBackgroundColor(color);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != parent) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    parent.openFragment(CreateCourse.newInstance(holder.mItem));
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
        public Course mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = (TextView) view.findViewById(R.id.course_name);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }
    }
}
