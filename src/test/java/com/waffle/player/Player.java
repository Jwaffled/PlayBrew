package com.waffle.player;

import com.waffle.components.GeometryComponent;
import com.waffle.components.KinematicComponent;
import com.waffle.components.TransformComponent;
import com.waffle.core.Constants;
import com.waffle.core.RenderShape;
import com.waffle.ecs.GameObject;
import com.waffle.core.Constants;
import com.waffle.input.KeybindManager;
import com.waffle.struct.Vec2f;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.HashSet;

public class Player extends GameObject {
    KinematicComponent physics;
    TransformComponent pos;
    GeometryComponent g;
    HashSet<RenderShape> shapes;
    PlayerState currentState;
    HashMap<String, PlayerState> states;
    KeybindManager binds;


    public Player(){}

    public void start()
    {
        physics = new KinematicComponent(9.8f);
        physics.applyGravity = false;
        pos = new TransformComponent(0,0);
        g = new GeometryComponent();
        shapes = new HashSet<RenderShape>();
        shapes.add(new RenderShape(Constants.ShapeType.RECTANGLE, Constants.DrawMode.FILLED, new Color(0,0,0,255), 64, 128, new Vec2f()));
        states = new HashMap<String, PlayerState>();
        states.put("Idle", new Idling());
        states.put("Jump", new Jumping(45, 216, Jumping.JumpCurve.PARABOLIC));
        currentState = states.get("Idle");
        binds = new KeybindManager();
        binds.addKeybind("Jump", KeyEvent.VK_SPACE);
    }

    public void update(float dt)
    {
        if(currentState.equals(states.get("Idle")))
        {
            if(binds.triggered("Jump"))
            {
                currentState = states.get("Jump");
                currentState.activate();
            }
        }
        else
        {
            if(!currentState.active)
            {
                currentState = states.get("Idle");
            }
        }
    }

}
