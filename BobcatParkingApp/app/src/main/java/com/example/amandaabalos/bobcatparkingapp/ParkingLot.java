package com.example.amandaabalos.bobcatparkingapp;

/**
 * Created by djsta on 11/2/2017.
 */

public class ParkingLot implements java.io.Serializable {
    private int max_capacity;
    private String name;
    private String permit; //each permit type seperated by a colon
    private double dist;
    public int curr_capacity = 0;
    //Database lotinfo;

    public ParkingLot(int mc, String p, double d, String n){
        this.max_capacity = mc;
        this.permit = p;
        this.dist = d;
        //this.lotinfo = db;
        this.name = n;
    }

    public boolean is_full(){
        if (this.curr_capacity == this.max_capacity){
            return true;
        }
        return false;
    }

    public void LotUpdate(){
        //placeholder. interfaces with LotUpdater
        return;
    }

    public void LotTrend(){
        //placeholder. interfaces with the database
        return;
    }


    public String getName(){
        return this.name;
    }

    public String getPermits(){
        return this.permit;
    }

    public String getPermitsComma() { return this.permit.replace(";", ", "); }

    public double getDist(){
        return this.dist;
    }

    public int getMax_capacity (){ return this.max_capacity; }
}
