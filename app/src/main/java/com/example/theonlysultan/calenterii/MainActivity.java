package com.example.theonlysultan.calenterii;


import android.app.Dialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;




public class MainActivity extends AppCompatActivity implements
        OnClickListener {
    DataBase dataholder;
    private CalendarView mCalenter;
    private Button createBtn;
    private Button editView;
    private Button del;
    private Button moveAppntm;
    private Button searchBtn;
    private String dateCalenter;
    String Date;
    TextView editText1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCalenter = findViewById(R.id.calenterView);

        createBtn = (Button) findViewById(R.id.createBtn);
        createBtn.setOnClickListener(this);

        editView = (Button) findViewById(R.id.editView);
        editView.setOnClickListener(this);

        del = (Button) findViewById(R.id.delBtn);
        del.setOnClickListener(this);

        moveAppntm = (Button) findViewById(R.id.moveApptnmt);
        moveAppntm.setOnClickListener(this);

        searchBtn = (Button) findViewById(R.id.searchApptnmt);
        searchBtn.setOnClickListener(this);
        editText1 = (TextView) findViewById(R.id.textView);
        dataholder = new DataBase(this);

        mCalenter.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                String Date = String.format("%02d", dayOfMonth) + "/" + String.format("%02d", (month + 1)) + "/" + year;
                dateCalenter = Date;

            }
        });

        SimpleDateFormat dateCalFormat = new SimpleDateFormat("dd/MM/yyyy");
        String Date = dateCalFormat.format(new Date(mCalenter.getDate()));
        dateCalenter = Date;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.createBtn: {
                Intent intent = new Intent(MainActivity.this, CreateAppointment.class);
                intent.putExtra("date", dateCalenter);
                startActivity(intent);
                break;
            }

            case R.id.editView: {
                Intent intent = new Intent(MainActivity.this, ViewActivity.class);
                intent.putExtra("selected date", dateCalenter);
                String userDate = editText1.getText().toString();
                Date = userDate;
                startActivity(intent);
                break;

                /*Cursor result = dataholder.getData();
                Cursor det = dataholder.getIntel();

                if (result.getCount() == 0) {
                    viewAppointments("ERROR", "Nothing is displayed", "no appointments booked");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (result.moveToNext()) {


                    buffer.append("ID :" + result.getString(0 )+"\n");
                    buffer.append("Title :" + result.getString(1 )+"\n");
                    buffer.append("Time :" + result.getString(3 )+"\n");
                    buffer.append("Detail :" + result.getString(4 )+"\n\n");
                }

                //view data
                viewAppointments("Data", buffer.toString(), buffer.toString());
                break;*/
            }

            case R.id.delBtn: {
                Dialog onCreateDialog; {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Please Select")
                            .setItems(R.array.deleteArray ,new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String[] getArray = getResources().getStringArray(R.array.deleteArray);
                                    String selectedOp = getArray[which].toString();
                                    //DataBase = new DataBase(getApplicationContext());
                                    SQLiteDatabase data = dataholder.getWritableDatabase();
                                    if (selectedOp.equals("Select")) {
                                        Intent intent = new Intent(MainActivity.this, DeleteAppointment.class);
                                        intent.putExtra("selected date", dateCalenter);
                                        startActivity(intent);
                                    }
                                    else if (selectedOp.equals("Delete All")) {
                                        dataholder.deleteData(dateCalenter.toString());
                                        Toast.makeText(MainActivity.this, "All appointments deleted", Toast.LENGTH_LONG);

                                    }
                                    else {
                                        int pid = android.os.Process.myPid();
                                        android.os.Process.killProcess(pid);
                                    }
                                }
                            })
                            .show();
                }
                break;
            }

            case R.id.moveApptnmt: {
                break;
            }

            case R.id.searchApptnmt: {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);

                startActivity(intent);

                break;
            }
        }


        }
    private void viewAppointments (String title, String time, String intel){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(time);
        builder.setMessage(intel);
        builder.show();
    }
}