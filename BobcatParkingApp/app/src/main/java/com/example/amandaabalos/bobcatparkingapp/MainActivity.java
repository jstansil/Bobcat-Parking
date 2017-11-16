package com.example.amandaabalos.bobcatparkingapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

   /*myDB is the instance*/
    //Database myDB;
    public ParkingLot [] lots = new ParkingLot[9];
    private LotUpdater update;
    private int updateDelay = 60000; //Milliseconds. used to dictate database retrieval/update and parking lot updates

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //myDB = new Database(this);
        update = new LotUpdater();

        //initialize all the parking lots. could probably find a prettier way to do this
        //also, the values put in for distance & max capacity are wrong
        //they are arranged by their relative northern position on campus. permit values were taken from http://taps.ucmerced.edu/parkingmaps
        lots[0] = new ParkingLot(200, "X;A;ACP;AUB;ALEV;B;BCP;DP;MP;AM;M;C;FSLEV;HCP;CCP", 10.0, "Lake One");
        lots[1] = new ParkingLot(300, "X;DP;MP;ALEV;A;ACP;AUB;FSLEV;B;BCP;CCP;C;HCP", 12.0, "Lake Two");

        lots[2] = new ParkingLot(15, "X;DP;MP;ALEV;A;ACP;AUB;FSLEV;B;BCP;C;CCP;HCP", 8.5, "ECEC");

        lots[3] = new ParkingLot(400, "X;DP;MP;ALEV;A;ACP;AUB;FSLEV;B;BCP;CCP;C;HCP", 11.0, "Evolution");

        lots[4] = new ParkingLot(20, "X;ACP;AUB;DP;EV;AM;VRIDE;EZPARK", 5.5, "Library One");
        lots[5] = new ParkingLot(15, "A;ACP;ALEV;MP", 5.0, "Library Two");

        lots[6] = new ParkingLot(100, "X;A;ACP;AUB;EZPARK;DP;MP;AM;ALEV", 2.5, "Le Grand");

        lots[7] = new ParkingLot(200, "X;A;ACP;AUB;ALEV;LEV;B;BCP;DP;MP", 5.5, "North Bowl One");
        lots[8] = new ParkingLot(250, "X;A;ACP;AUB;ALEV;LEV;B;BCP;DP;MP;FSC", 6.0, "North Bowl Two");

        //Initial update of parking lots. this will fetch their status from the DB before they are displayed
        for(ParkingLot l: lots){
            update.update(l);
        }

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
                i.putExtra("com.example.amandaabalos.bobcatparkingapp.lotrequest", lots[0]);
                startActivity(i);
            }
        });
        lot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ParkingInfo.class);
                i.putExtra("com.example.amandaabalos.bobcatparkingapp.lotrequest", lots[1]);
                startActivity(i);
            }
        });
        lot3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ParkingInfo.class);
                i.putExtra("com.example.amandaabalos.bobcatparkingapp.lotrequest", lots[2]);
                startActivity(i);
            }
        });
        lot4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ParkingInfo.class);
                i.putExtra("com.example.amandaabalos.bobcatparkingapp.lotrequest", lots[3]);
                startActivity(i);
            }
        });
        lot5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ParkingInfo.class);
                i.putExtra("com.example.amandaabalos.bobcatparkingapp.lotrequest", lots[4]);
                startActivity(i);
            }
        });
        lot6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ParkingInfo.class);
                i.putExtra("com.example.amandaabalos.bobcatparkingapp.lotrequest", lots[5]);
                startActivity(i);
            }
        });
        lot7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ParkingInfo.class);
                i.putExtra("com.example.amandaabalos.bobcatparkingapp.lotrequest", lots[6]);
                startActivity(i);
            }
        });
        lot8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ParkingInfo.class);
                i.putExtra("com.example.amandaabalos.bobcatparkingapp.lotrequest", lots[7]);
                startActivity(i);
            }
        });
        lot9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ParkingInfo.class);
                i.putExtra("com.example.amandaabalos.bobcatparkingapp.lotrequest", lots[8]);
                startActivity(i);
            }
        });

        //Activity that calls the lot updater every minute (based on user clock)
        //Later we can also augment this to send/recieve info from the database server every minute
        final Handler delay_call = new Handler();
        Runnable call_updater = new Runnable() {
            @Override
            public void run() {
                try{
                    //Code that is ran every minute
                    for(ParkingLot l : lots){
                        update.update(l);
                    }
                }
                catch (Exception e) {
                    // TODO: handle exception
                }
                finally{
                    //recursively call the same runnable to execute it at  azregular interval
                    delay_call.postDelayed(this, updateDelay);
                }
            }
        };
        delay_call.postDelayed(call_updater, updateDelay);
    }
}
