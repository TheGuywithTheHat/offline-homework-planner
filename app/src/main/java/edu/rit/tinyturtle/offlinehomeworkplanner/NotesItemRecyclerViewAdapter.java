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
 * {@link RecyclerView.Adapter} that can display a {@link Notes}
 */
public class NotesItemRecyclerViewAdapter extends RecyclerView.Adapter<NotesItemRecyclerViewAdapter.ViewHolder> {

    private final List<Notes> mValues;
    private final Parent parent;

    public NotesItemRecyclerViewAdapter(List<Notes> items, Parent parent) {
        mValues = items;
        this.parent = parent;
    }

    @Override
    public NotesItemRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parentGroup, int viewType) {
        View view = LayoutInflater.from(parentGroup.getContext())
                .inflate(R.layout.notes_list_item, parentGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        ImageButton overflow = ((ImageButton) view.findViewById(R.id.notes_list_overflow_button));
        overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
                popupMenu.getMenuInflater().inflate(R.menu.notes_overflow, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.notes_overflow_delete:
                                parent.deleteNote(viewHolder.mItem);
                                parent.openFragment(new NotesList());
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
    public void onBindViewHolder(final NotesItemRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mNameView.setText(mValues.get(position).getName());

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
                    parent.openFragment(NotesPage.newInstance(holder.mItem));
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
        public final TextView mDateView;
        public Notes mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = (TextView) view.findViewById(R.id.notes_list_name);
            mDateView = (TextView) view.findViewById(R.id.notes_list_date);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + ", " + mDateView.getText() + "'";
        }
    }
}
