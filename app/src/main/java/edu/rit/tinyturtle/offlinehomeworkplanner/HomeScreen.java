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

    List<Course> courses = new ArrayList<>();
    List<Homework> homeworks = new ArrayList<>();
    List<Notes> notes = new ArrayList<>();
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
        Course math = new Course("Math", "08:00 AM", "08:55 AM", new boolean[] {false, true, true, true, true, true, false}, Color.MAGENTA);
        Course biology = new Course("Biology", "09:00 AM", "09:55 AM", new boolean[] {false, true, true, true, true, true, false}, Color.LTGRAY);
        Course english = new Course("English", "10:00 AM", "10:55 AM", new boolean[] {false, true, true, true, true, true, false}, Color.BLUE);
        Course health = new Course("Health", "11:00 AM", "11:55 AM", new boolean[] {false, true, true, true, true, true, false}, Color.rgb(20, 200, 100));
        Homework mathHW = new Homework("Factoring Homework", "04/25/2018", math, false);
        Homework biologyHW = new Homework("Cells Homework", "04/19/2018", biology, false);
        Notes biologyNotes = new Notes("Bio notes 1", biology, "Mitochondria is the powerhouse of the cell");
        Notes mathNotes = new Notes("Slope notes", math, "y=mx+b\na^2+b^2=c^2");
        Notes englishNotes = new Notes("The English rule", english, getResources().getString(R.string.i_before_e));
        courses.add(math);
        courses.add(biology);
        courses.add(english);
        courses.add(health);
        notes.add(mathNotes);
        notes.add(biologyNotes);
        notes.add(englishNotes);
        homeworks.add(mathHW);
        homeworks.add(biologyHW);




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

    @Override
    public Parent getFragmentParent(){
        return this;
    }
}
