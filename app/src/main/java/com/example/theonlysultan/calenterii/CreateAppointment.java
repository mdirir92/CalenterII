package com.example.theonlysultan.calenterii;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.TimePicker;

import android.widget.Toast;


import java.lang.String;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;

/**
 * Created by TheOnlySultan on 22/03/2018.
 */

public class CreateAppointment extends AppCompatActivity implements View.OnClickListener {
    DataBase dataHolder;
    TextView dateCalenter;
    TextView eventHeader;
    Button timeSetter;
    static EditText time;
    Button saveBtn;
    EditText intel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_activity);
        dataHolder = new DataBase(this);

        dateCalenter = (TextView) findViewById(R.id.dateCal);
        eventHeader = (TextView) findViewById(R.id.textInput);
        time = (EditText) findViewById(R.id.timeInput);

        intel = (EditText) findViewById(R.id.detailText);

        saveBtn = (Button) findViewById(R.id.saveBtn);
        timeSetter = (Button) findViewById(R.id.timeSetter);
        timeSetter.setOnClickListener(this);


        Intent incomingIntent = getIntent();
        String Date = incomingIntent.getStringExtra("date");
        dateCalenter.setText(Date);
        SaveIntel();


    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.timeSetter: {
                viewTimeSetterDialog(view);
            }
        }


    }
    public void SaveIntel() {
        saveBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String EVENT = eventHeader.getText().toString();
                        String DATE = dateCalenter.getText().toString();
                        String TIME = time.getText().toString();
                        String DETAILS = intel.getText().toString();
                        boolean isInserted = dataHolder.storeData(EVENT, DATE, TIME, DETAILS);
                        if (isInserted = true) {
                            Toast.makeText(CreateAppointment.this, "Appointment Set", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(CreateAppointment.this, MainActivity.class);
                            //intent.putExtra("event", EVENT);
                            intent.putExtra("date", DATE);
                            intent.putExtra("time", TIME);
                            intent.putExtra("details", DETAILS);
                            startActivity(intent);
                            dataHolder.close();
                        }
                        else {
                            Toast.makeText(CreateAppointment.this, "ERROR", Toast.LENGTH_LONG).show();
                            dataHolder.close();
                        }
                    }
                }
        );

    }
    public static class TimeFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            if (time.equals("")) {
                NumberFormat formatter = new DecimalFormat("00");
                time.setText(time.getText() + "" + formatter.format(hourOfDay) + ":" + formatter.format(minute));
                time.setFocusable(false);
            }
            else {
                time.setText("");
                NumberFormat formatter = new DecimalFormat("00");
                time.setText(time.getText() + "" + formatter.format(hourOfDay) + ":" + formatter.format(minute));
                time.setFocusable(false);

            }

        }
    }



    public void viewTimeSetterDialog(View view) {
        DialogFragment newFragment = new TimeFragment();
        newFragment.show(getFragmentManager(), "time picker");

    }
}
