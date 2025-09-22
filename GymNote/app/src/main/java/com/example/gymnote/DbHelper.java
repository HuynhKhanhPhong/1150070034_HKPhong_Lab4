package com.example.gymnote;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;
import com.example.gymnote.Workout;
public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "fitness.db";
    private static final int DB_VER = 1;


    private static final String TBL_WORKOUT = "workouts";
    private static final String COL_ID = "id";
    private static final String COL_TITLE = "title";
    private static final String COL_DATE = "date";
    private static final String COL_DURATION = "duration";
    private static final String COL_CALORIES = "calories";
    private static final String COL_TYPE = "type";


    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TBL_WORKOUT + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_TITLE + " TEXT, " +
                COL_DATE + " TEXT, " +
                COL_DURATION + " INTEGER, " +
                COL_CALORIES + " INTEGER, " +
                COL_TYPE + " TEXT" +
                ");";
        db.execSQL(sql);


// Insert sample data
        ContentValues v = new ContentValues();
        v.put(COL_TITLE, "Full Body Strength"); v.put(COL_DATE, "01-09-2025"); v.put(COL_DURATION, 50); v.put(COL_CALORIES, 450); v.put(COL_TYPE, "Strength");
        db.insert(TBL_WORKOUT, null, v);
        v.clear();
        v.put(COL_TITLE, "Morning Run"); v.put(COL_DATE, "02-09-2025"); v.put(COL_DURATION, 30); v.put(COL_CALORIES, 320); v.put(COL_TYPE, "Cardio");
        db.insert(TBL_WORKOUT, null, v);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TBL_WORKOUT);
        onCreate(db);
    }
// CRUD
    public long addWorkout(Workout w) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(COL_TITLE, w.getTitle());
        v.put(COL_DATE, w.getDate());
        v.put(COL_DURATION, w.getDuration());
        v.put(COL_CALORIES, w.getCalories());
        v.put(COL_TYPE, w.getType());
        long id = db.insert(TBL_WORKOUT, null, v);
        db.close();
        return id;
    }


    public int updateWorkout(Workout w) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(COL_TITLE, w.getTitle());
        v.put(COL_DATE, w.getDate());
        v.put(COL_DURATION, w.getDuration());
        v.put(COL_CALORIES, w.getCalories());
        v.put(COL_TYPE, w.getType());
        int rows = db.update(TBL_WORKOUT, v, COL_ID + "=?", new String[]{String.valueOf(w.getId())});
        db.close();
        return rows;
    }


    public int deleteWorkout(int id) {
        SQLiteDatabase db = getWritableDatabase();
        int r = db.delete(TBL_WORKOUT, COL_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return r;
    }


    public List<Workout> getAllWorkouts() {
        List<Workout> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TBL_WORKOUT, null, null, null, null, null, COL_DATE + " DESC");
        if (c!= null) {
            while (c.moveToNext()) {
                Workout w = new Workout();
                w.setId(c.getInt(c.getColumnIndexOrThrow(COL_ID)));
                w.setTitle(c.getString(c.getColumnIndexOrThrow(COL_TITLE)));
                w.setDate(c.getString(c.getColumnIndexOrThrow(COL_DATE)));
                w.setDuration(c.getInt(c.getColumnIndexOrThrow(COL_DURATION)));
                w.setCalories(c.getInt(c.getColumnIndexOrThrow(COL_CALORIES)));
                w.setType(c.getString(c.getColumnIndexOrThrow(COL_TYPE)));
                list.add(w);
            }
            c.close();
        }
        db.close();
        return list;
    }
}
