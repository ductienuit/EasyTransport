package com.transport.easytransport.bicycle;

import transportapisdk.models.Stop;

public class BicycleStop extends Stop {
    private int maxBikeCount;
    private int bikeCount = 0;

    public BicycleStop() {
        super();
    }

    public BicycleStop(int maxBike) {
        super();
        this.maxBikeCount = maxBike;
    }

    public int getBikeCount() {
        return bikeCount;
    }

    public void setBikeCount(int bikeCount) {
        this.bikeCount = bikeCount;
    }

    public int getMaxBikeCount() {
        return maxBikeCount;
    }
}
