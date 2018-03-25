package edu.rit.tinyturtle.offlinehomeworkplanner;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.rit.tinyturtle.offlinehomeworkplanner.HomeworkList.OnListFragmentInteractionListener;
import edu.rit.tinyturtle.offlinehomeworkplanner.dummy.DummyContent.DummyItem;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class HomeworkItemRecyclerViewAdapter extends RecyclerView.Adapter<HomeworkItemRecyclerViewAdapter.ViewHolder> {

    private final List<Homework> mValues;
    private final HomeScreen parent;

    public HomeworkItemRecyclerViewAdapter(List<Homework> items, HomeScreen parent) {
        mValues = items;
        this.parent = parent;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.homework_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTitleView.setText(mValues.get(position).getName());
        holder.mDueDateView.setText(mValues.get(position).getDueDate());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != parent) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    parent.openFragment(CreateHomework.newInstance(holder.mItem));
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
        public final TextView mTitleView;
        public final TextView mDueDateView;
        public Homework mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitleView = (TextView) view.findViewById(R.id.title);
            mDueDateView = (TextView) view.findViewById(R.id.dueDate);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTitleView.getText() + ", " + mDueDateView.getText() + "'";
        }
    }
}
