package com.waffle.dredes.gameobject.player;

import com.waffle.components.KinematicComponent;
import com.waffle.components.SpriteRenderComponent;
import com.waffle.components.TransformComponent;
import com.waffle.core.Constants;
import com.waffle.core.LogLevel;
import com.waffle.core.SpriteRenderer;
import com.waffle.core.Utils;
import com.waffle.dredes.MainGame;
import com.waffle.dredes.gameobject.Bullet;
import com.waffle.ecs.GameObject;
import com.waffle.input.KeybindManager;
import com.waffle.struct.Vec2f;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Player extends GameObject {
    private static final Vec2f TERMINAL_VELOCITY = new Vec2f(500, 200);
    //COMPONENTS
    private SpriteRenderComponent sprites;
    public TransformComponent transform;
    public KinematicComponent kinematics;
    //STATES
    public boolean faceLeft;
    private Jumping jumpState;
    private Idling idleState;
    private Walking walkState;
    private PlayerState current;
    private KeybindManager keybindManager;
    //STATS
    public int traction;



    public Player() {
        keybindManager = MainGame.INSTANCE.keybindManager;
        addBindings();
    }


    @Override
    public void start() {
        try {
            BufferedImage b = Utils.loadImageFromPath("DreDes/DreDes-Will-FFPose-Gun.png");
            sprites = new SpriteRenderComponent();
            sprites.sprites.add(new SpriteRenderer(new Vec2f(), b, 40, 200));
            transform = new TransformComponent(400, 300);
            kinematics = new KinematicComponent(new Vec2f(), new Vec2f(), 2000, 10);
        } catch(Exception e) {
            Constants.LOGGER.logException(e, LogLevel.SEVERE);
        }
    }

    @Override
    public void update(float dt) {
        if(keybindManager.triggered("Shoot")) {
            shoot();
        }

        if(keybindManager.triggered("Left")) {
            transform.position.x -= 5;
        }

        if(keybindManager.triggered("Right")) {
            transform.position.x += 5;
        }

        if(transform.position.y >= 400) {
            transform.position.y = 400;
            kinematics.v.y = 0;
            kinematics.applyGravity = false;
        } else {
            kinematics.applyGravity = true;
        }
    }

    public void addBindings() {
        keybindManager.addKeybind("Jump", KeyEvent.VK_SPACE);
        keybindManager.addKeybind("Left", KeyEvent.VK_A);
        keybindManager.addKeybind("Right", KeyEvent.VK_D);
        keybindManager.addKeybind("Shoot", KeyEvent.VK_F);

    }

    public void setStates() {
        jumpState = new Jumping(100,30);
        walkState = new Walking(50);
        idleState = new Idling(50);
    }

    public void shoot() {
        world.createGameObject(new Bullet("TestGame/Dirt.png", 65, new Vec2f(transform.position), 500, 20, 5));
    }
}