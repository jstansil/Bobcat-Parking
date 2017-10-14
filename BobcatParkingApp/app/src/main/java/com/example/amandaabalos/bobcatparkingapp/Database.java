package com.example.amandaabalos.bobcatparkingapp;



/* creates database*/

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by amandaabalos on 10/11/17.
 */



/* our database class that extends from our sqlite class*/
public class Database extends SQLiteOpenHelper {



    /*declare variables*/
    public static final String DATABASE_NAME = "BobcatParking.db";

    public static final String TABLE_NAME = "LakeLot1";
    public static final String COL_1 =  "Sunday";
    public static final String COL_2 =  "Monday";
    public static final String COL_3 =  "Tuesday";
    public static final String COL_4 =  "Wednesday";
    public static final String COL_5 =  "Thursday";
    public static final String COL_6 =  "Friday";
    public static final String COL_7 =  "Saturday";

    public static final String TABLE_NAME = "LakeLot1";
    public static final String COL_1 =  "Sunday";
    public static final String COL_2 =  "Monday";
    public static final String COL_3 =  "Tuesday";
    public static final String COL_4 =  "Wednesday";
    public static final String COL_5 =  "Thursday";
    public static final String COL_6 =  "Friday";
    public static final String COL_7 =  "Saturday";

    public static final String TABLE_NAME = "LakeLot2";
    public static final String COL_1 =  "Sunday";
    public static final String COL_2 =  "Monday";
    public static final String COL_3 =  "Tuesday";
    public static final String COL_4 =  "Wednesday";
    public static final String COL_5 =  "Thursday";
    public static final String COL_6 =  "Friday";
    public static final String COL_7 =  "Saturday";

    public static final String TABLE_NAME = "EvolutionLot";
    public static final String COL_1 =  "Sunday";
    public static final String COL_2 =  "Monday";
    public static final String COL_3 =  "Tuesday";
    public static final String COL_4 =  "Wednesday";
    public static final String COL_5 =  "Thursday";
    public static final String COL_6 =  "Friday";
    public static final String COL_7 =  "Saturday";

    public static final String TABLE_NAME = "LibraryLot";
    public static final String COL_1 =  "Sunday";
    public static final String COL_2 =  "Monday";
    public static final String COL_3 =  "Tuesday";
    public static final String COL_4 =  "Wednesday";
    public static final String COL_5 =  "Thursday";
    public static final String COL_6 =  "Friday";
    public static final String COL_7 =  "Saturday";

    public static final String TABLE_NAME = "LeGrandLot";
    public static final String COL_1 =  "Sunday";
    public static final String COL_2 =  "Monday";
    public static final String COL_3 =  "Tuesday";
    public static final String COL_4 =  "Wednesday";
    public static final String COL_5 =  "Thursday";
    public static final String COL_6 =  "Friday";
    public static final String COL_7 =  "Saturday";

    public static final String TABLE_NAME = "NorthBowl";
    public static final String COL_1 =  "Sunday";
    public static final String COL_2 =  "Monday";
    public static final String COL_3 =  "Tuesday";
    public static final String COL_4 =  "Wednesday";
    public static final String COL_5 =  "Thursday";
    public static final String COL_6 =  "Friday";
    public static final String COL_7 =  "Saturday";


    /*constructor*/
    /*every time this constructor is called,database will be created*/
    public Database(Context context) {
        super(context, DATABASE_NAME, null, 1);
        /*create database and table, just for checking, wont need later*/
        /*SQLiteDatabase db = this.getWritableDatabase();*/

    }



    /*method*/
    /* when onCreate is called, table will be created*/
    @Override
    public void onCreate(SQLiteDatabase db) {
        /*query to create table*/
        db.execSQL("create table" + TABLE_NAME + "(Sunday INTEGER, Monday INTEGER, Tuesday INTEGER, Wednesday INTEGER, Thursday INTEGER, Friday INTEGER, Saturday INTEGER)");

    }




    /*method*/
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }




    /*method to insert data*/
    public booelan insertData(String Sunday, String Monday, String Tuesday, String Wednesday, String Thursday, String Friday, String Saturday )
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues contentValues = new ContentValues();
    contentvalues.put();


}
