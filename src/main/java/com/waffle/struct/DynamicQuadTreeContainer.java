package com.waffle.struct;

import com.waffle.core.Constants;
import com.waffle.core.Pair;
import com.waffle.core.Rectangle;
import com.waffle.core.Vec2f;

import java.util.*;

public class DynamicQuadTreeContainer<T> implements Iterable<Pair<T, Map<Integer, Rectangle>>> {
    protected Map<Integer, Pair<T, Map<Integer, Rectangle>>> allItems;
    protected Deque<Integer> availableSlots = new LinkedList<>();
    protected DynamicQuadTree<Integer> root;

    public DynamicQuadTreeContainer(int maxElements) {
        root = new DynamicQuadTree<>(new Rectangle(new Vec2f(0, 0), new Vec2f(100, 100)), 0);
        allItems = new HashMap<>();
        for(int i = 0; i < maxElements; i++) {
            availableSlots.push(i);
        }
    }

    private int allocate() {
        return availableSlots.pop();
    }

    private void free(int id) {
        availableSlots.push(id);
    }

    public DynamicQuadTreeContainer(Rectangle size) {
        root = new DynamicQuadTree<>(size, 0);
        allItems = new HashMap<>();
    }

    public void resize(Rectangle size) {
        root.resize(size);
    }

    public int size() {
        return allItems.size();
    }

    public boolean empty() {
        return allItems.isEmpty();
    }

    public void clear() {
        allItems.clear();
        root.clear();
    }

    public int insert(T item, Rectangle itemSize) {
        int id = allocate();
        Map<Integer, Rectangle> mapOfPointers = root.insert(id, itemSize);
        allItems.put(id, new Pair<>(item, mapOfPointers));
        return id;
    }

    public List<T> search(Rectangle rectArea) {
        ArrayList<Integer> itemPointers = new ArrayList<>(allItems.size());
        List<T> list = new ArrayList<>();
        root.search(rectArea, itemPointers);
        for(int i : itemPointers) {
            list.add(allItems.get(i).first);
        }
        return list;
    }

    public void remove(int item) {
        if(allItems.containsKey(item)) {
            allItems.get(item).second.remove(item);
            allItems.remove(item);
            free(item);
        } else {
            Constants.LOGGER.logWarning("Object with pointer ID " + item + " does not exist in quadtree.");
        }
    }

    @Override
    public Iterator<Pair<T, Map<Integer, Rectangle>>> iterator() {
        return allItems.values().iterator();
    }
}
