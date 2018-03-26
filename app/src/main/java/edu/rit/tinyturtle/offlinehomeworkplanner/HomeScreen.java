package edu.rit.tinyturtle.offlinehomeworkplanner;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class HomeScreen extends AppCompatActivity implements Parent {
    private FragmentManager fragmentManager;
    private HomeList homeListFrag;
    private NotesList notesListFrag;
    private HomeworkList homeworkListFrag;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    private List<Course> courses;
    private List<Homework> homeworks;
    private List<Notes> notes;

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

        BottomNavigationView navigation = findViewById(R.id.navigation_drawer_settings);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)){

        }
        return super.onOptionsItemSelected(item);
    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_frag_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
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

    public List<Homework> getHomeworks() {
        return homeworks;
    }
    public boolean deleteHomework(Homework h){
        return homeworks.remove(h);
    }

    public List<Notes> getNotes() {
        return notes;
    }
    public boolean deleteNote(Notes n){
        return notes.remove(n);
    }

    public HomeList getHomeListFrag() {
        return homeListFrag;
    }

    public NotesList getNotesListFrag() {
        return notesListFrag;
    }

    public HomeworkList getHomeworkListFrag() {
        return homeworkListFrag;
    }
}
