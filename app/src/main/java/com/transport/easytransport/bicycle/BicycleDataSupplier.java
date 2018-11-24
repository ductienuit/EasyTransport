package com.transport.easytransport.bicycle;

import java.util.ArrayList;

public class BicycleDataSupplier {
    private static BicycleDataSupplier mInstance = null;

    private BicycleDataSupplier() {

    }

    public static BicycleDataSupplier getInstance() {
        if (mInstance == null)
            mInstance = new BicycleDataSupplier();

        return mInstance;
    }

    public ArrayList<BicycleStop> getBikeStationByArea(
            double lat, double lon,
            int radius) {
        return getBikeStationByArea(lat, lon, radius, null);
    }

    public ArrayList<BicycleStop> getBikeStationByArea(
            double lat, double lon,
            int radius,
            String boundingBox) {
        ArrayList<BicycleStop> res = null;

        return res;
    }
}
