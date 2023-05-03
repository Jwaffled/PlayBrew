package com.waffle.dredes;

import com.waffle.core.DefaultScene;
import com.waffle.ui.Button;
import com.waffle.ui.ButtonEventListener;

import java.awt.event.MouseEvent;

public class OtherScene extends DefaultScene {
    private Button basicButton;
    public OtherScene() {
        super(10);
    }

    public void start() {
        world.createLayers(4);
        basicButton = Button.newBuilder()
                .setX(100)
                .setY(100)
                .setButtonMessage("Hello world!")
                .setWidth(100)
                .setHeight(30)
                .addButtonListener(new ButtonEventListener() {
                    @Override
                    public void buttonClicked(MouseEvent e) {
                        MainGame.INSTANCE.setCurrentScene("MenuScene");
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

        world.createGameObject(basicButton);
    }
}
