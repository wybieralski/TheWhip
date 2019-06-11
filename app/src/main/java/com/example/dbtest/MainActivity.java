package com.example.dbtest;


import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    DatabaseHelper mDatabaseHelper;
    private Button mSaveButton, btnViewData;
    private EditText mNotification;
    private String[] dbPackage = new String[3];
    private TextView mDate, mTime;
    public TimePickerDialog timePickerDialog;
    private Calendar calendar;
    private int currentHour,currentMinute;
    private String amPm,notification;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDate =  (TextView) findViewById(R.id.selectDate);
        mTime = (TextView) findViewById(R.id.selectTime);
        mNotification = (EditText)  findViewById(R.id.selectNotification);
        checkIfValid();
        mSaveButton = (Button) findViewById(R.id.btnAdd);
        btnViewData = (Button) findViewById(R.id.btnView);
        mDatabaseHelper = new DatabaseHelper(this);

        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        MainActivity.this,
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
                timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
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

        mNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notification = mNotification.getText().toString().trim();
            }
        });
        // HER IS THE MAGIC
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbPackage[0]= mDate.getText().toString();
                dbPackage[1]= mTime.getText().toString();
                dbPackage[2]= mNotification.getText().toString();
                if (dbPackage[0].length() != 0 && dbPackage[1].length() != 0 && dbPackage[2].length() != 0 ) {
                    AddData(dbPackage);
                    setAlarm();
                    mNotification.setText("");
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),"Put notification here!",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        btnViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListDataActivity.class);
                startActivity(intent);
            }
        });

    }

    private void checkIfValid() {
        Cursor data = mDatabaseHelper.getData();
        Date currentTime = Calendar.getInstance().getTime();
        DateFormat yourDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        while (data.moveToNext()) {
            String data_from_db = data.getString(2);
            Integer ID = data.getInt(1);
            String chuj = "15/12/3222";
            String dateString = yourDateFormat.format(new Date());
//            Date strDate = sdf.parse(data_from_db);
//
//            if (currentTime).after(strDate)) {
//                mDatabaseHelper.deleteName(ID);
//            }
        }
    }
    public void setAlarm(){
        Calendar cal = Calendar.getInstance();
        String date= mDate.getText().toString();
        String time= mTime.getText().toString();
        dbPackage[2]= mNotification.getText().toString();
        String datetime =  date+" "+time.substring(0,time.length()-2);
        Date datetimeToParse;
        Cursor data = mDatabaseHelper.getData();
        DateFormat yourDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        try {
            datetimeToParse = yourDateFormat.parse(datetime);
            cal.setTime(datetimeToParse);
        } catch (ParseException e) {
            Log.e(TAG, "Parsing date time failed", e);
        }

        Calendar current = Calendar.getInstance();
        if(cal.compareTo(current) <= 0)
        {
        //The set Date/Time already passed
            Toast.makeText(getApplicationContext(), "Invalid Date/Time", Toast.LENGTH_LONG).show();
        }
        else{
//            Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
//            PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), RQS_1, intent, 0);
//            AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
//            alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);
            String x = time.substring(0,time.length()-2)+":00";
            Time td = Time.valueOf(x);
            long timel = td.getTime();
            //getting the alarm manager
            AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            //creating a new intent specifying the broadcast receiver
            Intent i = new Intent(this, AlarmReceiver.class);

            //creating a pending intent using the intent
            PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);

            //setting the repeating alarm that will be fired every day
            am.setRepeating(AlarmManager.RTC, timel, AlarmManager.INTERVAL_DAY, pi);
            Toast.makeText(this, "Alarm is set", Toast.LENGTH_SHORT).show();
        }
    }
    public void AddData(String[] dbPackage) {
        boolean insertData = mDatabaseHelper.addData(dbPackage);

        if (insertData) {
            toastMessage("Data Successfully Inserted!");
        } else {
            toastMessage("Something went wrong");
        }
    }
    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}