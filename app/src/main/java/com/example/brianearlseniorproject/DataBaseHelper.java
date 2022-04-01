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
    public static final String SWIM_SPEED = "SWIM_SPEED";

    public static final String BIKE_TABLE = "BIKE_TABLE";
    public static final String BIKE_ID = "BIKE_ID";
    public static final String BIKE_DATE = "BIKE_DATE";
    public static final String BIKE_TIME = "BIKE_TIME";
    public static final String BIKE_DISTANCE = "BIKE_DISTANCE";
    public static final String BIKE_SPEED = "BIKE_SPEED";

    public static final String RUN_TABLE = "RUN_TABLE";
    public static final String RUN_ID = "RUN_ID";
    public static final String RUN_DATE = "RUN_DATE";
    public static final String RUN_TIME = "RUN_TIME";
    public static final String RUN_DISTANCE = "RUN_DISTANCE";
    public static final String RUN_SPEED = "RUN_SPEED";

    public static final String SWIM_GOAL_TABLE = "SWIM_GOAL_TABLE";
    public static final String SWIM_GOAL_ID = "SWIM_GOAL_ID";
    public static final String SWIM_GOAL_TIME = "SWIM_GOAL_TIME";
    public static final String SWIM_GOAL_DISTANCE = "SWIM_GOAL_DISTANCE";

    public static final String BIKE_GOAL_TABLE = "BIKE_GOAL_TABLE";
    public static final String BIKE_GOAL_ID = "BIKE_GOAL_ID";
    public static final String BIKE_GOAL_TIME = "BIKE_GOAL_TIME";
    public static final String BIKE_GOAL_DISTANCE = "BIKE_GOAL_DISTANCE";

    public static final String RUN_GOAL_TABLE = "RUN_GOAL_TABLE";
    public static final String RUN_GOAL_ID = "RUN_GOAL_ID";
    public static final String RUN_GOAL_TIME = "RUN_GOAL_TIME";
    public static final String RUN_GOAL_DISTANCE = "RUN_GOAL_DISTANCE";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "TriTracker.db", null, 1);
    }

    //this is called the first time a database is accessed. There should be code in here to create a new database.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSwimTableStatement = "CREATE TABLE IF NOT EXISTS " + SWIM_TABLE + "(" + SWIM_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + SWIM_DATE + " TEXT, " + SWIM_TIME +
                " TEXT, " + SWIM_LAPS + " REAL, " + SWIM_LAP_DISTANCE + " REAL, " +
                SWIM_DISTANCE + " REAL, " + SWIM_SPEED + " REAL)";

        String createBikeTableStatement = "CREATE TABLE IF NOT EXISTS " + BIKE_TABLE + "(" + BIKE_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + BIKE_DATE + " TEXT, " + BIKE_TIME +
                " TEXT, " + BIKE_DISTANCE + " REAL, " + BIKE_SPEED + " REAL)";

        String createRunTableStatement = "CREATE TABLE IF NOT EXISTS " + RUN_TABLE + "(" + RUN_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + RUN_DATE + " TEXT, " + RUN_TIME +
                " TEXT, " + RUN_DISTANCE + " REAL, " + RUN_SPEED + " REAL)";

        String createSwimGoalTableStatement = "CREATE TABLE IF NOT EXISTS " + SWIM_GOAL_TABLE + "(" + SWIM_GOAL_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + SWIM_GOAL_TIME + " TEXT, " + SWIM_GOAL_DISTANCE + " REAL)";

        String createBikeGoalTableStatement = "CREATE TABLE IF NOT EXISTS " + BIKE_GOAL_TABLE + "(" + BIKE_GOAL_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + BIKE_GOAL_TIME + " TEXT, " + BIKE_GOAL_DISTANCE + " REAL)";

        String createRunGoalTableStatement = "CREATE TABLE IF NOT EXISTS " + RUN_GOAL_TABLE + "(" + RUN_GOAL_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + RUN_GOAL_TIME + " TEXT, " + RUN_GOAL_DISTANCE + " REAL)";

        db.execSQL(createSwimTableStatement);
        db.execSQL(createBikeTableStatement);
        db.execSQL(createRunTableStatement);
        db.execSQL(createSwimGoalTableStatement);
        db.execSQL(createBikeGoalTableStatement);
        db.execSQL(createRunGoalTableStatement);
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
        cv.put(SWIM_SPEED, swimModel.getSwim_speed());

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
        cv.put(BIKE_SPEED, bikeModel.getBike_speed());

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
        cv.put(RUN_SPEED, runModel.getRun_speed());

        long insert = db.insert(RUN_TABLE, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean addSwimGoal(SwimGoalModel swimGoalModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(SWIM_GOAL_TIME, swimGoalModel.getSwimGoal_time());
        cv.put(SWIM_GOAL_DISTANCE, swimGoalModel.getSwimGoal_distance());

        if (!swimGoalExists()) {
            long insert = db.insert(SWIM_GOAL_TABLE, null, cv);
            if (insert == -1) {
                return false;
            } else {
                return true;
            }
        }
        return db.update(SWIM_GOAL_TABLE, cv, SWIM_GOAL_ID + "=1", null) > 0;
    }

    private boolean swimGoalExists() {
        boolean result = false;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select " + SWIM_GOAL_ID + " FROM " + SWIM_GOAL_TABLE + " WHERE " + SWIM_GOAL_ID + "=1", null);
        result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }

    public boolean addBikeGoal(BikeGoalModel bikeGoalModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(BIKE_GOAL_TIME, bikeGoalModel.getBikeGoal_time());
        cv.put(BIKE_GOAL_DISTANCE, bikeGoalModel.getBikeGoal_distance());

        if (!bikeGoalExists()) {
            long insert = db.insert(BIKE_GOAL_TABLE, null, cv);
            if (insert == -1) {
                return false;
            } else {
                return true;
            }
        }
        return db.update(BIKE_GOAL_TABLE, cv, BIKE_GOAL_ID + "=1", null) > 0;
    }

    private boolean bikeGoalExists() {
        boolean result = false;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select " + BIKE_GOAL_ID + " FROM " + BIKE_GOAL_TABLE + " WHERE " + BIKE_GOAL_ID + "=1", null);
        result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }

    public boolean addRunGoal(RunGoalModel runGoalModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(RUN_GOAL_TIME, runGoalModel.getRunGoal_time());
        cv.put(RUN_GOAL_DISTANCE, runGoalModel.getRunGoal_distance());

        if (!runGoalExists()) {
            long insert = db.insert(RUN_GOAL_TABLE, null, cv);
            if (insert == -1) {
                return false;
            } else {
                return true;
            }
        }
        return db.update(RUN_GOAL_TABLE, cv, RUN_GOAL_ID + "=1", null) > 0;
    }

    private boolean runGoalExists() {
        boolean result = false;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select " + RUN_GOAL_ID + " FROM " + RUN_GOAL_TABLE + " WHERE " + RUN_GOAL_ID + "=1", null);
        result = cursor.getCount() > 0;
        cursor.close();
        return result;
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
        String queryString = "SELECT * FROM " + SWIM_TABLE + " ORDER BY " + SWIM_DATE + " ASC";
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
                Float swimSpeed = cursor.getFloat(6);

                SwimModel newSwimWorkout = new SwimModel(swimID, swimDate, swimTime, swimLaps, swimLapDistance, swimDistance, swimSpeed);
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

    public SwimModel getBestSwimWorkout() {
        SwimModel bestSwimWorkout = new SwimModel(-1, "", "", 0.0F, 0.0F, 0.0F,0.0F);
        //get data from database
        String queryString = "SELECT * FROM SWIM_TABLE WHERE SWIM_SPEED = (SELECT MAX(SWIM_SPEED) FROM SWIM_TABLE)";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int swimID = cursor.getInt(0);
            String swimDate = cursor.getString(1);
            String swimTime = cursor.getString(2);
            Float swimLaps = cursor.getFloat(3);
            Float swimLapDistance = cursor.getFloat(4);
            Float swimDistance = cursor.getFloat(5);
            Float swimSpeed = cursor.getFloat(6);

            bestSwimWorkout = new SwimModel(swimID, swimDate, swimTime, swimLaps, swimLapDistance, swimDistance, swimSpeed);
        }
        //close both the cursor and the db when done.
        cursor.close();
        db.close();
        return bestSwimWorkout;
    }

    public List<BikeModel> getAllBikeWorkouts() {
        List<BikeModel> returnList = new ArrayList<>();
        //get data from database
        String queryString = "SELECT * FROM " + BIKE_TABLE + " ORDER BY " + BIKE_DATE + " ASC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            //loop through the cursor (result set) and create new bike objects. Put them into the return list.
            do {
                int bikeID = cursor.getInt(0);
                String bikeDate = cursor.getString(1);
                String bikeTime = cursor.getString(2);
                Float bikeDistance = cursor.getFloat(3);
                Float bikeSpeed = cursor.getFloat(4);

                BikeModel newBikeWorkout = new BikeModel(bikeID, bikeDate, bikeTime, bikeDistance, bikeSpeed);
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

    public BikeModel getBestBikeWorkout() {
        BikeModel bestBikeWorkout = new BikeModel(-1, "", "", 0.0F, 0.0F);
        //get data from database
        String queryString = "SELECT * FROM BIKE_TABLE WHERE BIKE_SPEED = (SELECT MAX(BIKE_SPEED) FROM BIKE_TABLE)";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int bikeID = cursor.getInt(0);
            String bikeDate = cursor.getString(1);
            String bikeTime = cursor.getString(2);
            Float bikeDistance = cursor.getFloat(3);
            Float bikeSpeed = cursor.getFloat(4);

            bestBikeWorkout = new BikeModel(bikeID, bikeDate, bikeTime, bikeDistance, bikeSpeed);
        }
        //close both the cursor and the db when done.
        cursor.close();
        db.close();
        return bestBikeWorkout;
    }

    public List<RunModel> getAllRunWorkouts() {
        List<RunModel> returnList = new ArrayList<>();
        //get data from database
        String queryString = "SELECT * FROM " + RUN_TABLE + " ORDER BY " + RUN_DATE + " ASC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            //loop through the cursor (result set) and create new run objects. Put them into the return list.
            do {
                int runID = cursor.getInt(0);
                String runDate = cursor.getString(1);
                String runTime = cursor.getString(2);
                Float runDistance = cursor.getFloat(3);
                Float runSpeed = cursor.getFloat(4);

                RunModel newRunWorkout = new RunModel(runID, runDate, runTime, runDistance, runSpeed);
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

    public RunModel getBestRunWorkout() {
        RunModel bestRunWorkout = new RunModel(-1, "", "", 0.0F, 0.0F);
        //get data from database
        String queryString = "SELECT * FROM RUN_TABLE WHERE RUN_SPEED = (SELECT MAX(RUN_SPEED) FROM RUN_TABLE)";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int runID = cursor.getInt(0);
            String runDate = cursor.getString(1);
            String runTime = cursor.getString(2);
            Float runDistance = cursor.getFloat(3);
            Float runSpeed = cursor.getFloat(4);

            bestRunWorkout = new RunModel(runID, runDate, runTime, runDistance, runSpeed);
        }
        //close both the cursor and the db when done.
        cursor.close();
        db.close();
        return bestRunWorkout;
    }

    public SwimGoalModel getSwimGoal() {
        SwimGoalModel swimGoalModel = new SwimGoalModel(-1, "", 0.0F);
        //get data from database
        String queryString = "SELECT * FROM SWIM_GOAL_TABLE WHERE SWIM_GOAL_ID = 1";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int swimGoalID = cursor.getInt(0);
            String swimGoalTime = cursor.getString(1);
            Float swimGoalDistance = cursor.getFloat(2);

            swimGoalModel = new SwimGoalModel(swimGoalID, swimGoalTime, swimGoalDistance);
        }
        //close both the cursor and the db when done.
        cursor.close();
        db.close();
        return swimGoalModel;
    }

    public BikeGoalModel getBikeGoal() {
        BikeGoalModel bikeGoalModel = new BikeGoalModel(-1, "", 0.0F);
        //get data from database
        String queryString = "SELECT * FROM BIKE_GOAL_TABLE WHERE BIKE_GOAL_ID = 1";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int bikeGoalID = cursor.getInt(0);
            String bikeGoalTime = cursor.getString(1);
            Float bikeGoalDistance = cursor.getFloat(2);

            bikeGoalModel = new BikeGoalModel(bikeGoalID, bikeGoalTime, bikeGoalDistance);
        }
        //close both the cursor and the db when done.
        cursor.close();
        db.close();
        return bikeGoalModel;
    }

    public RunGoalModel getRunGoal() {
        RunGoalModel runGoalModel = new RunGoalModel(-1, "", 0.0F);
        //get data from database
        String queryString = "SELECT * FROM RUN_GOAL_TABLE WHERE RUN_GOAL_ID = 1";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int runGoalID = cursor.getInt(0);
            String runGoalTime = cursor.getString(1);
            Float runGoalDistance = cursor.getFloat(2);

            runGoalModel = new RunGoalModel(runGoalID, runGoalTime, runGoalDistance);
        }
        //close both the cursor and the db when done.
        cursor.close();
        db.close();
        return runGoalModel;
    }
}