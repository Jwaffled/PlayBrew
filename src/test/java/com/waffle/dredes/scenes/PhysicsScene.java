package com.waffle.dredes.scenes;

import com.waffle.core.DefaultScene;
import com.waffle.dredes.gameobject.player.Player;
import com.waffle.render.Camera;
import com.waffle.struct.Vec2f;

public class PhysicsScene extends DefaultScene {
    public Player test;
    public static PhysicsScene INSTANCE;
    Camera c;
    public PhysicsScene()
    {
        super(10);

    }

    public void start()
    {
        INSTANCE = this;
        world.createLayers(4);

        test = new Player();
        world.createGameObject(test, 1);
        c = new Camera(1000, 500, new Vec2f(-50, 50));


    }

    public void update(float dt)
    {
        super.update(dt);
        c.setPosition(test.transform.position);
    }

}
