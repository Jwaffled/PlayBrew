package com.waffle.dredes.gameobject.player;

import com.waffle.components.ColliderComponent;
import com.waffle.components.KinematicComponent;
import com.waffle.components.SpriteRenderComponent;
import com.waffle.components.TransformComponent;
import com.waffle.core.Constants;
import com.waffle.core.LogLevel;
import com.waffle.core.SpriteRenderer;
import com.waffle.core.Utils;
import com.waffle.dredes.level.Tile;
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
    public Boolean groundCheck;
    public Boolean wallCheckR;
    public Boolean wallCheckL;
    public Boolean ceilingCheck;
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


    /**
     * Called when the GameObject is added to the world
     */
    @Override
    public void start() {
        try {
            BufferedImage b = Utils.loadImageFromPath("DreDes/DreDes-Will-FFPose-Gun.png");
            sprites = new SpriteRenderComponent();
            sprites.sprites.add(new SpriteRenderer(new Vec2f(), b, b.getWidth(), b.getHeight()));
            transform = new TransformComponent(64, 448);
            kinematics = new KinematicComponent(new Vec2f(), new Vec2f(), 0, 1);
            gc = new ColliderComponent(new Vec2f(0, 0), new Vec2f(b.getWidth(), b.getHeight()) , e -> {
                if(e.getCollidedObject() instanceof Tile tile) {

                    if(!tile.fluid) {
                        groundCheck = true;
                        Vec2f playerMidpoint = new Vec2f(transform.position.x + b.getWidth() / 2f, transform.position.y + b.getHeight() / 2f);
                        Vec2f tileMidpoint = new Vec2f(tile.transform.position.x + tile.width / 2f, tile.transform.position.y + tile.height / 2f);
                        // Calculate collision normal
                        Vec2f collisionNormal = new Vec2f(tileMidpoint).sub(playerMidpoint);
                        collisionNormal.normalize();
                        float absX = Math.abs(collisionNormal.x);
                        float absY = Math.abs(collisionNormal.y);

                        if (absX > absY) {
                            // Hitting wall
                            if (collisionNormal.x > 0) {
                                // Right wall
                                wallCheckR = true;
                                kinematics.v.x = 0;
                                transform.position.x = tile.transform.position.x - b.getWidth();
                            } else {
                                // Left wall
                                wallCheckL = true;
                                kinematics.v.x = 0;
                                transform.position.x = tile.transform.position.x + tile.width + 1;
                            }
                        } else {
                            // Hitting floor or ceiling
                            if (collisionNormal.y > 0) {
                                // On ground
                                groundCheck = true;
                                kinematics.v.y = 0;
                                transform.position.y = tile.transform.position.y - b.getHeight();
                            } else {
                                // Hitting ceiling
                                ceilingCheck = true;
                                kinematics.v.y = 0;
                                transform.position.y = tile.transform.position.y + tile.height + 1;
                            }
                        }
                    }
                }
            });

            keybindManager = new KeybindManager();
            addBindings();
            setStates();
            inputD = new Vec2f();
            groundCheck = false;
            vCoEff = new Vec2f(1,1);
            frictionCoEff = 1;
        } catch(Exception e) {
            Constants.LOGGER.logException(e, LogLevel.SEVERE);
        }
    }

    /**
     * Updates the player state every frame
     * @param dt the time elapsed from the last update call
     */
    @Override
    public void update(float dt) {
        sprites.sprites.get(0).setFlipped(faceLeft);

        if(transform.position.x < 1) {
            transform.position.x = 64;
            kinematics.v.x = 100;
        }
        if(transform.position.x > 2559) {
            transform.position.x = 2528;
            kinematics.v.x = -100;
        }
        applyDirection();
        groundCheck = groundCheck || keybindManager.triggered("Levitate");
        if(!groundCheck) {
            if(!(current instanceof Jumping) || (!current.active) || ceilingCheck) {
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

        current.apply(this);
        if(kinematics.v.x > 0 && wallCheckR || kinematics.v.x < 0 && wallCheckL) {
            kinematics.v.x = 0;
        }
        applyCoefficients();
        vCoEff.x = 1;
        vCoEff.y = 1;
        frictionCoEff = 1;
        groundCheck = false;
        ceilingCheck = false;
        wallCheckR = false;
        wallCheckL = false;
    }

    /**
     * Adds player keybindings
     */
    private void addBindings() {
        keybindManager.addKeybind("Jump", KeyEvent.VK_SPACE);
        keybindManager.addKeybind("Left", KeyEvent.VK_A);
        keybindManager.addKeybind("Right", KeyEvent.VK_D);
        keybindManager.addKeybind("Up", KeyEvent.VK_W);
        keybindManager.addKeybind("Down", KeyEvent.VK_S);
        keybindManager.addKeybind("Levitate", KeyEvent.VK_SHIFT);
    }

    /**
     * Sets the states for the player
     */
    private void setStates() {
        jumpState = new Jumping(100,20, 200, 30);
        walkState = new Walking(175);
        idleState = new Idling(50);
        turnState = new Turning(5, 75);
        fallState = new Falling(250,135, 9.8f, 45);
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
    private void applyCoefficients() {
        kinematics.v.div(vCoEff);
        idleState.traction = (int)(idleState.baseTraction * frictionCoEff);
        turnState.friction = (int)(turnState.baseFriction * frictionCoEff);
    }
}
