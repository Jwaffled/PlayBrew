package com.waffle;

import com.waffle.components.FontRenderComponent;
import com.waffle.components.SpriteRenderComponent;
import com.waffle.components.TransformComponent;
import com.waffle.core.Constants;
import com.waffle.core.SpriteRenderer;
import com.waffle.core.Utils;
import com.waffle.struct.Vec2f;
import com.waffle.ecs.GameObject;
import com.waffle.ui.Button;
import com.waffle.ui.ButtonBuilder;
import com.waffle.ui.ButtonEventListener;
import com.waffle.ui.TexturedButton;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * 
 */
public class MainMenuGraphics extends GameObject {
    private SpriteRenderComponent map;
    private SimpleDateFormat dateFormat;
    private String username;
    private Button quitBTN;
    private FontRenderComponent systemStatus;
    private TransformComponent pos;

    public void start() {
        systemStatus = new FontRenderComponent(username);
        systemStatus.position = (new Vec2f(32, 6));
        ArrayList<SpriteRenderer> images = new ArrayList<SpriteRenderer>();
        BufferedImage image = Utils.loadImageFromPath("DreDes-TitleScreen.png");
        images.add(new SpriteRenderer(new Vec2f(0,0), image, image.getWidth(), image.getHeight()));
        map = new SpriteRenderComponent();
        map.sprites = images;
        dateFormat = new SimpleDateFormat("EEE, MMM d yyyy HH:mm:ss");
        pos = new TransformComponent(new Vec2f(0,0));
    }

    public void update(float dt)
    {
        systemStatus.message = username + "\n" + dateFormat.format(new Date(System.currentTimeMillis()));
    }
}
