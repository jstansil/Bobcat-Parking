package com.example.amandaabalos.bobcatparkingapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    public ParkingLot [] lots;
    private LotUpdater update;
    private int updateDelay = 60000; //Milliseconds. used to dictate database retrieval/update and parking lot updates

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //myDB = new Database(this);
        update = new LotUpdater();

        //case where we move from the 2nd screen. we don't need to remake the parking array, we just recieve it from the 2nd screen
        if (getIntent().hasExtra("com.example.amandaabalos.bobcatparkingapp.southlots")) {
            lots = (ParkingLot[])getIntent().getExtras().getSerializable("com.example.amandaabalos.bobcatparkingapp.southlots");
            //2nd screen is passed the appropriate parking lot object. need to access and display its info
        }
        else {
            //initialize all the parking lots. could probably find a prettier way to do this
            //also, the values put in for distance & max capacity are wrong
            //they are arranged by their relative northern position on campus. permit values were taken from
            // http://taps.ucmerced.edu/parkingmaps
            lots = new ParkingLot[9];
            lots[0] = new ParkingLot(200, "X;A;ACP;AUB;ALEV;B;BCP;DP;MP;AM;M;C;FSLEV;HCP;CCP", 10.0, getString(R.string.lake1));
            lots[1] = new ParkingLot(300, "X;DP;MP;ALEV;A;ACP;AUB;FSLEV;B;BCP;CCP;C;HCP", 12.0, getString(R.string.lake2));

            lots[2] = new ParkingLot(15, "X;DP;MP;ALEV;A;ACP;AUB;FSLEV;B;BCP;C;CCP;HCP", 8.5,  getString(R.string.ecec));

            lots[3] = new ParkingLot(400, "X;DP;MP;ALEV;A;ACP;AUB;FSLEV;B;BCP;CCP;C;HCP", 11.0,  getString(R.string.evo));

            lots[4] = new ParkingLot(20, "X;ACP;AUB;DP;EV;AM;VRIDE;EZPARK", 5.5,  getString(R.string.library1));
            lots[5] = new ParkingLot(15, "A;ACP;ALEV;MP", 5.0, getString(R.string.library2));

            lots[6] = new ParkingLot(100, "X;A;ACP;AUB;EZPARK;DP;MP;AM;ALEV", 2.5, getString(R.string.legrand));

            lots[7] = new ParkingLot(200, "X;A;ACP;AUB;ALEV;LEV;B;BCP;DP;MP", 5.5, getString(R.string.bowl1));
            lots[8] = new ParkingLot(250, "X;A;ACP;AUB;ALEV;LEV;B;BCP;DP;MP;FSC", 6.0, getString(R.string.bowl2));
        }
        //Initial update of parking lots. this will fetch their status from the DB before they are displayed
        for(ParkingLot l: lots){
            update.update(l, getString(R.string.mon));
        }



        //Initialize buttons, one for each lot and one to switch to the north side of campus
        Button lot1 = (Button)findViewById(R.id.lake1_btn);
        Button lot2 = (Button)findViewById(R.id.lake2_btn);
        Button lot3 = (Button)findViewById(R.id.ecec_btn);
        Button lot4 = (Button)findViewById(R.id.evo_btn);
        Button northlots = (Button)findViewById(R.id.moveup);

        lot1.setText(Integer.toString(lots[0].curr_capacity) + "/" + Integer.toString(lots[0].getMax_capacity()));
        setPercentColorBackground(lot1, lots[0].curr_capacity, lots[0].getMax_capacity());
        lot2.setText(Integer.toString(lots[1].curr_capacity) + "/" + Integer.toString(lots[1].getMax_capacity()));
        setPercentColorBackground(lot2, lots[1].curr_capacity, lots[1].getMax_capacity());
        lot3.setText(Integer.toString(lots[2].curr_capacity) + "/" + Integer.toString(lots[2].getMax_capacity()));
        setPercentColorBackground(lot3, lots[2].curr_capacity, lots[2].getMax_capacity());
        lot4.setText(Integer.toString(lots[3].curr_capacity) + "/" + Integer.toString(lots[3].getMax_capacity()));
        setPercentColorBackground(lot4, lots[3].curr_capacity, lots[3].getMax_capacity());

        //Each button press sends a different lot object to the display info screen
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
        northlots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Main3Activity.class);
                i.putExtra("com.example.amandaabalos.bobcatparkingapp.northlots", lots);
                startActivity(i);
            }
        });

        //Activity that calls the lot updater every minute (based on user clock)
        //Lot updater is currently configured to just randomly increment/decrement lots but it will
        //be able to query the database

        final SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
        final Calendar calendar = Calendar.getInstance();

        final Handler delay_call = new Handler();
        Runnable call_updater = new Runnable() {
            @Override
            public void run() {
                try{
                    //Code that is ran every minute
                    //Checks current day to make the correct query
                    String weekDay = dayFormat.format(calendar.getTime());
                    for(ParkingLot l : lots){
                        update.update(l, weekDay);
                    }
                }
                catch (Exception e) {
                    // TODO: handle exception
                }
                finally{
                    //recursively call the same runnable to execute it at a regular interval
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
