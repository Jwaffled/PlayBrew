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

public class DebugMenu extends GameObject {
    private GeometryComponent outline;
    private TransformComponent transform;
    private FontRenderComponent text;
    private static final int STRING_COUNT = 11;

    @Override
    public void start() {
        outline = new GeometryComponent();
        outline.shapes.add(new RenderShape(ShapeType.RECTANGLE, DrawMode.OUTLINE, Color.BLACK, 300, 5 + 5 + (STRING_COUNT * 16), new Vec2f()));
        text = new FontRenderComponent("");
        text.position = new Vec2f(5, 10);
        transform = new TransformComponent(20, 20);
    }

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
                        Collisions: %d
                        Player onGround: %s 
                        Player State: %s
                        State Counter: %d""",
                world.getLivingEntityCount(),
                Input.getInstance().getMousePosition().x, Input.getInstance().getMousePosition().y,
                MainGame.INSTANCE.renderTime,
                k.v, k.a, k.force, GameplayScene.INSTANCE.player.transform.position,
                GameplayScene.INSTANCE.collisionObject.totalCollisions, GameplayScene.INSTANCE.player.onGround,
                GameplayScene.INSTANCE.player.current.getClass(), GameplayScene.INSTANCE.player.current.counter
        );
    }
}
