package com.transport.easytransport.bicycle;

import java.util.ArrayList;

public class BicycleService {
    private ArrayList<BicycleStop> stations;
    private int mFarePerKm;
    private String mOpenTime;
    private String mCloseTime;

    public BicycleService(ArrayList<BicycleStop> stations, int mFarePerKm, String mOpenTime, String mCloseTime) {
        this.stations = stations;
        this.mFarePerKm = mFarePerKm;
        this.mOpenTime = mOpenTime;
        this.mCloseTime = mCloseTime;
    }

    public int getmFarePerKm() {
        return mFarePerKm;
    }

    public void setmFarePerKm(int mFarePerKm) {
        this.mFarePerKm = mFarePerKm;
    }

    public String getmOpenTime() {
        return mOpenTime;
    }

    public void setmOpenTime(String mOpenTime) {
        this.mOpenTime = mOpenTime;
    }

    public String getmCloseTime() {
        return mCloseTime;
    }

    public void setmCloseTime(String mCloseTime) {
        this.mCloseTime = mCloseTime;
    }
}
