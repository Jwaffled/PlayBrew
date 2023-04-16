package com.waffle.audio;

import javax.sound.sampled.Control;
import javax.sound.sampled.FloatControl;

public class AudioPropertyController {
    private final FloatControl control;
    public AudioPropertyController(FloatControl c) {
        control = c;
    }

    public void fadeTo(float val, int durationMicros) {
        control.shift(control.getValue(), val, durationMicros);
    }

    public float getMaximum() {
        return control.getMaximum();
    }

    public String getMaxLabel() {
        return control.getMaxLabel();
    }

    public String getMidLabel() {
        return control.getMidLabel();
    }

    public float getMinimum() {
        return control.getMinimum();
    }

    public String getMinLabel() {
        return control.getMinLabel();
    }

    public float getPrecision() {
        return control.getPrecision();
    }

    public String getUnits() {
        return control.getUnits();
    }

    public int getUpdatePeriod() {
        return control.getUpdatePeriod();
    }

    public float getValue() {
        return control.getValue();
    }

    public void setValue(float newValue) {
        control.setValue(newValue);
    }

    public void shift(float from, float to, int microseconds) {
        control.shift(from, to, microseconds);
    }

    public Control.Type getType() {
        return control.getType();
    }

    public String toString() {
        return control.toString();
    }

}
