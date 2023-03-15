package com.waffle.test;

import com.waffle.main.core.Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

public class GameTest extends Game implements MouseListener, KeyListener {
    private final Player player;
    private final FrameCounter frameCount;
    private final Map<Integer, Boolean> currentlyPressed = new HashMap<>();
    public static GameTest INSTANCE = null;

    public GameTest() {
        window = createWindow(800, 600, "Testing");
        window.addMouseListener(this);
        window.addKeyListener(this);
        player = new Player();
        frameCount = new FrameCounter();
        INSTANCE = this;
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        if(isKeyPressed(KeyEvent.VK_SPACE)) {
            player.shoot();
        }
        if(isKeyPressed(KeyEvent.VK_LEFT)) {
            player.moveLeft();
        } else if(isKeyPressed(KeyEvent.VK_RIGHT)) {
            player.moveRight();
        }
    }

    public void start() {
        world.createGameObject(player);
        world.createGameObject(frameCount);
        window.setVisible(true);
    }

    public boolean isKeyPressed(int keyCode) {
        Boolean b = currentlyPressed.get(keyCode);
        return b != null && b;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        currentlyPressed.put(e.getKeyCode(), true);

    }

    @Override
    public void keyReleased(KeyEvent e) {
        currentlyPressed.put(e.getKeyCode(), false);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
