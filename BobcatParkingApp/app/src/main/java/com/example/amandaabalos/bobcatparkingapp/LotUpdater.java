package com.example.amandaabalos.bobcatparkingapp;

/**
 * Created by djsta on 11/2/2017.
 */
import java.util.Random;
public class LotUpdater {
    //Current convention is that input will be sampled every minute in real-time
    Random r;
    public LotUpdater(){
        r = new Random();
    }

    //will receive binary input from our 'sensor' that represents a car leaving or entering a lot
    //right now we just generate a random boolean, with a 70% chance of it being true (a car entering) and
    //30% chance of it being false (a car leaving)
    public boolean trip(ParkingLot p){
        double entry = r.nextDouble();
        if(entry >= 0.3){
            return true;
        }
        return false;
    }

    public void update(ParkingLot p){
        if(trip(p)){
            p.curr_capacity++;
        }
        else{
            if(p.curr_capacity > 0) {
                p.curr_capacity--;
            }
        }
    }
}
