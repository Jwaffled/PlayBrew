package com.waffle.player;

public class Jumping extends PlayerState {
    public float peakHeight;
    public JumpCurve curve;
    public enum JumpCurve
    {
        PARABOLIC,
        EXPONENTIAL,
        TRIG
    }

    public Jumping(int duration, float height, JumpCurve curve)
    {
        super(100,100,0, duration);
        peakHeight = height;
        this.curve = curve;
    }
    public void apply(Player p)
    {
        switch(curve)
        {
            case PARABOLIC -> p.physics.v.y = para();
            default -> p.physics.v.y -= 9.8f;
        }
        super.apply();
    }

    private float para()
    {
        return ((-(2 * peakHeight * (super.record - super.counter))/(super.record * super.record) ) + ((2 * peakHeight)/super.record))/60;
    }


}
