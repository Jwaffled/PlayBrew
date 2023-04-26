package com.waffle.struct;

import com.waffle.core.Rectangle;
import com.waffle.core.Vec2f;

import java.util.*;

public class DynamicQuadTreeContainer<T> implements Iterable<QuadTreeItem<T>> {
    protected Map<Integer, T> allItems;
    protected Deque<Integer> availableSlots = new LinkedList<>();
    protected DynamicQuadTree<Integer> root;

    public DynamicQuadTreeContainer() {
        root = new DynamicQuadTree<>(new Rectangle(new Vec2f(0, 0), new Vec2f(100, 100)), 0);
        allItems = new HashMap<>();
        for(int i = 0; i < 10_000; i++) {
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

    public void insert(T item, Rectangle itemSize) {
        int id = allocate();
        root.insert(id, itemSize);
        allItems.put(id, item);
    }

    public List<Integer> search(Rectangle rectArea) {
        LinkedList<Integer> itemPointers = new LinkedList<>();
        root.search(rectArea, itemPointers);
        return itemPointers;
    }

    public void remove(int item) {
        // TODO

    }

    @Override
    public Iterator<QuadTreeItem<T>> iterator() {
        return null;
    }
}
