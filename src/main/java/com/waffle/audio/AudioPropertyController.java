package com.waffle.audio;

import javax.sound.sampled.FloatControl;

public class AudioPropertyController extends FloatControl {
    public AudioPropertyController(FloatControl c, FloatControl.Type type) {
        super(type, c.getMinimum(), c.getMaximum(), c.getPrecision(), c.getUpdatePeriod(), c.getValue(), c.getUnits());
    }

    public void fadeTo(float val, int durationMicros) {
        this.shift(getValue(), val, durationMicros);
    }



}
