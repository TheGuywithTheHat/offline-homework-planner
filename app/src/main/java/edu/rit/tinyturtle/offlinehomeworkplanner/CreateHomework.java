package edu.rit.tinyturtle.offlinehomeworkplanner;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateHomework#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateHomework extends Fragment {
    private static final String ARG_HOMEWORK = "homework";

    private Homework homework;

    private HomeScreen parent;

    public CreateHomework() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param homework Homework to edit, if any.
     * @return A new instance of fragment CreateHomework.
     */
    public static CreateHomework newInstance(Homework homework) {
        CreateHomework fragment = new CreateHomework();
        Bundle args = new Bundle();
        args.putSerializable(ARG_HOMEWORK, homework);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            homework = (Homework)getArguments().getSerializable(ARG_HOMEWORK);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_create_homework, container, false);
        view.findViewById(R.id.create_homework_save_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(homework == null) {
                    homework = new Homework();
                    parent.getHomeworks().add(homework);
                }

                homework.setName(((EditText)(view.findViewById(R.id.homework_create_name))).getText().toString());
                parent.openFragment(parent.homeworkListFrag);
            }
        });

        //TODO: finish spinner adapter
        //((Spinner)view.findViewById(R.id.homework_create_course)).set

        if(null != homework) {
            ((EditText) view.findViewById(R.id.homework_create_name)).setText(homework.getName());
        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HomeScreen) {
            parent = (HomeScreen) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        parent = null;
    }
}
