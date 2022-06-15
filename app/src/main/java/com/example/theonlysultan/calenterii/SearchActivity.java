package com.example.theonlysultan.calenterii;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by TheOnlySultan on 22/03/2018.
 */

public class SearchActivity extends AppCompatActivity {


    DataBase dataHolder;
    String date;
    ListView listview;
    EditText searchIn;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        listview = (ListView) findViewById(R.id.searchList);
        searchIn = (EditText) findViewById(R.id.insertSearch);
        //bringDate = (TextView) findViewById(R.id.getDate);
        dataHolder = new DataBase(this);
        // list();

        dataHolder = new DataBase(this);
searchList();

    }
    public void searchList() {
        Intent bundle = getIntent();
        //Extract the data…

        final Cursor data = dataHolder.getAllData();
        final ArrayList<String> thelist = new ArrayList<>();

        while (data.moveToNext()) {
            thelist.add(data.getString(1) + ". " + data.getString(4));


            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, thelist);


            listview.setAdapter(adapter);



        }


        searchIn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                (SearchActivity.this).adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
}