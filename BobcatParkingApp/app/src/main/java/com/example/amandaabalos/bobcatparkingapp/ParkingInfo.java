package com.example.amandaabalos.bobcatparkingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ParkingInfo extends AppCompatActivity {
    //Do something with the parking lot that the user clicks
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_info);

        if (getIntent().hasExtra("com.example.amandaabalos.bobcatparkingapp.lotrequest")) {
            ParkingLot to_display = (ParkingLot)getIntent().getExtras().getSerializable("com.example.amandaabalos.bobcatparkingapp.lotrequest");
            //2nd screen is passed the appropriate parking lot object. need to access and display its info
            TextView name = (TextView)findViewById(R.id.lotname);
            name.setText(to_display.getName());

            TextView permits = (TextView)findViewById(R.id.permitlist);
            permits.setText(to_display.getPermitsComma());

            TextView dist = (TextView)findViewById(R.id.distance);
            dist.setText(Double.toString(to_display.getDist()));

            TextView cap = (TextView)findViewById(R.id.current);
            //cap.setText(Integer.toString(to_display.curr_capacity));
            cap.setText(": " + Integer.toString(to_display.curr_capacity) + "/" + Integer.toString(to_display.getMax_capacity()));

        }
    }
}
