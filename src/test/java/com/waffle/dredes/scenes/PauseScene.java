package com.waffle.dredes.scenes;

import com.waffle.core.Constants;
import com.waffle.core.DefaultScene;
import com.waffle.core.UpdateCounter;
import com.waffle.dredes.MainGame;
import com.waffle.input.KeybindManager;
import com.waffle.ui.Button;
import com.waffle.ui.ButtonEventListener;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Timer;

public class PauseScene extends DefaultScene {
    private Button resume;
    private Button options;
    private Button exit;
    private KeybindManager keybindManager;
    public PauseScene() {
        super(15);
    }

    @Override
    public void start() {
        world.createLayers(1);
        resume = Button.newBuilder()
                .setX(400)
                .setY(300)
                .setWidth(100)
                .setHeight(20)
                .setButtonMessage("Resume")
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
        options = Button.newBuilder()
                .setX(400)
                .setY(350)
                .setWidth(100)
                .setHeight(20)
                .setButtonMessage("Options")
                .setDrawMode(Constants.DrawMode.OUTLINE)
                .buildButton();

        exit = Button.newBuilder()
                .setX(400)
                .setY(400)
                .setWidth(100)
                .setHeight(20)
                .setButtonMessage("Exit")
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

        keybindManager = new KeybindManager();
        keybindManager.addKeybind("Unpause", KeyEvent.VK_ESCAPE);

        world.createGameObject(resume);
        world.createGameObject(options);
        world.createGameObject(exit);
    }

    @Override
    public void update(float dt) {
        incrementFramesActive();
        if(keybindManager.triggered("Unpause") && getFramesActive() >= 20) {
            MainGame.INSTANCE.setCurrentScene(MainGame.INSTANCE.getPreviousSceneName());
        }
    }

    @Override
    public void focus() {
        super.focus();
    }
}