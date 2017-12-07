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
    String[]params; //Stores the table name in index 0 and day in index 1 for making queries
    //GetData retreive; //executes a database query

    public LotUpdater() {
        averages = new int[13];
        params = new String[2];
        r = new Random();
        //retreive = new GetData();
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
        //retreive.execute(params);
        //p.curr_capacity = averages[0]; //
        return averages;
    }
    //functions that will interface with the DB go here. keep them commented out unless on Amanda's machine or
    //the online DB is running.
    //call 'retreive.execute();' to query the DB


    //1st argument = input type, 2nd argument = input type for progress execute, 3rd argument = output of .execute() function
    //currently retrieve.execute("string") passes a query through to the database, and populates an array with the results
    //so only the 1st argument is used
    /*
    private class GetData extends AsyncTask<String, Void, Void> {


        //JDBC driver name and database URL
        static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        static final String DB_URL = "jdbc:mysql://" +
                Database.DATABASE_URL + "/" +
                Database.DATABASE_NAME;
        //string that is displayed in the progess text view
        String msg = "";

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected  Void doInBackground(String ...params) {

            Connection conn = null;
            Statement stmt = null;

            try {
                Class.forName(JDBC_DRIVER);
                conn = DriverManager.getConnection(DB_URL, Database.USERNAME, Database.PASSWORD);

                stmt = conn.createStatement();
                //Edit this to change the query to DB. we will need to make the queries dynamic
                //based off of what parking lot(s) is currently visible
                String sql = "SELECT *" + " FROM " + params[0];

                //this is what holds the result of the query, in the form of a table.
                //we can iterate through the table by row and retrieve info from a given column
                //an example is shown
                ResultSet rs = stmt.executeQuery(sql);
                int i = 0;
                while(rs.next()){ //this works under the assumption that each COLUMN is a day of the week with 13 hours
                    averages[i] = rs.getInt(params[1]);
                    i++;
                }

                rs.close();
                stmt.close();
                conn.close();

            //Error handling, don't touch
            } catch (SQLException connError) {
                msg = "An exception was thrown for JDBC.";
                connError.printStackTrace();
            } catch (ClassNotFoundException e) {
                msg = "A class not found exception was thrown.";
                e.printStackTrace();
            } finally {

                try {
                    if (stmt != null) {
                        stmt.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                try {
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }

        //can use this to display a message, or do something after a successful query. otherwise nothing happens.
        protected Void onPostExecute(String msg) {
            return null;
        }
    }
    */
}
