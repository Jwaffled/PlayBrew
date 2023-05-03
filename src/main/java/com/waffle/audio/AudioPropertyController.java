package com.waffle.audio;

import javax.sound.sampled.Control;
import javax.sound.sampled.FloatControl;

public class AudioPropertyController {
    private final FloatControl control;

    /**
     * Constructs a new AudioPropertyController wrapping around a FloatControl
     * @param c the FloatControl reference to wrap
     */
    public AudioPropertyController(FloatControl c) {
        control = c;
    }

    /**
     * Fades from the current value to the new value over the specified duration (in microseconds)
     * @param val the new value
     * @param durationMicros the microseconds to spend fading
     */
    public void fadeTo(float val, int durationMicros) {
        control.shift(control.getValue(), val, durationMicros);
    }

    /**
     * Returns the maximum allowed value of the control
     * @return the maximum allowed value of the control
     */
    public float getMaximum() {
        return control.getMaximum();
    }

    /**
     * Returns the "label" for the maximum, such as "Full" or "Right"
     * @return the "label" for the maximum
     */
    public String getMaxLabel() {
        return control.getMaxLabel();
    }

    /**
     * Returns the "label" for the midpoint, such as "Center" or "Default"
     * @return the "label" for the midpoint
     */
    public String getMidLabel() {
        return control.getMidLabel();
    }

    /**
     * Returns the minimum allowed value of the control
     * @return the minimum allowed value of the control
     */
    public float getMinimum() {
        return control.getMinimum();
    }

    /**
     * Returns the "label" for the minimum value, such as "Left" or "Off"
     * @return the "label" for the minimum value
     */
    public String getMinLabel() {
        return control.getMinLabel();
    }

    /**
     * Returns the precision of the control<br>
     * The precision is the size of the increment between discrete valid values of the control
     * @return the precision of the control
     */
    public float getPrecision() {
        return control.getPrecision();
    }

    /**
     * Returns the "units" of this control, such as "dB"
     * @return the "units" of this control
     */
    public String getUnits() {
        return control.getUnits();
    }

    /**
     * Returns the smallest microsecond amount over which the control's value can change during a shift
     * @return update period in microseconds
     */
    public int getUpdatePeriod() {
        return control.getUpdatePeriod();
    }

    /**
     * Returns the value of the control
     * @return the value of the control
     */
    public float getValue() {
        return control.getValue();
    }

    /**
     * Sets the value of the control directly
     * @param newValue the value to set the control to
     */
    public void setValue(float newValue) {
        control.setValue(newValue);
    }

    /**
     * Shifts the control's value from one number to another over the specified microsecond amount
     * @param from the value to shift from
     * @param to the value to shift to
     * @param microseconds the amount of time (in microseconds) to spend shifting
     */
    public void shift(float from, float to, int microseconds) {
        control.shift(from, to, microseconds);
    }

    /**
     * Returns the type of control this controller wraps over
     * @return the type of control this controller wraps over
     */
    public Control.Type getType() {
        return control.getType();
    }

    public String toString() {
        return control.toString();
    }

}
