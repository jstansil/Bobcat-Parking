package com.example.amandaabalos.bobcatparkingapp;

/**
 * Created by djsta on 11/2/2017.
 */

import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class LotUpdater {

    //Will retrieve info from the database depending given a parking lot, and either populate
    //an arraylist with averages or return a current capacity
    //right now we just generate a random boolean, with a 70% chance of it being true (a car entering) and
    //30% chance of it being false (a car leaving)
    Random r;

    int[] averages; //Stores the capacity averages for each hour [8am-8pm]
    String[] params; //Stores the table name in index 0 and day in index 1 for making queries

    public LotUpdater() {
        averages = new int[13];
        params = new String[2];
        r = new Random();
    }

    public boolean trip(ParkingLot p) {
        double entry = r.nextDouble();
        if (entry >= 0.3) {
            return true;
        }
        return false;
    }

    public int[] update(ParkingLot p, String day) {
        if (trip(p)) {
            p.curr_capacity++;
        } else {
            if (p.curr_capacity > 0) {
                p.curr_capacity--;
            }
        }
        params[0] = p.getName();
        params[1] = day;
        return averages;
    }
}


