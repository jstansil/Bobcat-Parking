package com.example.amandaabalos.bobcatparkingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

   /*myDB is the instance*/
    Database myDB;
    public ParkingLot [] lots = new ParkingLot[9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new Database(this);

        //initialize all the parking lots. could probably find a prettier way to do this
        //also, the values put in for distance & max capacity are wrong
        //they are arranged by their relative northern position on campus. permit values were taken from http://taps.ucmerced.edu/parkingmaps
        lots[0] = new ParkingLot(200, "X;A;ACP;AUB;ALEV;B;BCP;DP;MP;AM;M;C;FSLEV;HCP;CCP", 10.0, "Lake One", myDB);
        lots[1] = new ParkingLot(300, "X;DP;MP;ALEV;A;ACP;AUB;FSLEV;B;BCP;CCP;C;HCP", 12.0, "Lake Two", myDB);

        lots[2] = new ParkingLot(15, "X;DP;MP;ALEV;A;ACP;AUB;FSLEV;B;BCP;C;CCP;HCP", 8.5, "ECEC", myDB);

        lots[3] = new ParkingLot(400, "X;DP;MP;ALEV;A;ACP;AUB;FSLEV;B;BCP;CCP;C;HCP", 11.0, "Evolution", myDB);

        lots[4] = new ParkingLot(20, "X;ACP;AUB;DP;EV;AM;VRIDE;EZPARK", 5.5, "Library One", myDB);
        lots[5] = new ParkingLot(15, "A;ACP;ALEV;MP", 5.0, "Library Two", myDB);

        lots[6] = new ParkingLot(100, "X;A;ACP;AUB;EZPARK;DP;MP;AM;ALEV", 2.5, "Le Grand", myDB);

        lots[7] = new ParkingLot(200, "X;A;ACP;AUB;ALEV;LEV;B;BCP;DP;MP", 5.5, "North Bowl One", myDB);
        lots[8] = new ParkingLot(250, "X;A;ACP;AUB;ALEV;LEV;B;BCP;DP;MP;FSC", 6.0, "North Bowl Two", myDB);

        //one button for each lot. right now the menu is super basic, and each button is initialized one by one.
        //could probably be cleaner
        Button lot1 = (Button)findViewById(R.id.button);
        Button lot2 = (Button)findViewById(R.id.button2);
        Button lot3 = (Button)findViewById(R.id.button3);
        Button lot4 = (Button)findViewById(R.id.button4);
        Button lot5 = (Button)findViewById(R.id.button5);
        Button lot6 = (Button)findViewById(R.id.button6);
        Button lot7 = (Button)findViewById(R.id.button7);
        Button lot8 = (Button)findViewById(R.id.button8);
        Button lot9 = (Button)findViewById(R.id.button9);

        //Each button press sends a different lot object to the 2nd screen
        lot1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ParkingInfo.class);
                i.putExtra("com.example.amandaabalos.bobcatparkingapp.lot1", lots[0]);
                startActivity(i);
            }
        });
        lot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ParkingInfo.class);
                i.putExtra("com.example.amandaabalos.bobcatparkingapp.lot1", lots[1]);
                startActivity(i);
            }
        });
        lot3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ParkingInfo.class);
                i.putExtra("com.example.amandaabalos.bobcatparkingapp.lot1", lots[2]);
                startActivity(i);
            }
        });
        lot4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ParkingInfo.class);
                i.putExtra("com.example.amandaabalos.bobcatparkingapp.lot1", lots[3]);
                startActivity(i);
            }
        });
        lot5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ParkingInfo.class);
                i.putExtra("com.example.amandaabalos.bobcatparkingapp.lot1", lots[4]);
                startActivity(i);
            }
        });
        lot6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ParkingInfo.class);
                i.putExtra("com.example.amandaabalos.bobcatparkingapp.lot1", lots[5]);
                startActivity(i);
            }
        });
        lot7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ParkingInfo.class);
                i.putExtra("com.example.amandaabalos.bobcatparkingapp.lot1", lots[6]);
                startActivity(i);
            }
        });
        lot8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ParkingInfo.class);
                i.putExtra("com.example.amandaabalos.bobcatparkingapp.lot1", lots[7]);
                startActivity(i);
            }
        });
        lot9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ParkingInfo.class);
                i.putExtra("com.example.amandaabalos.bobcatparkingapp.lot1", lots[8]);
                startActivity(i);
            }
        });
    }
}
