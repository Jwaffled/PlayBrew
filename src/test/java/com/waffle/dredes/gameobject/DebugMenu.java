package com.waffle.dredes.gameobject;

import com.waffle.components.FontRenderComponent;
import com.waffle.components.GeometryComponent;
import com.waffle.components.KinematicComponent;
import com.waffle.components.TransformComponent;
import com.waffle.core.RenderShape;
import com.waffle.dredes.MainGame;
import com.waffle.dredes.scenes.GameplayScene;
import com.waffle.struct.Vec2f;
import com.waffle.ecs.GameObject;
import com.waffle.input.Input;

import java.awt.*;

import static com.waffle.core.Constants.*;

/**
 * A debug menu for viewing information about specifics of the game
 */
public class DebugMenu extends GameObject {
    private GeometryComponent outline;
    private TransformComponent transform;
    private FontRenderComponent text;
    private static final int STRING_COUNT = 12;

    /**
     * Called when the GameObject is added to the world
     */
    @Override
    public void start() {
        outline = new GeometryComponent();
        outline.shapes.add(new RenderShape(ShapeType.RECTANGLE, DrawMode.OUTLINE, Color.BLACK, 300, 5 + 5 + (STRING_COUNT * 16), new Vec2f()));
        text = new FontRenderComponent("");
        text.position = new Vec2f(5, 10);
        text.color = Color.WHITE;
        transform = new TransformComponent(20, 20);
    }

    /**
     * Updates all of the statistics shown in the viewing window of this menu
     * @param dt not used
     */
    @Override
    public void update(float dt) {
        KinematicComponent k = GameplayScene.INSTANCE.player.kinematics;
        text.message = String.format(
                """
                        Entity count: %d
                        Mouse position: (%d, %d)
                        Render time: %.1fus
                        Player velocity: %s
                        Player accel: %s
                        Player force: %s
                        Player pos: %s
                        Player onGround: %s
                        Player State: %s
                        State Counter: %d
                        Collider pos: %s""",
                world.getLivingEntityCount(),
                Input.getInstance().getMousePosition().x, Input.getInstance().getMousePosition().y,
                MainGame.INSTANCE.renderTime,
                k.v, k.a, k.force, GameplayScene.INSTANCE.player.transform.position,
                GameplayScene.INSTANCE.player.groundCheck,
                GameplayScene.INSTANCE.player.current.getClass(), GameplayScene.INSTANCE.player.current.counter,
                GameplayScene.INSTANCE.player.gc.position
        );
    }
}
