package com.example.amandaabalos.bobcatparkingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ParkingInfo extends AppCompatActivity {
    //Do something with the parking lot that the user clicks
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_info);

        if (getIntent().hasExtra("com.example.amandaabalos.bobcatparkingapp.lot1")) {
            ParkingLot to_display = (ParkingLot)getIntent().getExtras().getSerializable("com.example.amandaabalos.bobcatparkingapp.lot1");
            //2nd screen is passed the appropriate parking lot object. need to access and display its info
        }
    }
}
