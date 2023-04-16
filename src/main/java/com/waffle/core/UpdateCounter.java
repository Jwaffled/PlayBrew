package com.waffle.core;

public class UpdateCounter {
    private final int updateFrequency;
    private int updateCounter;

    public UpdateCounter(int updateFrequency) {
        this.updateFrequency = updateFrequency;
    }

    public void update() {
        updateCounter++;
        if(updateCounter > updateFrequency) {
            updateCounter = 0;
        }
    }

    public boolean isReady() {
        return updateCounter == updateFrequency;
    }
}
