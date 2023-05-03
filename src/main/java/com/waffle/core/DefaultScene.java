package com.waffle.core;

import com.waffle.components.*;
import com.waffle.ecs.World;
import com.waffle.systems.*;

import java.util.BitSet;

public class DefaultScene implements Scene {
    protected final int maxEntities;
    protected World world;
    protected SpriteRenderSystem spriteRenderSystem;
    protected PhysicsSystem physicsSystem;
    protected CollisionSystem collisionSystem;
    protected FontRenderSystem fontRenderSystem;
    protected GeometryRenderSystem geometryRenderSystem;
    protected UIRenderSystem uiRenderSystem;

    /**
     * Constructs a scene with a maximum amount of entities allowed in its ECS
     * @param max maximum amount of entities allowed in its ECS
     */
    public DefaultScene(int max) {
        maxEntities = max;
        world = new World(maxEntities);
        registerComponents();
        registerSystems();
    }

    /**
     * Updates the specified systems<br>
     * The default systems that are updated are
     * <ul>
     *     <li>Physics</li>
     *     <li>Collision</li>
     *     <li>World</li>
     * </ul>
     * @param dt the time between frames
     */
    @Override
    public void update(float dt) {
        physicsSystem.update(dt);
        collisionSystem.update(dt);
        world.update(dt);

    }

    /**
     * Called when the scene is added to a SceneManager
     */
    @Override
    public void start() {
        world.createLayers(1);
    }

    private void registerComponents() {
        world.registerComponent(TransformComponent.class);
        world.registerComponent(SpriteRenderComponent.class);
        world.registerComponent(FontRenderComponent.class);
        world.registerComponent(GeometryComponent.class);
        world.registerComponent(UITextureComponent.class);
        world.registerComponent(KinematicComponent.class);
        world.registerComponent(ColliderComponent.class);
    }

    private void registerSystems() {
        spriteRenderSystem = world.registerSystem(SpriteRenderSystem.class);
        {
            BitSet sig = new BitSet();
            sig.set(world.getComponentType(TransformComponent.class));
            sig.set(world.getComponentType(SpriteRenderComponent.class));
            world.setSystemSignature(sig, SpriteRenderSystem.class);
        }

        physicsSystem = world.registerSystem(PhysicsSystem.class);
        {
            BitSet sig = new BitSet();
            sig.set(world.getComponentType(TransformComponent.class));
            sig.set(world.getComponentType(KinematicComponent.class));
            world.setSystemSignature(sig, PhysicsSystem.class);
        }

        fontRenderSystem = world.registerSystem(FontRenderSystem.class);
        {
            BitSet sig = new BitSet();
            sig.set(world.getComponentType(TransformComponent.class));
            sig.set(world.getComponentType(FontRenderComponent.class));
            world.setSystemSignature(sig, FontRenderSystem.class);
        }

        geometryRenderSystem = world.registerSystem(GeometryRenderSystem.class);
        {
            BitSet sig = new BitSet();
            sig.set(world.getComponentType(GeometryComponent.class));
            sig.set(world.getComponentType(TransformComponent.class));
            world.setSystemSignature(sig, GeometryRenderSystem.class);
        }

        uiRenderSystem = world.registerSystem(UIRenderSystem.class);
        {
            BitSet sig = new BitSet();
            sig.set(world.getComponentType(UITextureComponent.class));
            sig.set(world.getComponentType(TransformComponent.class));
            world.setSystemSignature(sig, UIRenderSystem.class);
        }

        collisionSystem = world.registerSystem(CollisionSystem.class);
        {
            BitSet sig = new BitSet();
            sig.set(world.getComponentType(TransformComponent.class));
            sig.set(world.getComponentType(ColliderComponent.class));
            sig.set(world.getComponentType(KinematicComponent.class));
            world.setSystemSignature(sig, CollisionSystem.class);
        }
    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public SpriteRenderSystem getSpriteRenderSystem() {
        return spriteRenderSystem;
    }

    @Override
    public UIRenderSystem getUIRenderSystem() {
        return uiRenderSystem;
    }

    @Override
    public GeometryRenderSystem getGeometryRenderSystem() {
        return geometryRenderSystem;
    }

    @Override
    public FontRenderSystem getFontRenderSystem() {
        return fontRenderSystem;
    }
}
