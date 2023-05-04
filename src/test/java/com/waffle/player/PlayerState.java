package com.waffle.player;
import java.awt.Color;
public abstract class PlayerState
{
    private Color color;
    protected int counter;
    protected int record;
    private boolean countingUp;
    public boolean active;

    public PlayerState(int r, int g, int b)
    {
        color = new Color(r,b,g, 50);
        countingUp = true;
    }
    public PlayerState(int r, int g, int b, int duration)
    {
        color = new Color(r,b,g, 50);
        record = duration;
        counter = duration;
        countingUp = false;
    }
    public Color getColor()
    {
        return color;
    }

    protected void apply()
    {
        if(active)
        {
            if(countingUp)
            {
                counter++;
                if(counter > record)
                {
                    counter = record;
                }
            }
            else
            {
                counter--;
                if(counter < 0)
                {
                    counter = 0;
                    active = false;
                }
            }
        }
    }
    public void activate()
    {
        if(countingUp)
        {
            counter = 0;
        }
        else
        {
            counter = record;
        }
        active = true;
    }
    abstract void apply(Player p);

}