package edu.rit.tinyturtle.offlinehomeworkplanner;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import edu.rit.tinyturtle.offlinehomeworkplanner.dummy.DummyContent;

public class HomeScreen extends AppCompatActivity implements HomeList.OnListFragmentInteractionListener,
        NotesList.OnListFragmentInteractionListener, HomeworkList.OnListFragmentInteractionListener {

    private TextView mTextMessage;
    FragmentManager fragmentManager;
    HomeList homeListFrag;
    NotesList notesListFrag;
    HomeworkList homeworkListFrag;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    openFragment(homeListFrag);
                    return true;
                case R.id.navigation_notes:
                    openFragment(notesListFrag);
                    return true;
                case R.id.navigation_homework:
                    openFragment(homeworkListFrag);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        fragmentManager = getSupportFragmentManager();
        homeListFrag = new HomeList();
        homeworkListFrag = new HomeworkList();
        notesListFrag = new NotesList();

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onListFragmentInteraction(Course item) {

    }

    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_frag_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }
}
