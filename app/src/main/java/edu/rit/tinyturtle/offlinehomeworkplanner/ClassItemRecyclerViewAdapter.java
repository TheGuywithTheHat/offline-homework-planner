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
 * {@link RecyclerView.Adapter} that can display a {@link Course} and makes a call to the
 * specified {@link HomeScreen}.
 */
public class ClassItemRecyclerViewAdapter extends RecyclerView.Adapter<ClassItemRecyclerViewAdapter.ViewHolder> {

    private final List<Course> mValues;
    private final HomeScreen parent;

    public ClassItemRecyclerViewAdapter(List<Course> items, HomeScreen parent) {
        mValues = items;
        this.parent = parent;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parentGroup, int viewType) {
        View view = LayoutInflater.from(parentGroup.getContext())
                .inflate(R.layout.class_list_item, parentGroup, false);
        final ClassItemRecyclerViewAdapter.ViewHolder viewHolder = new ClassItemRecyclerViewAdapter.ViewHolder(view);
        ImageButton overflow = ((ImageButton) view.findViewById(R.id.class_list_overflow_button));
        overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
                popupMenu.getMenuInflater().inflate(R.menu.class_overflow, popupMenu.getMenu());
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
                                return true;
                            case R.id.class_overflow_delete:
                                parent.deleteCourse(viewHolder.mItem);
                                mValues.remove((viewHolder.mItem));
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
