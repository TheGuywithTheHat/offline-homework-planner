package edu.rit.tinyturtle.offlinehomeworkplanner;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by iceem on 3/28/2018.
 */

public class MyDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "HomeworkPlanner.db";
    private static final int DATABASE_VERSION = 1;
    public static final String HOMEWORK_TABLE_NAME = "homework";
    public static final String HOMEWORK_COLUMN_ID = "id";
    public static final String HOMEWORK_COLUMN_NAME = "name";
    public static final String HOMEWORK_COLUMN_COURSE_ID = "course_id";
    public static final String HOMEWORK_COLUMN_COMPLETED = "completed";
    public static final String HOMEWORD_COLUMN_DUE_DATE = "due_date";
    public static final String COURSE_TABLE_NAME = "course";
    public static final String COURSE_COLUMN_ID = "id";
    public static final String COURSE_COLUMN_NAME = "name";
    public static final String COURSE_COLUMN_COLOR = "color";
    public static final String COURSE_COLUMN_START_TIME = "start_time";
    public static final String COURSE_COLUMN_END_TIME = "end_time";
    public static final String NOTE_TABLE_NAME = "note";
    public static final String NOTE_COLUMN_ID = "id";
    public static final String NOTE_COLUMN_NAME = "name";
    public static final String NOTE_COLUMN_COURSE_ID = "course_id";
    public static final String NOTE_NOTE_DATA_TABLE_NAME = "note_note_data";
    public static final String NOTE_NOTE_DATA_COLUMN_ID = "id";
    public static final String NOTE_NOTE_DATA_COLUMN_NOTE_ID = "note_id";
    public static final String NOTE_NOTE_DATA_COLUMN_NOTE_DATA_ID = "note_data_id";
    public static final String NOTE_DATA_TABLE_NAME = "note_data";
    public static final String NOTE_DATA_COLUMN_ID = "id";
    public static final String NOTE_DATA_COLUMN_TYPE = "type";
    public static final String NOTE_DATA_COLUMN_data = "data";
    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
