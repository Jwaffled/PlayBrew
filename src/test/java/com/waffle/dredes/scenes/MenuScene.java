package com.waffle.dredes.scenes;

import com.waffle.core.Constants;
import com.waffle.core.DefaultScene;
import com.waffle.core.Utils;
import com.waffle.dredes.DebugMenu;
import com.waffle.dredes.MainGame;
import com.waffle.struct.Vec2f;
import com.waffle.ui.Button;
import com.waffle.ui.ButtonEventListener;
import com.waffle.ui.TexturedButton;
import com.waffle.ui.TexturedSlider;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class MenuScene extends DefaultScene {
    private TexturedSlider slider;
    private TexturedButton button;
    private DebugMenu debug;

    public MenuScene() {
        super(100);
    }

    public void start() {
        world.createLayers(4);
        BufferedImage sliderRect = Utils.loadImageFromPath("TestGame/SliderRect.png");
        BufferedImage sliderTrack = Utils.loadImageFromPath("TestGame/SliderTrack.png");

        slider = TexturedSlider.newBuilder()
                .setX(750)
                .setY(15)
                .setWidth(200)
                .setHeight(50)
                .setMinValue(-60)
                .setMaxValue(0)
                .setSliderWidth(10)
                .setStartingValue(-20)
                .setSliderTexture(sliderRect)
                .setTrackTexture(sliderTrack)
                .buildTexturedSlider();

        BufferedImage texture = Utils.loadImageFromPath("TestGame/TestButtonPlayBrew_2.png");

        BufferedImage tint = Utils.applyTint(texture, new Color(90, 90, 90, 100));

        button = TexturedButton.newBuilder()
                .setX(750)
                .setY(95)
                .setWidth(100)
                .setHeight(50)
                .setButtonMessage("Player can shoot: true")
                .setMessageOffset(new Vec2f(-10, -30))
                .setButtonTexture(texture)
                .addButtonListener(new ButtonEventListener() {
                    @Override
                    public void buttonClicked(MouseEvent e) {
                        Constants.LOGGER.logInfo("Button clicked");
                        MainGame.INSTANCE.setCurrentScene("OtherScene");
                    }

                    @Override
                    public void buttonPressed(MouseEvent e) {
                        button.setCurrentTexture(tint);
                    }

                    @Override
                    public void buttonReleased(MouseEvent e) {
                        button.setCurrentTexture(texture);
                    }

                    @Override
                    public void mouseEntered() {
                        System.out.println("Mouse entered button");
                        //button.geometryComponent.color = Color.GRAY;
                    }

                    @Override
                    public void mouseExited() {
                        System.out.println("Mouse exited button");
                        //button.geometryComponent.color = Color.LIGHT_GRAY;
                    }
                })
                .buildTexturedButton();

        debug = new DebugMenu();

        world.createGameObject(debug);
        world.createGameObject(button);
        world.createGameObject(slider);
    }

    public void update(float dt) {
        world.update(dt);
    }
}
