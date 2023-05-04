
    package com.waffle;

import com.waffle.audio.StereoSoundEffect;
import com.waffle.core.Constants;
import com.waffle.core.Game;
import com.waffle.core.Utils;
import com.waffle.struct.Vec2f;
import com.waffle.input.*;
import com.waffle.render.Camera;
import com.waffle.tilemap.Tilemap;
import com.waffle.ui.Button;
import com.waffle.ui.ButtonBuilder;
import com.waffle.ui.ButtonEventListener;
import com.waffle.ui.TexturedButton;
import com.waffle.ui.TexturedSlider;

import java.util.Scanner;
import java.io.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

    public class MainMenu extends Game {

        public String userName;
        public SaveDataManager dm;
        public KeybindManager binds;
        private MainMenuGraphics mmg;
        public float volume;
        private StereoSoundEffect effect;
        public float currentVolume;
        private Button quitBTN;
        private Button playBTN;
        private Button optBTN;

        public static com.waffle.MainMenu INSTANCE = null;
        @Override
        public void free() {
            effect.free();
        }

        @Override
        public void start() {
            world.createLayers(4);
            INSTANCE = this;
            Camera view = new Camera(960, 540);
            //window = createWindow(960, 540, "Dreams and Deserts",view);
            window.addMouseListener(Input.getInstance());
            window.addKeyListener(Input.getInstance());
            mmg = new MainMenuGraphics();
            world.createGameObject(mmg, 1);
            dm = new SaveDataManager();
            world.createGameObject(dm, 1);
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
                        public void buttonPressed(MouseEvent e) {
                            System.out.println("Wanna Quit?");
                        }

                        @Override
                        public void buttonReleased(MouseEvent e) {}

                        @Override
                        public void mouseEntered() {
                            System.out.println("Mouse entered button");
                        }

                        @Override
                        public void mouseExited() {
                            System.out.println("Mouse exited button");
                        }
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
                            System.out.println("Does Nothing Yet");
                        }

                        @Override
                        public void buttonPressed(MouseEvent e) {}

                        @Override
                        public void buttonReleased(MouseEvent e) {}

                        @Override
                        public void mouseEntered() {
                            System.out.println("Mouse entered play button");
                        }

                        @Override
                        public void mouseExited() {
                            System.out.println("Mouse exited play button");
                        }
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
                        public void mouseEntered() {
                            System.out.println("Mouse entered op. button");
                        }

                        @Override
                        public void mouseExited() {
                            System.out.println("Mouse exited op. button");
                        }
                    })
                    .buildButton();
            world.createGameObject(quitBTN);
            world.createGameObject(playBTN);
            world.createGameObject(optBTN);
            window.setVisible(true);
        }
    }

