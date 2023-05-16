package com.waffle.dredes.scenes;

import com.waffle.core.DefaultScene;
import com.waffle.core.SceneManager;
import com.waffle.core.UpdateCounter;
import com.waffle.core.Utils;
import com.waffle.dredes.MainGame;
import com.waffle.dredes.gameobject.CollisionObject;
import com.waffle.dredes.gameobject.DebugMenu;
import com.waffle.dredes.gameobject.LevelMenu;
import com.waffle.dredes.gameobject.Travel;
import com.waffle.dredes.gameobject.player.Player;
import com.waffle.dredes.level.*;
import com.waffle.input.Input;
import com.waffle.input.KeybindManager;
import com.waffle.render.Camera;
import com.waffle.struct.Vec2f;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class MapScene extends DefaultScene {

    public static MapScene INSTANCE = null;
    public KeybindManager keybindManager;
    public Background bgMap;
    public Background bgBg;

    public LevelGen.Biome biome;

    LevelMenu menu;
    Travel icon;
    Input i = Input.getInstance();
    private RoomLoader roomLoader;
    private Room room;
    public Vec2f pos;
    private DebugMenu debug;
    public MapScene() {
        super(10);
        INSTANCE = this;
    }

    private boolean inRange()
    {
        return (pos.x < icon.transformComponent.position.x + 75  + MainGame.INSTANCE.gameCamera.getPosition().x && pos.x > icon.transformComponent.position.x  + MainGame.INSTANCE.gameCamera.getPosition().x  && pos.y < icon.transformComponent.position.y + 75 +  MainGame.INSTANCE.gameCamera.getPosition().y && pos.y > icon.transformComponent.position.y + MainGame.INSTANCE.gameCamera.getPosition().y );
    }
    private boolean validBounds()
    {
        return pos.x < 521 && pos.y < 429;
    }
    @Override
    public void update(float dt) {
        pos = new Vec2f(i.getMousePosition().x, i.getMousePosition().y);
        world.update(dt);

        if(keybindManager.triggered("Click") && inRange() && validBounds())
        {
            System.out.println("Valid Click");
            Color pixel = new Color(bgMap.image.getRGB((int)pos.x, (int)pos.y));
            if(pixel.getRed() > 175 && pixel.getGreen() < 150)
            {
                biome = LevelGen.Biome.Redland;
            }
            else if(pixel.getRed() < 80)
            {
                biome = LevelGen.Biome.Grassland;
            }
            else if(pixel.getRed() > 150 && pixel.getBlue() < 150)
            {
                biome = LevelGen.Biome.Sandland;
            }
            else if(pixel.getRed() > 200 && pixel.getBlue() > 200)
            {
                biome = LevelGen.Biome.Saltland;
            }
            else
            {
                biome = LevelGen.Biome.Stoneland;
            }
            MainGame.INSTANCE.setCurrentScene("GameplayScene");
                //MainGame.INSTANCE.

        }
    }

    @Override
    public void start() {
        menu = new LevelMenu();
        world.createLayers(2);
        bgBg = new Background(Utils.loadImageFromPath("DreDes/Texas-Cali-Gradient.png"));
        bgMap = new Background(Utils.loadImageFromPath("DreDes/DreDes-BiomeMap.png"));
        icon = new Travel(new Vec2f(450, 250));
        keybindManager = new KeybindManager();
        keybindManager.addMouseBind("Click", MouseEvent.BUTTON1);
        menu.validLB = icon.transformComponent.position;
        menu.validUB = new Vec2f(menu.validLB.x + 75, menu.validLB.y + 75);
        world.createGameObject(bgBg, 0);
        world.createGameObject(bgMap, 1);
        world.createGameObject(icon, 1);
        world.createGameObject(menu, 1);
    }

    @Override
    public void focus() {
        super.focus();
        MainGame.INSTANCE.gameCamera.setPosition(new Vec2f());
    }

    private void addBindings() {

    }
}