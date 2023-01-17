package com.waffle.test;

import com.waffle.main.components.TransformComponent;
import com.waffle.main.core.Game;

public class GameTest extends Game {
    public GameTest() {
        super();
        window = createWindow(800, 600, "Testing");
    }

    @Override
    public void update(float dt) {
        super.update(dt);
    }

    public void start() {
        int entity = world.createEntity();
        world.addComponent(entity, new TransformComponent(300, 300));
        window.setVisible(true);
    }
}
