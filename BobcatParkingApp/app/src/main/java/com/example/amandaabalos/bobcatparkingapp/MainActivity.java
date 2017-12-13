package com.example.amandaabalos.bobcatparkingapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.widget.ToggleButton;
import android.widget.ViewFlipper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    public static int numlots = 9;
    public ParkingLot[] lots;
    Calendar calendar;
    DatabaseReference capacities;
    DatabaseReference select;
    private ViewFlipper mainViewFlipper;
    public Button[] buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calendar = Calendar.getInstance();
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
        Calendar calendar = Calendar.getInstance();
        final String weekDay = dayFormat.format(calendar.getTime());

        //initialize all the parking lots. could probably find a prettier way to do this
        //also, the values put in for distance & max capacity are wrong
        //they are arranged by their relative northern position on campus. permit values were taken from
        // http://taps.ucmerced.edu/parkingmaps
        lots = new ParkingLot[9];
        lots[0] = new ParkingLot(200, "X;A;ACP;AUB;ALEV;B;BCP;DP;MP;AM;M;C;FSLEV;HCP;CCP", 10.0, getString(R.string.lake1));
        lots[1] = new ParkingLot(300, "X;DP;MP;ALEV;A;ACP;AUB;FSLEV;B;BCP;CCP;C;HCP", 12.0, getString(R.string.lake2));

        lots[2] = new ParkingLot(15, "X;DP;MP;ALEV;A;ACP;AUB;FSLEV;B;BCP;C;CCP;HCP", 8.5, getString(R.string.ecec));

        lots[3] = new ParkingLot(400, "X;DP;MP;ALEV;A;ACP;AUB;FSLEV;B;BCP;CCP;C;HCP", 11.0, getString(R.string.evo));

        lots[4] = new ParkingLot(20, "X;ACP;AUB;DP;EV;AM;VRIDE;EZPARK", 5.5, getString(R.string.library1));
        lots[5] = new ParkingLot(15, "A;ACP;ALEV;MP", 5.0, getString(R.string.library2));

        lots[6] = new ParkingLot(100, "X;A;ACP;AUB;EZPARK;DP;MP;AM;ALEV", 2.5, getString(R.string.legrand));

        lots[7] = new ParkingLot(200, "X;A;ACP;AUB;ALEV;LEV;B;BCP;DP;MP", 5.5, getString(R.string.bowl1));
        lots[8] = new ParkingLot(250, "X;A;ACP;AUB;ALEV;LEV;B;BCP;DP;MP;FSC", 6.0, getString(R.string.bowl2));

        buttons = new Button[9];
        buttons[0] = (Button) findViewById(R.id.lake1_btn);
        buttons[1] = (Button) findViewById(R.id.lake2_btn);
        buttons[2] = (Button) findViewById(R.id.ecec_btn);
        buttons[3] = (Button) findViewById(R.id.evo_btn);
        buttons[4] = (Button) findViewById(R.id.library1_btn);
        buttons[5] = (Button) findViewById(R.id.library2_btn);
        buttons[6] = (Button) findViewById(R.id.legrand_btn);
        buttons[7] = (Button) findViewById(R.id.bowl1_btn);
        buttons[8] = (Button) findViewById(R.id.bowl2_btn);


        for(int j = 0; j < numlots; j++){
            buttons[j].setText("...");
            setPercentColorBackground(buttons[j], lots[j].curr_capacity, lots[j].getMax_capacity());
        }

        //Initial update of parking lots. this will fetch their status from the DB before they are displayed
        capacities = FirebaseDatabase.getInstance().getReference();
        capacities.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (int i = 0; i < numlots; i++) {
                    if (dataSnapshot.child(lots[i].getName() + "/" + weekDay + "/" + "7AM").getValue() != null) {
                        lots[i].curr_capacity = dataSnapshot.child(lots[i].getName() + "/" + weekDay + "/" + "7AM").getValue(Long.class).intValue();
                        buttons[i].setText(Integer.toString(lots[i].curr_capacity) + "/" + Integer.toString(lots[i].getMax_capacity()));
                        setPercentColorBackground(buttons[i], lots[i].curr_capacity, lots[i].getMax_capacity());
                    } else {
                        Log.d("error", "is at " + lots[i].getName());
                        lots[i].curr_capacity = 198;
                        buttons[i].setText(Integer.toString(lots[i].curr_capacity) + "/" + Integer.toString(lots[i].getMax_capacity()));
                        setPercentColorBackground(buttons[i], lots[i].curr_capacity, lots[i].getMax_capacity());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();

        //Initialize switchers and onclick listeners

        mainViewFlipper = (ViewFlipper) findViewById(R.id.main_display); // get the reference of ViewFlipper
        // Declare in and out animations and load them using AnimationUtils class
        final Animation northIn = AnimationUtils.loadAnimation(this, R.anim.slide_in_down);
        final Animation northOut = AnimationUtils.loadAnimation(this, R.anim.slide_out_up);
        final Animation southIn = AnimationUtils.loadAnimation(this, R.anim.slide_in_up);
        final Animation southOut = AnimationUtils.loadAnimation(this, R.anim.slide_out_down);

        final ToggleButton southLots = (ToggleButton) findViewById(R.id.btn_south_lots);
        final ToggleButton northLots = (ToggleButton) findViewById(R.id.btn_north_lots);

        //Listeners that swap between south and north lots
        //Swaps between two Views in ViewFlipper
        northLots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (southLots.isChecked()) {
                    southLots.setChecked(false);
                    northLots.setChecked(true);
                    // set the animation type to ViewFlipper
                    mainViewFlipper.setInAnimation(northIn);
                    mainViewFlipper.setOutAnimation(southOut);
                    mainViewFlipper.showNext();
                } else {
                    northLots.setChecked(true);
                }
            }
        });
        southLots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (northLots.isChecked()) {
                    northLots.setChecked(false);
                    southLots.setChecked(true);
                    // set the animation type to ViewFlipper
                    mainViewFlipper.setInAnimation(northOut);
                    mainViewFlipper.setOutAnimation(southIn);
                    mainViewFlipper.showPrevious();
                } else {
                    southLots.setChecked(true);
                }
            }
        });
        for(int k = 0; k < numlots; k++){
            final int z = k;
            buttons[k].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), ParkingInfo.class);
                    i.putExtra("com.example.amandaabalos.bobcatparkingapp.lotrequest", lots[z]);
                    startActivity(i);
                }
            });
        }

}

    public void setPercentColorBackground(Button lot, int curr, int max) {
        int percent = curr * 100 / max;
        if (percent <= 100 & percent >= 50) {
            lot.setBackgroundResource(R.drawable.btn_round_full);
            Log.d("test", "max button");
        } else if (percent < 50 & percent >= 25) {
            lot.setBackgroundResource(R.drawable.btn_round_semi_full);
            Log.d("test", "med button");
        } else if (percent < 25 & percent >= 0) {
            lot.setBackgroundResource(R.drawable.btn_round_empty);
            Log.d("test", "small button");
        } else {
            lot.setBackgroundResource(R.drawable.btn_round_error);
        }
        lot.invalidate();
    }

    public void setCapacity(int cap, ParkingLot p){
        p.curr_capacity = cap;
    }
}
