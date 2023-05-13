package com.waffle.dredes.scenes;

import com.waffle.core.DefaultScene;
import com.waffle.dredes.gameobject.player.Player;
import com.waffle.render.Camera;
import com.waffle.struct.Vec2f;

public class PhysicsScene extends DefaultScene {
    public Player test;
    public static PhysicsScene INSTANCE;
    public PhysicsScene() {
        super(10);
    }

    public void start() {
        INSTANCE = this;
        world.createLayers(4);

        test = new Player();
        world.createGameObject(test, 1);
    }

    public void update(float dt) {
        super.update(dt);
    }
}
