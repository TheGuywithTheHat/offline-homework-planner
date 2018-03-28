package edu.rit.tinyturtle.offlinehomeworkplanner;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class HomeScreen extends AppCompatActivity implements Parent, View.OnTouchListener {
    private FragmentManager fragmentManager;

    List<Course> courses;
    List<Homework> homeworks;
    List<Notes> notes;
    private Fragment curFragment;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private BottomNavigationView mBottomNavigationView;
    private ActionBarDrawerToggle mToggle;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (curFragment != null) {
                openFragmentTransaction(curFragment);
                curFragment = null;
                return true;
            }
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    openFragmentTransaction(HomeList.newInstance(false));
                    return true;
                case R.id.navigation_notes:
                    openFragmentTransaction(NotesList.newInstance());
                    return true;
                case R.id.navigation_homework:
                    openFragmentTransaction(HomeworkList.newInstance(false));
                    return true;
            }
            return false;
        }
    };
    private NavigationView.OnNavigationItemSelectedListener mOnNavigationDrawerItemSelectedListener
            = new NavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_drawer_archives:
                    curFragment = HomeList.newInstance(true);
                    openFragment(HomeList.newInstance(true));
                    mDrawerLayout.closeDrawer(Gravity.START, true);
                    mNavigationView.setSelected(false);
                    return true;
                case R.id.navigation_drawer_settings:
                    mDrawerLayout.closeDrawer(Gravity.START, true);
                    return true;
                case R.id.navigation_drawer_completed:
                    curFragment = HomeworkList.newInstance(true);
                    openFragment(HomeworkList.newInstance(true));
                    mDrawerLayout.closeDrawer(Gravity.START, true);
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
        courses = new ArrayList<>();
        courses.add(new Course("default", "09:00 AM", "10:00 AM",
                new boolean[] {false, true, false, true, false, true, false}, Color.MAGENTA));
        homeworks = new ArrayList<>();
        homeworks.add(new Homework("default hw","3/30/2018", courses.get(0),  false));
        notes = new ArrayList<>();
        notes.add(new Notes("default note", courses.get(0), "blank"));

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener(mOnNavigationDrawerItemSelectedListener);
        mBottomNavigationView = findViewById(R.id.navigation_drawer_settings);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mBottomNavigationView.setSelectedItemId(R.id.navigation_home);
        findViewById(R.id.navigation_home).setOnTouchListener(this);
        findViewById(R.id.navigation_homework).setOnTouchListener(this);
        findViewById(R.id.navigation_notes).setOnTouchListener(this);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openFragmentTransaction(Fragment fragment) {
        curFragment = null;
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_frag_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void openFragment(Fragment fragment) {
        if (!(fragment instanceof Titleable)) {
            throw new IllegalArgumentException("Fragments must be Titleable to open them in the home screen");
        }
        curFragment = fragment;
        if (fragment.getClass() == HomeList.class){
            mBottomNavigationView.setSelectedItemId(R.id.navigation_home);
        } else if ((fragment.getClass() == HomeworkList.class)){
            mBottomNavigationView.setSelectedItemId(R.id.navigation_homework);
        } else if ((fragment.getClass() == NotesList.class)){
            mBottomNavigationView.setSelectedItemId(R.id.navigation_notes);
        } else {
            openFragmentTransaction(fragment);
        }
    }

    @Override
    public void changeTitle(String title) {
        getSupportActionBar().setTitle(title);
    }


    public List<Course> getCourses() {
        return courses;
    }

    public boolean deleteCourse(Course c){
        if (courses.remove(c)){
            ListIterator<Homework> hIter = homeworks.listIterator();
            while (hIter.hasNext()){
                if (hIter.next().getCourse() == c){
                    hIter.remove();
                }
            }
            ListIterator<Notes> nIter = notes.listIterator();
            while (nIter.hasNext()){
                if (nIter.next().getCourse() == c){
                    nIter.remove();
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public List<Homework> getHomeworks() {
        return homeworks;
    }

    @Override
    public boolean deleteHomework(Homework h){
        return homeworks.remove(h);
    }

    @Override
    public List<Notes> getNotes() {
        return notes;
    }

    @Override
    public boolean deleteNote(Notes n){
        return notes.remove(n);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return false;
    }
}
