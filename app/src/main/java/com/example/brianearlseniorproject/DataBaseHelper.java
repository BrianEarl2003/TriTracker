package com.example.brianearlseniorproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String SWIM_TABLE = "SWIM_TABLE";
    public static final String SWIM_ID = "SWIM_ID";
    public static final String SWIM_DATE = "SWIM_DATE";
    public static final String SWIM_TIME = "SWIM_TIME";
    public static final String SWIM_LAPS = "SWIM_LAPS";
    public static final String SWIM_LAP_DISTANCE = "SWIM_LAP_DISTANCE";
    public static final String SWIM_DISTANCE = "SWIM_DISTANCE";

    public static final String BIKE_TABLE = "BIKE_TABLE";
    public static final String BIKE_ID = "BIKE_ID";
    public static final String BIKE_DATE = "BIKE_DATE";
    public static final String BIKE_TIME = "BIKE_TIME";
    public static final String BIKE_DISTANCE = "BIKE_DISTANCE";

    public static final String RUN_TABLE = "RUN_TABLE";
    public static final String RUN_ID = "RUN_ID";
    public static final String RUN_DATE = "RUN_DATE";
    public static final String RUN_TIME = "RUN_TIME";
    public static final String RUN_DISTANCE = "RUN_DISTANCE";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "TriTracker.db", null, 1);
    }

    //this is called the first time a database is accessed. There should be code in here to create a new database.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSwimTableStatement = "CREATE TABLE IF NOT EXISTS " + SWIM_TABLE + "(" + SWIM_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + SWIM_DATE + " TEXT, " + SWIM_TIME +
                " TEXT, " + SWIM_LAPS + " REAL, " + SWIM_LAP_DISTANCE + " REAL, " +
                SWIM_DISTANCE + " REAL)";

        String createBikeTableStatement = "CREATE TABLE IF NOT EXISTS " + BIKE_TABLE + "(" + BIKE_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + BIKE_DATE + " TEXT, " + BIKE_TIME +
                " TEXT, " + BIKE_DISTANCE + " REAL)";

        String createRunTableStatement = "CREATE TABLE IF NOT EXISTS " + RUN_TABLE + "(" + RUN_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + RUN_DATE + " TEXT, " + RUN_TIME +
                " TEXT, " + RUN_DISTANCE + " REAL)";

        db.execSQL(createSwimTableStatement);
        db.execSQL(createBikeTableStatement);
        db.execSQL(createRunTableStatement);
    }

    //this is called if the database version number changes. It prevents previous users apps from breaking when you change the database design.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    public boolean addSwimWorkout(SwimModel swimModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(SWIM_DATE, swimModel.getSwim_date());
        cv.put(SWIM_TIME, swimModel.getSwim_time());
        cv.put(SWIM_LAPS, swimModel.getSwim_laps());
        cv.put(SWIM_LAP_DISTANCE, swimModel.getSwim_lapDistance());
        cv.put(SWIM_DISTANCE, swimModel.getSwim_distance());

        long insert = db.insert(SWIM_TABLE, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean addBikeWorkout(BikeModel bikeModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(BIKE_DATE, bikeModel.getBike_date());
        cv.put(BIKE_TIME, bikeModel.getBike_time());
        cv.put(BIKE_DISTANCE, bikeModel.getBike_distance());

        long insert = db.insert(BIKE_TABLE, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean addRunWorkout(RunModel runModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(RUN_DATE, runModel.getRun_date());
        cv.put(RUN_TIME, runModel.getRun_time());
        cv.put(RUN_DISTANCE, runModel.getRun_distance());

        long insert = db.insert(RUN_TABLE, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean deleteSwim(SwimModel swimModel) {
        //find swimModel in the database. if it found, delete it and return true.
        //if it is not found, return false
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + SWIM_TABLE + " WHERE " + SWIM_ID + " = " + swimModel.getSwim_id();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public boolean deleteBike(BikeModel bikeModel) {
        //find bikeModel in the database. if it found, delete it and return true.
        //if it is not found, return false
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + BIKE_TABLE + " WHERE " + BIKE_ID + " = " + bikeModel.getBike_id();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public boolean deleteRun(RunModel runModel) {
        //find runModel in the database. if it found, delete it and return true.
        //if it is not found, return false
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + RUN_TABLE + " WHERE " + RUN_ID + " = " + runModel.getRun_id();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }
    
    public List<SwimModel> getAllSwimWorkouts() {
        List<SwimModel> returnList = new ArrayList<>();
        //get data from database
        String queryString = "SELECT * FROM " + SWIM_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            //loop through the cursor (result set) and create new swim objects. Put them into the return list.
            do {
                int swimID = cursor.getInt(0);
                String swimDate = cursor.getString(1);
                String swimTime = cursor.getString(2);
                Float swimLaps = cursor.getFloat(3);
                Float swimLapDistance = cursor.getFloat(4);
                Float swimDistance = cursor.getFloat(5);

                SwimModel newSwimWorkout = new SwimModel(swimID, swimDate, swimTime, swimLaps, swimLapDistance, swimDistance);
                returnList.add(newSwimWorkout);
            } while (cursor.moveToNext());
        } else {
            //failure. do not add anything to the list.
        }
        //close both the cursor and the db when done.
        cursor.close();
        db.close();
        return returnList;
    }

    public List<BikeModel> getAllBikeWorkouts() {
        List<BikeModel> returnList = new ArrayList<>();
        //get data from database
        String queryString = "SELECT * FROM " + BIKE_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            //loop through the cursor (result set) and create new bike objects. Put them into the return list.
            do {
                int bikeID = cursor.getInt(0);
                String bikeDate = cursor.getString(1);
                String bikeTime = cursor.getString(2);
                Float bikeDistance = cursor.getFloat(3);

                BikeModel newBikeWorkout = new BikeModel(bikeID, bikeDate, bikeTime, bikeDistance);
                returnList.add(newBikeWorkout);
            } while (cursor.moveToNext());
        } else {
            //failure. do not add anything to the list.
        }
        //close both the cursor and the db when done.
        cursor.close();
        db.close();
        return returnList;
    }

    public List<RunModel> getAllRunWorkouts() {
        List<RunModel> returnList = new ArrayList<>();
        //get data from database
        String queryString = "SELECT * FROM " + RUN_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            //loop through the cursor (result set) and create new run objects. Put them into the return list.
            do {
                int runID = cursor.getInt(0);
                String runDate = cursor.getString(1);
                String runTime = cursor.getString(2);
                Float runDistance = cursor.getFloat(3);

                RunModel newRunWorkout = new RunModel(runID, runDate, runTime, runDistance);
                returnList.add(newRunWorkout);
            } while (cursor.moveToNext());
        } else {
            //failure. do not add anything to the list.
        }
        //close both the cursor and the db when done.
        cursor.close();
        db.close();
        return returnList;
    }
}