package com.example.theonlysultan.calenterii;

import android.support.v7.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

/**
 * Created by TheOnlySultan on 22/03/2018.
 */

public class DeleteAppointment extends AppCompatActivity implements View.OnClickListener{
    private ListView listAppointment;
    DataBase dataHolder;
    private String date;
    private TextView input;
    private Button delete;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_activity);
        dataHolder = new DataBase(this);
        listAppointment = (ListView) findViewById(R.id.list);
        input = (TextView) findViewById(R.id.Input);
        delete = (Button) findViewById(R.id.DeleteBtn);
        delete.setOnClickListener(this);

        ListView();


    }


    public void ListView() {
        String Date = getIntent().getStringExtra("selected date");
        date = Date.toString();
        Cursor cursor = dataHolder.getDeleteData(date);
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
            case R.id.DeleteBtn: {
                Cursor deleteId = dataHolder.getID(input.getText().toString());

                for (deleteId.moveToFirst(); !deleteId.isAfterLast(); deleteId.moveToNext()) {
                    if (deleteId.getString(0).toString().equals(input.getText().toString())) {
                        DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case DialogInterface.BUTTON_POSITIVE:
                                        dataHolder.deleteSelectedData(input.getText().toString());
                                        Toast.makeText(DeleteAppointment.this, "Appointment " + input.getText().toString() + " deleted", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(DeleteAppointment.this, MainActivity.class);
                                        startActivity(intent);
                                        break;
                                    case DialogInterface.BUTTON_NEGATIVE:
                                        break;
                                }
                            }

                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage("Are you sure you want to delete " + deleteId.getString(1) + "?").setPositiveButton("Yes", dialogListener)
                                .setNegativeButton("No", dialogListener).show();
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
