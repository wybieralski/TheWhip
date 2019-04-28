package com.example.whipx1;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import java.util.Calendar;


public class GoalActivity extends AppCompatActivity {

    private static final String TAG = GoalActivity.class.getSimpleName();
    private TextView mDate,mNotification,mTime;
    private EditText mNotification2;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    public TimePickerDialog timePickerDialog;
    private Calendar calendar;
    private int currentHour,currentMinute;
    private String amPm,notification;
    private Button mSaveButton;
    DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);

        mDate =  (TextView) findViewById(R.id.selectDate);
        mTime = (TextView) findViewById(R.id.selectTime);
        mNotification = (TextView)  findViewById(R.id.selectNotification);
        mNotification2 = (EditText)  findViewById(R.id.selectNotification2);
        mSaveButton = (Button) findViewById(R.id.saveButton);

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = mNotification2.getText().toString();
                if (mNotification2.length() != 0) {
                    AddData(newEntry);
                    mNotification2.setText("");
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),"Put notification here!",Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });

        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        GoalActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                mDate.setText(date);
            }
        };

        mTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);
                timePickerDialog = new TimePickerDialog(GoalActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if (hourOfDay >= 12) {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        mTime.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm);
                    }
                }, currentHour, currentMinute, false);
                timePickerDialog.show();
            }
        });

        mNotification2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            notification = mNotification2.getText().toString().trim();
            }
        });
    }
    public void AddData(String newEntry) {
        boolean insertData = mDatabaseHelper.addData(newEntry);

        if (insertData) {
            Toast toast = Toast.makeText(getApplicationContext(),"Succes!",Toast.LENGTH_SHORT);
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),"Something failes!",Toast.LENGTH_SHORT);
        }
    }
}