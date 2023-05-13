package com.waffle.dredes.scenes;

import com.waffle.core.Constants;
import com.waffle.core.DefaultScene;
import com.waffle.dredes.MainGame;
import com.waffle.dredes.gameobject.Background;
import com.waffle.struct.Vec2f;
import com.waffle.ui.Button;
import com.waffle.ui.ButtonBuilder;
import com.waffle.ui.ButtonEventListener;
import com.waffle.ui.TexturedButton;

import java.awt.*;
import java.awt.event.MouseEvent;

public class TitleScene extends DefaultScene {
    private Background sceneBackground;
    private Button quitBTN;
    private Button playBTN;
    private Button optBTN;

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
        quitBTN = new ButtonBuilder()
                .setX(58)
                .setY(380)
                .setWidth(140)
                .setHeight(50)
                .setButtonMessage("QUIT")
                .setMessageOffset(new Vec2f(15,15))
                .setFontColor(Color.white)
                .setShapeType(Constants.ShapeType.ELLIPSE)
                .setBackgroundColor(new Color(10,10,10))
                .addButtonListener(new ButtonEventListener() {
                    @Override
                    public void buttonClicked(MouseEvent e) {
                        System.out.println("Quitting Game");
                        System.exit(0);
                    }

                    @Override
                    public void buttonPressed(MouseEvent e) {}

                    @Override
                    public void buttonReleased(MouseEvent e) {}

                    @Override
                    public void mouseEntered() {}

                    @Override
                    public void mouseExited(){}

                })
                .buildButton();
        playBTN = new ButtonBuilder()
                .setX(58)
                .setY(250)
                .setWidth(140)
                .setHeight(50)
                .setButtonMessage("PLAY")
                .setMessageOffset(new Vec2f(15,15))
                .setFontColor(Color.white)
                .setShapeType(Constants.ShapeType.ELLIPSE)
                .setBackgroundColor(new Color(10,10,10))
                .addButtonListener(new ButtonEventListener() {
                    @Override
                    public void buttonClicked(MouseEvent e) {
                        MainGame.INSTANCE.setCurrentScene("GameplayScene");;
                    }

                    @Override
                    public void buttonPressed(MouseEvent e) {}

                    @Override
                    public void buttonReleased(MouseEvent e) {}

                    @Override
                    public void mouseEntered() {}

                    @Override
                    public void mouseExited() {}
                })
                .buildButton();
        optBTN = new ButtonBuilder()
                .setX(58)
                .setY(315)
                .setWidth(140)
                .setHeight(50)
                .setButtonMessage("OPTIONS")
                .setMessageOffset(new Vec2f(15,15))
                .setFontColor(Color.white)
                .setShapeType(Constants.ShapeType.ELLIPSE)
                .setBackgroundColor(new Color(10,10,10))
                .addButtonListener(new ButtonEventListener() {
                    @Override
                    public void buttonClicked(MouseEvent e) {
                        System.out.println("Does Nothing Yet");
                    }

                    @Override
                    public void buttonPressed(MouseEvent e) {}

                    @Override
                    public void buttonReleased(MouseEvent e) {}

                    @Override
                    public void mouseEntered() {}

                    @Override
                    public void mouseExited() {}
                })
                .buildButton();
        world.createGameObject(optBTN);
        world.createGameObject(quitBTN);
        world.createGameObject(playBTN);
    }
}
