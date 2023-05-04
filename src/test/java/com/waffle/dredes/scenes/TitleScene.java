package com.waffle.dredes.scenes;

import com.waffle.core.DefaultScene;
import com.waffle.dredes.gameobject.Background;
import com.waffle.ui.ButtonEventListener;
import com.waffle.ui.TexturedButton;

import java.awt.event.MouseEvent;

public class TitleScene extends DefaultScene {
    private Background sceneBackground;
    private TexturedButton playButton;

    public TitleScene() {
        super(25);
    }

    @Override
    public void update(float dt) {
        world.update(dt);
    }

    @Override
    public void start() {
        world.createLayers(2);

        sceneBackground = new Background("DreDes/DreDes-TitleScreen.png", 960, 540);

        buildButtons();



        world.createGameObject(sceneBackground);
    }

    private void buildButtons() {
        playButton = TexturedButton.newBuilder()
                .setWidth(55 * 3)
                .setHeight(20 * 3)
                .setX(70)
                .setY(300)
                .setButtonTexture("DreDes/DreDes-Placeholder-Play.png")
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
                .buildTexturedButton();

        world.createGameObject(playButton);
    }
}
