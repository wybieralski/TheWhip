package com.example.dbtest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Alarm extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper;
    DateFormat yourDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.list_layout);
//        mDatabaseHelper = new DatabaseHelper(this);
//
//
//        DateFormat yourDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        try {
//            date = yourDateFormat.parse(dateTime);
//        } catch (ParseException e) {
//            Log.e(TAG, "Parsing date time failed", e);
//        }
//    }


}
