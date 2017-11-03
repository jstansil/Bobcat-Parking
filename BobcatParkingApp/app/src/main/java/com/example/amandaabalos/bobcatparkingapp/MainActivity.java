package com.example.amandaabalos.bobcatparkingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

   /*myDB is the instance*/
    Database myDB;
    ParkingLot [] lots = new ParkingLot[9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new Database(this);

        char [] c = {'X', 'A', 'B', 'C'};
        //initialize all the parking lots. could probably find a prettier way to do this
        //also, the values put in for distance & max capacity are wrong, I'll find them later..
        lots[0] = new ParkingLot(200, c, 10.0, "Lake One", myDB);
        lots[1] = new ParkingLot(300, c, 12.0, "Lake Two", myDB);
        lots[2] = new ParkingLot(15, c, 8.5, "ECEC", myDB);
        lots[3] = new ParkingLot(400, c, 11.0, "Evolution", myDB);

        char [] c2 = {'X'};
        lots[4] = new ParkingLot(20, c2, 5.5, "Library One", myDB);

        char [] c3 = {'A'};
        lots[5] = new ParkingLot(15, c3, 5.0, "Library Two", myDB);

        char [] c4 = {'X', 'A'};
        lots[6] = new ParkingLot(100, c4, 2.5, "Le Grand", myDB);

        char [] c5 = {'X', 'A', 'B'};
        lots[7] = new ParkingLot(200, c5, 5.5, "North Bowl One", myDB);
        lots[8] = new ParkingLot(250, c5, 6.0, "North Bowl Two", myDB);
    }
}
