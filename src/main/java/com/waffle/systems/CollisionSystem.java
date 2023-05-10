package com.waffle.systems;

import com.waffle.components.ColliderComponent;
import com.waffle.components.TransformComponent;
import com.waffle.core.*;
import com.waffle.ecs.ECSSystem;
import com.waffle.struct.DynamicQuadTreeContainer;
import com.waffle.struct.Pair;
import com.waffle.struct.Rectangle;
import com.waffle.struct.Vec2f;

import java.util.*;

public class CollisionSystem extends ECSSystem {
    private ArrayList<DynamicQuadTreeContainer<Integer>> quadTrees = new ArrayList<>();
    private final Map<Integer, Integer> entityToIndexMap = new HashMap<>();
    public void update(float dt) {
        for(DynamicQuadTreeContainer<Integer> tree : quadTrees) {
            Map<Integer, ColliderComponent> toCall = new HashMap<>();
            List<Integer> list = tree.search(new Rectangle(new Vec2f(0, 0), new Vec2f(10000, 10000)));
            for(int entity : list) {
                TransformComponent t = world.getComponent(entity, TransformComponent.class);
                ColliderComponent c = world.getComponent(entity, ColliderComponent.class);
                tree.remove(entityToIndexMap.get(entity));
                Vec2f colliderPos = new Vec2f(t.position).add(c.position);
                Rectangle searchRect = new Rectangle(colliderPos, c.size);
                entityToIndexMap.put(entity, tree.insert(entity, searchRect));
                for(int checkAgainst : tree.search(searchRect)) {
                    if(entity == checkAgainst) continue;
                    TransformComponent tTwo = world.getComponent(checkAgainst, TransformComponent.class);
                    ColliderComponent cTwo = world.getComponent(checkAgainst, ColliderComponent.class);
                    if(intersects(c, cTwo, t.position, tTwo.position)) {
                        toCall.put(entity, cTwo);
                        toCall.put(checkAgainst, c);
                    }
                }
            }
            for(Integer key : toCall.keySet()) {
                toCall.get(key).listener.collideWith(new CollisionEvent(world.getGameObject(key)));
            }
        }
    }

    @Override
    public void entityAdded(int layer, int entity) {
        if(!entityToIndexMap.containsKey(entity)) {
            ColliderComponent collider = world.getComponent(entity, ColliderComponent.class);
            TransformComponent t = world.getComponent(entity, TransformComponent.class);
            int id = quadTrees.get(layer).insert(entity, new Rectangle(new Vec2f(t.position).add(collider.position), collider.size));
            entityToIndexMap.put(entity, id);
        }
    }

    @Override
    public void entityRemoved(int layer, int entity) {
        if(entityToIndexMap.containsKey(entity)) {
            quadTrees.get(layer).remove(entityToIndexMap.get(entity));
            entityToIndexMap.remove(entity);
        }
    }

    @Override
    public void layersCreated(int layerAmount) {
        quadTrees = new ArrayList<>(layerAmount);
        for(int i = 0; i < layerAmount; i++) {
            DynamicQuadTreeContainer<Integer> container = new DynamicQuadTreeContainer<>(world.getMaxEntities());
            container.resize(new Rectangle(new Vec2f(0, 0), new Vec2f(10000, 10000)));
            quadTrees.add(container);
        }
    }

    private boolean intersects(ColliderComponent boxOne, ColliderComponent boxTwo, Vec2f posOne, Vec2f posTwo) {
            return posOne.x < posTwo.x + boxTwo.size.x
                    && posOne.x + boxOne.size.x > posTwo.x
                    && posOne.y < posTwo.y + boxTwo.size.y
                    && posOne.y + boxOne.size.y > posTwo.y;
    }
}
