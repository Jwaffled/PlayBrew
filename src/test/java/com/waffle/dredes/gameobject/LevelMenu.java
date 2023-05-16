package com.waffle.dredes.gameobject;

import com.waffle.components.FontRenderComponent;
import com.waffle.components.GeometryComponent;
import com.waffle.components.KinematicComponent;
import com.waffle.components.TransformComponent;
import com.waffle.core.RenderShape;
import com.waffle.dredes.scenes.GameplayScene;
import com.waffle.struct.Vec2f;
import com.waffle.ecs.GameObject;
import com.waffle.input.Input;

import java.awt.*;

import static com.waffle.core.Constants.*;

/**
 *
 */
public class LevelMenu extends GameObject {
    private GeometryComponent outline;
    private TransformComponent transform;
    private FontRenderComponent text;
    /**
     * Represents the lowest coordinate value that the player can select a value from on the MapScene
     */
    public Vec2f validLB;
    /**
     * Represents the highest coordinate value that the player can select a value from on the MapScene
     */
    public Vec2f validUB;
    private static final int STRING_COUNT = 12;

    /**
     * Initializes this level menu
     */
    @Override
    public void start() {
        outline = new GeometryComponent();
        outline.shapes.add(new RenderShape(ShapeType.RECTANGLE, DrawMode.OUTLINE, Color.BLACK, 300, 5 + 5 + (STRING_COUNT * 16), new Vec2f()));
        text = new FontRenderComponent("");
        text.position = new Vec2f(5, 10);
        transform = new TransformComponent(700, 20);
    }

    /**
     * Updates the state within the level menu
     * @param dt the time between frames
     */
    @Override
    public void update(float dt) {
        KinematicComponent k = GameplayScene.INSTANCE.player.kinematics;
        text.message = String.format(
                """
                  Mouse x:%d,  y:%d
                  Valid x range %d - %d
                  Valid y range %d - %d
                   """, Input.getInstance().getMousePosition().x,
                Input.getInstance().getMousePosition().y, (int)validLB.x, (int)validUB.x, (int)validLB.y, (int)validUB.y

        );
    }
}