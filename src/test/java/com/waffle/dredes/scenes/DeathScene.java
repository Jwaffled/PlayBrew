package com.waffle.dredes.scenes;

import com.waffle.components.FontRenderComponent;
import com.waffle.core.Constants;
import com.waffle.core.DefaultScene;
import com.waffle.core.UpdateCounter;
import com.waffle.dredes.MainGame;
import com.waffle.dredes.gameobject.Background;
import com.waffle.input.KeybindManager;
import com.waffle.ui.Button;
import com.waffle.ui.ButtonEventListener;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Timer;

/**
 * A pause menu scene
 */
public class DeathScene extends DefaultScene {
    private Button menu;

    private Button exit;
    private Button map;

    private Background bg;
    private KeybindManager keybindManager;
    private FontRenderComponent fontRender;

    /**
     * Creates a new death scene with a maximum of 100 entities
     */
    public DeathScene() {
        super(100);
        bg = new Background("DreDes/joeover.png", 960, 540, MainGame.INSTANCE.gameCamera);
    }

    /**
     * Called when the Scene is added to the world
     */
    @Override
    public void start() {
        world.createLayers(1);
        menu = Button.newBuilder()
                .setX(500)
                .setY(300)
                .setWidth(100)
                .setHeight(20)
                .setButtonMessage("Title Screen")
                .setFontColor(Color.white)
                .setDrawMode(Constants.DrawMode.OUTLINE)
                .addButtonListener(new ButtonEventListener() {
                    @Override
                    public void buttonClicked(MouseEvent e) {
                        System.out.println(MainGame.INSTANCE.getPreviousSceneName());
                        MainGame.INSTANCE.setCurrentScene(MainGame.INSTANCE.getPreviousSceneName());
                    }

                    @Override
                    public void buttonPressed(MouseEvent e) {

                    }

                    @Override
                    public void buttonReleased(MouseEvent e) {

                    }

                    @Override
                    public void mouseEntered() {

                    }

                    @Override
                    public void mouseExited() {

                    }
                })
                .buildButton();
        map = Button.newBuilder()
                .setX(500)
                .setY(350)
                .setWidth(100)
                .setHeight(20)
                .setButtonMessage("Level Map")
                .setFontColor(Color.white)
                .setDrawMode(Constants.DrawMode.OUTLINE)
                .addButtonListener(new ButtonEventListener() {
                    @Override
                    public void buttonClicked(MouseEvent e) {
                        System.out.println(MainGame.INSTANCE.getPreviousSceneName());
                        MainGame.INSTANCE.setCurrentScene("MapScene");
                    }

                    @Override
                    public void buttonPressed(MouseEvent e) {

                    }

                    @Override
                    public void buttonReleased(MouseEvent e) {

                    }

                    @Override
                    public void mouseEntered() {

                    }

                    @Override
                    public void mouseExited() {

                    }
                }).buildButton();

        exit = Button.newBuilder()
                .setX(500)
                .setY(400)
                .setWidth(100)
                .setHeight(20)
                .setButtonMessage("Quit Game")
                .setFontColor(Color.white)
                .addButtonListener(new ButtonEventListener() {
                    @Override
                    public void buttonClicked(MouseEvent e) {
                        System.exit(0);
                    }

                    @Override
                    public void buttonPressed(MouseEvent e) {

                    }

                    @Override
                    public void buttonReleased(MouseEvent e) {

                    }

                    @Override
                    public void mouseEntered() {

                    }

                    @Override
                    public void mouseExited() {

                    }
                })
                .setDrawMode(Constants.DrawMode.OUTLINE)
                .buildButton();


        world.createGameObject(menu);
        world.createGameObject(map);
        world.createGameObject(exit);
        world.createGameObject(bg);
    }

    /**
     * Updates state associated with this scene, called every frame
     * @param dt the time between frames
     */
    @Override
    public void update(float dt) {
        incrementFramesActive();
        }

    /**
     * Called when the scene is set as the current displayed scene
     */
    @Override
    public void focus() {
        menu.setActive(true);
        map.setActive(true);
        exit.setActive(true);
        super.focus();
    }

    @Override
    public void lostFocus() {
        menu.setActive(false);
        map.setActive(false);
        exit.setActive(false);
    }
}

