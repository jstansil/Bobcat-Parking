package com.example.amandaabalos.bobcatparkingapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

//this is a second version of MainActivity that represents the 2nd screen containing northern parking lots
public class Main3Activity extends AppCompatActivity {

    public ParkingLot [] lots;
    private LotUpdater update;
    private int updateDelay = 60000; //Milliseconds. used to dictate database retrieval/update and parking lot updates

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main3);
        //myDB = new Database(this);
        update = new LotUpdater();

        //initialize all the parking lots. could probably find a prettier way to do this
        //also, the values put in for distance & max capacity are wrong
        //they are arranged by their relative northern position on campus. permit values were taken from http://taps.ucmerced.edu/parkingmaps

        if (getIntent().hasExtra("com.example.amandaabalos.bobcatparkingapp.northlots")) {
            lots = (ParkingLot[])getIntent().getExtras().getSerializable("com.example.amandaabalos.bobcatparkingapp.northlots");
            //2nd screen is passed the appropriate parking lot object. need to access and display its info

        }
        //Initial update of parking lots. this will fetch their status from the DB before they are displayed
        for(ParkingLot l: lots){
            update.update(l, "Monday");
        }

        //one button for each lot. right now the menu is super basic, and each button is initialized one by one.
        //could probably be cleaner
        Button lot5 = (Button)findViewById(R.id.library1_btn);
        Button lot6 = (Button)findViewById(R.id.library2_btn);
        Button lot7 = (Button)findViewById(R.id.legrand_btn);
        Button lot8 = (Button)findViewById(R.id.bowl1_btn);
        Button lot9 = (Button)findViewById(R.id.bowl2_btn);
        Button southlots = (Button)findViewById(R.id.movedown);

        lot5.setText(Integer.toString(lots[4].curr_capacity) + "/" + Integer.toString(lots[4].getMax_capacity()));
        setPercentColorBackground(lot5, lots[4].curr_capacity, lots[4].getMax_capacity());
        lot6.setText(Integer.toString(lots[5].curr_capacity) + "/" + Integer.toString(lots[5].getMax_capacity()));
        setPercentColorBackground(lot6, lots[5].curr_capacity, lots[5].getMax_capacity());
        lot7.setText(Integer.toString(lots[6].curr_capacity) + "/" + Integer.toString(lots[6].getMax_capacity()));
        setPercentColorBackground(lot7, lots[6].curr_capacity, lots[6].getMax_capacity());
        lot8.setText(Integer.toString(lots[7].curr_capacity) + "/" + Integer.toString(lots[7].getMax_capacity()));
        setPercentColorBackground(lot8, lots[7].curr_capacity, lots[7].getMax_capacity());
        lot9.setText(Integer.toString(lots[8].curr_capacity) + "/" + Integer.toString(lots[8].getMax_capacity()));
        setPercentColorBackground(lot9, lots[8].curr_capacity, lots[8].getMax_capacity());

        //Each button press sends a different lot object to the 2nd screen
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
        southlots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("com.example.amandaabalos.bobcatparkingapp.northlots", lots);
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
                        update.update(l,"Monday");
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

    //Sets the button background depending on how full the lot is.
    public void setPercentColorBackground(Button lot, int curr, int max){
        int percent = curr * 100 / max;
        if (percent<=100 & percent>=90){
            lot.setBackgroundResource(R.drawable.btn_round_full);
        } else if (percent<90 & percent>=75){
            lot.setBackgroundResource(R.drawable.btn_round_semi_full);
        } else if (percent<75 & percent>=0){
            lot.setBackgroundResource(R.drawable.btn_round_empty);
        } else {
            lot.setBackgroundResource(R.drawable.btn_round_error);
        }
    }
}
