package com.example.dbtest;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by User on 2/28/2017.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "Goals";
    private static final String COL1 = "ID";
    private static final String COL2 = "Date";
    private static final String COL3 = "Time";
    private static final String COL4 = "Notification";


    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( "CREATE TABLE IF NOT EXISTS Goals (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Date TEXT, " +
                "Time TEXT, " +
                "Notification TEXT)"
        );
    }

//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                COL2 +" TimeDate" + COL3 + "TimeDate"  + COL4 + "TEXT)";
//        db.execSQL(createTable);
//    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String[] item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, item[0]);
        contentValues.put(COL3, item[1]);
        contentValues.put(COL4, item[2]);



        Log.d(TAG, "addData: Adding " + item[0]+ " , " +item[1]+ " , " +item[2] + " to " + TABLE_NAME);
        db.insert(TABLE_NAME, null, contentValues);
        int result = 0;

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Returns all the data from database
     * @return
     */
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    /**
     * Returns only the ID that matches the name passed in
     * @param id
     * @return
     */
    public Cursor getItemID(String notification){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME +
                " WHERE " + COL4 + " = '" + notification + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }
    public void updateDate(String newDate, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + "Date" +
                " = '" + newDate + "' WHERE " + "ID" + " = " + id;
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting name to " + newDate);
        db.execSQL(query);
    }
    public void updateTime(String newTime, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL3 +
                " = '" + newTime + "' WHERE " + COL1 + " = " + id;
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting name to " + newTime);
        db.execSQL(query);
    }
    public void updateNotification(String newNotification, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL4 +
                " = '" + newNotification + "' WHERE " + COL1 + " = " + id;
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting name to " + newNotification);
        db.execSQL(query);
    }

    public void deleteName(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "deleteName: Deleting from database.");
        db.execSQL( "DELETE FROM Goals WHERE ID = " + id);
    }
}

