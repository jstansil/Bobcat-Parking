package com.example.amandaabalos.bobcatparkingapp;

/**
 * Created by djsta on 11/2/2017.
 */

public class LotUpdater {

    public LotUpdater(){}

    //will receive binary input from our 'sensor' that represents a car leaving or entering a lot
    public boolean trip(ParkingLot p){
        return true;
    }

    public void update(ParkingLot p){
        if(trip(p)){
            if(!p.is_full()) {
                p.curr_capacity++;
            }
        }
        else{
            p.curr_capacity--;
        }
    }
}