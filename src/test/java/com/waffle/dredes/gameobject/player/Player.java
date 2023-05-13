package com.waffle.dredes.gameobject.player;

import com.waffle.components.ColliderComponent;
import com.waffle.components.KinematicComponent;
import com.waffle.components.SpriteRenderComponent;
import com.waffle.components.TransformComponent;
import com.waffle.core.Constants;
import com.waffle.core.LogLevel;
import com.waffle.core.SpriteRenderer;
import com.waffle.core.Utils;
import com.waffle.ecs.GameObject;
import com.waffle.input.KeybindManager;
import com.waffle.struct.Vec2f;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 * A class representing the player GameObject
 */
public class Player extends GameObject {
    private static final Vec2f TERMINAL_VELOCITY = new Vec2f(500, 200);
    //COMPONENTS
    private SpriteRenderComponent sprites;
    public TransformComponent transform;
    public KinematicComponent kinematics;
    private KeybindManager keybindManager;
    public ColliderComponent gc;
    public Boolean onGround;
    public Vec2f inputD;
    //STATES
    public boolean faceLeft;
    private Falling fallState;
    private Jumping jumpState;
    private Idling idleState;
    private Walking walkState;
    private Turning turnState;
    public State current;
    public float frictionCoEff;
    public Vec2f vCoEff;

    public boolean committed;


    /**
     * Constructs a new player object
     */
    public Player() {}


    @Override
    public void start() {
        try {
            BufferedImage b = Utils.loadImageFromPath("DreDes/DreDes-Will-FFPose-Gun.png");
            sprites = new SpriteRenderComponent();
            sprites.sprites.add(new SpriteRenderer(new Vec2f(), b, b.getWidth(), b.getHeight()));
            transform = new TransformComponent(400, 0);
            kinematics = new KinematicComponent(new Vec2f(), new Vec2f(), 0, 1);
            gc = new ColliderComponent(new Vec2f(0, b.getHeight()), new Vec2f(b.getWidth(), 1) ,e -> {
                onGround = true;
                System.out.println("Colliding");
            });
            keybindManager = new KeybindManager();
            addBindings();
            setStates();
            inputD = new Vec2f();
            onGround = false;
            vCoEff = new Vec2f(1,1);
            frictionCoEff = 1;
        } catch(Exception e) {
            Constants.LOGGER.logException(e, LogLevel.SEVERE);
        }
    }

    /**
     * Falling
     * Jumping
     * Running
     * Walking
     * Idle
     */

    @Override
    public void update(float dt) {
        applyDirection();
        onGround = onGround || keybindManager.triggered("Levitate");
        if(!onGround) {
            if(!(current instanceof Jumping) || (!current.active)) {
                current = fallState.activate();
            }
        } else {
            committed = current instanceof Jumping && current.active;
            if(keybindManager.triggered("Jump") && !committed) {
                current = jumpState.activate();
                committed = true;
            }
            committed = committed || (current instanceof Turning && current.active);
            if(!committed && ((faceLeft && inputD.x > 0) || (!faceLeft && inputD.x < 0))) {
                current = turnState.activate();
                faceLeft = !faceLeft;
                committed = true;
            }
            committed = committed || current instanceof Walking;
            if(!committed && inputD.x != 0) {
                current = walkState.activate();
                committed = true;
            }
            committed = committed || current instanceof Idling;
            if(!committed) {
                current = idleState.activate();
            }
        }
        applyCoefficients();
        current.apply(this);
        vCoEff.x = 1;
        vCoEff.y = 1;
        frictionCoEff = 1;
        onGround = false;
    }

    /**
     * Adds player keybindings
     */
    public void addBindings() {
        keybindManager.addKeybind("Jump", KeyEvent.VK_SPACE);
        keybindManager.addKeybind("Left", KeyEvent.VK_LEFT);
        keybindManager.addKeybind("Right", KeyEvent.VK_RIGHT);
        keybindManager.addKeybind("Up", KeyEvent.VK_UP);
        keybindManager.addKeybind("Down", KeyEvent.VK_DOWN);
        keybindManager.addKeybind("Levitate", KeyEvent.VK_SHIFT);
    }

    /**
     * Sets the states for the player
     */
    public void setStates() {
        jumpState = new Jumping(100,20, 200, 30);
        walkState = new Walking(50);
        idleState = new Idling(50);
        turnState = new Turning(5, 75);
        fallState = new Falling(100,135, 9.8f, 45);
    }

    private void applyDirection() {
        float x = 0;
        float y = 0;
        if(keybindManager.triggered("Left")) {
            x -= 1;
        }

        if(keybindManager.triggered("Right")) {
            x += 1;
        }

        if(keybindManager.triggered("Up")) {
            y += 1;
        }

        if(keybindManager.triggered("Down")) {
            y -= 1;
        }
        inputD.x = x;
        inputD.y = y;
    }

    /**
     * Apply extra physics information, such as friction
     */
    public void applyCoefficients() {
        kinematics.v.div(vCoEff);
        idleState.traction = (int)(idleState.baseTraction * frictionCoEff);
    }
}
