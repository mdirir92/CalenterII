package com.example.theonlysultan.calenterii;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.lang.String;


/**
 * Created by TheOnlySultan on 22/03/2018.
 */

public class DataBase extends SQLiteOpenHelper {
    private static final String database_name = "Appointments.db";
    private static final int database_version = 4;
    public static final String table_name = "appointments";
    public static final String Title = "Title";
    public static final String dates = "Dates";
    public static final String Time = "Time";
    public static final String details = "Details";
    public static final String id = "ID";

    public DataBase(Context context) {
        super(context, database_name, null, database_version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + table_name +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,Title TEXT, Dates TEXT, Time TEXT, Details TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + table_name);
        onCreate(db);
    }

    public boolean storeData(String title, String date, String time, String detail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Title, title);
        cv.put(dates, date);
        cv.put(Time, time);
        cv.put(details, detail);
        long result = db.insert(table_name,null, cv);
        if (result == -1) {
            Log.d("result", String.valueOf(result));
            return false;

        }
        else {
            Log.d("result", String.valueOf(result));
            return true;
        }
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + table_name, null);
        return  result;
    }

    public Cursor getDetails() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select Details from " + table_name, null);
        return  result;
    }

    public Cursor getTitle() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select Title from " + table_name, null);
        return  result;
    }

    public  Cursor getID(String ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + table_name + " WHERE " + id + " =\'" + ID + "\'", null);
        return result;
    }

    public Integer getId(String ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        /*Cursor result*/
        return db.delete(table_name,  "id = '" + "'" + ID + "'",  null);
        /*return result;*/
    }

    public Cursor getDeleteData(String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        //String sql = "select * from " + table_name + " where " + dates + " = " + date;
        Cursor result = db.rawQuery("SELECT * FROM " + table_name + " WHERE " + dates + " =\'" + date + "\'", null);
        return result;
    }

    public long Count() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, table_name);
        //db.close();
        return count;
    }

    public Integer deleteSelectedData (String ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(table_name, "ID = ?", new String[] {ID});
    }

    public void deleteAll(String userDate)     {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "DELETE FROM" + table_name + "WHERE" + dates + " = '" + userDate + "'";
        db.execSQL(sql);
    }

    public Integer deleteData(String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(table_name, "Dates = ?", new String[] {date});

    }
    public int updateAppointment(String userDate, String title, String date, String time, String detail){

        SQLiteDatabase db = getWritableDatabase();

        String sql = " SELECT * FROM " + table_name + " WHERE "
                + dates + " = '" + userDate + "\'" + " AND " +
                Title + "= '" + date  + "\';";

        Cursor cursor = db.rawQuery(sql,null);

        if (cursor == null || !cursor.moveToFirst()) {

            return -1;

        } else {

            ContentValues contentValues = new ContentValues();

            //stores the values to be updated
            contentValues.put(Time , time);
            contentValues.put(Title , title );
            contentValues.put(details , detail);


            //insert the values into the database
            db.update(table_name, contentValues , dates + "='" + userDate + "'" + " AND " +
                    Title + "='" + date + "'" , null);
            db.close(); //restores the memory
            cursor.close();
            return 1;

        }
    }


}
