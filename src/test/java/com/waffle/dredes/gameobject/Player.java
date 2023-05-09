//package com.waffle.dredes.gameobject;
//
//import com.waffle.components.KinematicComponent;
//import com.waffle.components.SpriteRenderComponent;
//import com.waffle.components.TransformComponent;
//import com.waffle.core.Constants;
//import com.waffle.core.LogLevel;
//import com.waffle.core.SpriteRenderer;
//import com.waffle.core.Utils;
//import com.waffle.dredes.MainGame;
//import com.waffle.ecs.GameObject;
//import com.waffle.input.KeybindManager;
//import com.waffle.struct.Vec2f;
//
//import java.awt.image.BufferedImage;
//
//public class Player extends GameObject {
//    private static final float PLAYER_SPEED = 400;
//    private static final Vec2f TERMINAL_VELOCITY = new Vec2f(500, 200);
//    private static final float FRICTION_COEFFICIENT = 10f;
//    private SpriteRenderComponent sprites;
//    public TransformComponent transform;
//    public KinematicComponent kinematics;
//    private KeybindManager keybindManager;
//
//    public Player() {}
//
//
//    @Override
//    public void start() {
//        try {
//            BufferedImage b = Utils.loadImageFromPath("DreDes/DreDes-Will-FFPose-Gun.png");
//            sprites = new SpriteRenderComponent();
//            sprites.sprites.add(new SpriteRenderer(new Vec2f(), b, 40, 200));
//            transform = new TransformComponent(400, 300);
//            kinematics = new KinematicComponent(new Vec2f(), new Vec2f(), 2000, 10);
//            keybindManager = MainGame.INSTANCE.keybindManager;
//        } catch(Exception e) {
//            Constants.LOGGER.logException(e, LogLevel.SEVERE);
//        }
//    }
//
//    @Override
//    public void update(float dt) {
//        if(Math.abs(kinematics.v.x) > TERMINAL_VELOCITY.x) {
//            kinematics.v.x = kinematics.v.x < 0 ? -TERMINAL_VELOCITY.x : TERMINAL_VELOCITY.x;
//        }
//
//        if(Math.abs(kinematics.v.y) > TERMINAL_VELOCITY.y) {
//            kinematics.v.y = kinematics.v.y < 0 ? -TERMINAL_VELOCITY.y : TERMINAL_VELOCITY.y;
//        }
//
//        if(keybindManager.triggered("PlayerLeft")) {
//            transform.position.x -= PLAYER_SPEED * dt;
//        }
//
//        if(keybindManager.triggered("PlayerRight")) {
//            transform.position.x += PLAYER_SPEED * dt;
//        }
//
//        if(keybindManager.triggered("PlayerShoot")) {
//            shoot();
//        }
//
//        //kinematics.a.x -= kinematics.v.x * FRICTION_COEFFICIENT;
//
//        if(keybindManager.triggered("PlayerJump")) {
//            // Jump
//            kinematics.force.add(new Vec2f(0, -10000));
//        } else if(transform.position.y >= 400) {
//            transform.position.y = 400;
//            kinematics.v.y = 0;
//            kinematics.applyGravity = false;
//        } else {
//            kinematics.applyGravity = true;
//        }
//    }
//
//    public void shoot() {
//        world.createGameObject(new Bullet("TestGame/Dirt.png", 15, new Vec2f(transform.position), 50, 8, 8));
//    }
//}
