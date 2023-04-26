//package com.waffle.struct;
//
//import com.waffle.core.Rectangle;
//import com.waffle.core.Vec2f;
//
//import java.util.*;
//
//public class DynamicQuadTreeContainer<T> implements Iterable<QuadTreeItem<T>> {
//    protected Map<Integer, QuadTreeLocation<T>> allItems;
//    protected DynamicQuadTree<Integer> root;
//
//    public DynamicQuadTreeContainer() {
//        root = new DynamicQuadTree<>(new Rectangle(new Vec2f(0, 0), new Vec2f(100, 100)), 0);
//        allItems = new HashMap<>();
//    }
//
//    public DynamicQuadTreeContainer(Rectangle size) {
//        root = new DynamicQuadTree<>(size, 0);
//        allItems = new HashMap<>();
//    }
//
//    public void resize(Rectangle size) {
//        root.resize(size);
//    }
//
//    public int size() {
//        return allItems.size();
//    }
//
//    public boolean empty() {
//        return allItems.isEmpty();
//    }
//
//    public void clear() {
//        allItems.clear();
//        root.clear();
//    }
//
//    public void insert(T item, Rectangle itemSize) {
//
//    }
//
//    public List<Integer> search(Rectangle rectArea) {
//        LinkedList<Integer> itemPointers = new LinkedList<>();
//        root.search(rectArea, itemPointers);
//        return itemPointers;
//    }
//
//    public QuadTreeItem<T> get(int index) {
//        return allItems.get(index);
//    }
//
//    public void remove(int item) {
//        QuadTreeItem<T> i = get(item);
//        i.pointer.container.remove(i.pointer.pointer);
//        allItems.remove(item);
//    }
//
//    @Override
//    public Iterator<QuadTreeItem<T>> iterator() {
//        return allItems.iterator();
//    }
//}