package com.waffle.struct;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class StaticQuadTreeContainer<T> implements Iterable<T> {
    protected List<T> allItems;
    protected StaticQuadTree<Integer> root;

    public StaticQuadTreeContainer() {
        root = new StaticQuadTree<>(new Rectangle(new Vec2f(0, 0), new Vec2f(100, 100)), 0);
        allItems = new ArrayList<>();
    }

    public StaticQuadTreeContainer(Rectangle size) {
        root = new StaticQuadTree<>(size, 0);
        allItems = new ArrayList<>();
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
        allItems.add(item);

        root.insert(allItems.size() - 1, itemSize);
    }

    public List<Integer> search(Rectangle rectArea) {
        LinkedList<Integer> itemPointers = new LinkedList<>();
        root.search(rectArea, itemPointers);
        return itemPointers;
    }

    public T get(int index) {
        return allItems.get(index);
    }

    @Override
    public Iterator<T> iterator() {
        return allItems.iterator();
    }
}
