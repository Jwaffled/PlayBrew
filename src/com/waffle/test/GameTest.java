package com.waffle.test;

import com.waffle.main.core.Game;
import com.waffle.main.input.*;
import java.awt.event.KeyEvent;

public class GameTest extends Game {
    public enum Actions
    {
        MOVE_LEFT,
        MOVE_RIGHT,
        MOVE_UP,
        MOVE_DOWN,
        FIRE
    }
    private Player player;

    private FrameCounter frameCount;
    public static GameTest INSTANCE = null;

    private Binding[] bindings;

    public GameTest() {

        INSTANCE = this;

    }

    @Override
    public void update(float dt) {
        super.update(dt);
        if(bindings[4].triggered()) {
            player.shoot();
        }
        if(bindings[0].triggered()) {
            player.moveLeft();
        } else if(bindings[1].triggered()) {
            player.moveRight();
        }
    }

    public void start() {
        window = createWindow(800, 600, "Testing");
        window.addMouseListener(Input.getInstance());
        window.addKeyListener(Input.getInstance());
        player = new Player();
        frameCount = new FrameCounter();
        world.createGameObject(player);
        world.createGameObject(frameCount);
        bindings = new Binding[Actions.values().length];
        addBindings();
        window.setVisible(true);
    }

    void addBindings()
    {
        bindings[Actions.MOVE_LEFT.ordinal()] = new Binding(KeyEvent.VK_LEFT, KeyEvent.VK_SHIFT, Input.getInstance());
        bindings[Actions.MOVE_RIGHT.ordinal()] = new Binding(KeyEvent.VK_RIGHT, KeyEvent.VK_SHIFT, Input.getInstance());
        bindings[Actions.FIRE.ordinal()] = new Binding(KeyEvent.VK_SPACE, Input.getInstance());
    }
}
