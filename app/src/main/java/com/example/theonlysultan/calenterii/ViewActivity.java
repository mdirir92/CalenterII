package com.example.theonlysultan.calenterii;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by TheOnlySultan on 22/03/2018.
 */

public class ViewActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView listAppointment;
    DataBase dataHolder;
    private String date;
    private TextView idView;
    private Button viewButton;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_activity);
        dataHolder = new DataBase(this);
        listAppointment = (ListView) findViewById(R.id.Viewlist);
        idView = (TextView) findViewById(R.id.ViewInput);
        viewButton = (Button) findViewById(R.id.editView);

        ListView();


    }


    public void ListView() {
        Intent incomingIntent = getIntent();
        String Date = incomingIntent.getStringExtra("selected date");
        date = Date.toString();
        Cursor cursor = dataHolder.getDeleteData(date);// getViewData is a method which i called twice because it can also b used in the same context.
        ArrayList<String> appointmentArray = new ArrayList<String>();

        while (cursor.moveToNext()) {
            appointmentArray.add(cursor.getString(0) + " " + cursor.getString(3) + " " + cursor.getString(1));
        }
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, appointmentArray);
        listAppointment.setAdapter(adapter);
    }

@Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.delBtn: {
                final Cursor deleteId = dataHolder.getID(idView.getText().toString());

                for (deleteId.moveToFirst(); !deleteId.isAfterLast(); deleteId.moveToNext()) {
                    if (deleteId.getString(0).toString().equals(idView.getText().toString())) {
                        DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case DialogInterface.BUTTON_POSITIVE:
                                        Intent intent = new Intent(ViewActivity.this, CreateAppointment.class);
                                        intent.putExtra("Title", deleteId.getString(1).toString());
                                        intent.putExtra("Date", deleteId.getString(2).toString());
                                        intent.putExtra("Time", deleteId.getString(3).toString());
                                        intent.putExtra("Details",deleteId.getString(4).toString());
                                        startActivity(intent);
                                        break;
                                    case DialogInterface.BUTTON_NEGATIVE:
                                        break;
                                }
                            }

                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage("Are you sure you want to edit " + deleteId.getString(1) + "?").setPositiveButton("Yes", dialogListener)
                                .setNegativeButton("No", dialogListener).show();

                       // dataHolder.updateAppointment();
                        break;
                    }

                    else {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage("ID Not Found")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        builder.setCancelable(true);
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                        break;
                    }
                }
            }
        }
    }
}


