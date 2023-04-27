package com.waffle.struct;

import com.waffle.core.Pair;
import com.waffle.core.Rectangle;
import com.waffle.core.Vec2f;

import java.util.*;

public class DynamicQuadTree<T> {
    public final static int MAX_DEPTH = 8;
    protected int depth;
    protected Rectangle rect;
    protected Rectangle[] childRects = new Rectangle[4];
    protected ArrayList<DynamicQuadTree<T>> children = new ArrayList<>(4);
    //private final Deque<Integer> availableSlots = new LinkedList<>();
    protected Map<T, Rectangle> items = new HashMap<>();

    public DynamicQuadTree() {
        rect = new Rectangle(new Vec2f(0, 0), new Vec2f(100, 100));
        depth = 0;
        for(int i = 0; i < 4; i++) {
            children.add(null);
        }
//        for(int i = 0; i < 1000; i++) {
//            availableSlots.push(i);
//        }
        resize(rect);
    }

//    private int allocate() {
//        return availableSlots.pop();
//    }
//
//    private void delete(int id) {
//        availableSlots.push(id);
//    }

    public DynamicQuadTree(Rectangle size, int depth) {
        rect = size;
        this.depth = depth;
        for(int i = 0; i < 4; i++) {
            children.add(null);
        }
        resize(rect);
    }

    public void resize(Rectangle area) {
        clear();
        rect = area;
        Vec2f childSize = rect.size.div(2);
        childRects[0] = new Rectangle(rect.pos, childSize);
        childRects[1] = new Rectangle(new Vec2f(rect.pos.x + childSize.x, rect.pos.y), childSize);
        childRects[2] = new Rectangle(new Vec2f(rect.pos.x, rect.pos.y + rect.size.y), childSize);
        childRects[3] = new Rectangle(rect.pos.add(childSize), childSize);
    }

    public void clear() {
        items.clear();

        for(int i = 0; i < 4; i++) {
            childRects[i] = null;
            if(children.get(i) != null) {
                children.get(i).clear();
            }
        }
    }

    public int size() {
        int count = items.size();
        for(int i = 0; i < 4; i++) {
            if(children.get(i) != null) {
                count += children.get(i).size();
            }
        }

        return count;
    }

    public Map<T, Rectangle> insert(T item, Rectangle itemSize) {
        for(int i = 0; i < 4; i++) {
            if(childRects[i].contains(itemSize)) {
                if(depth + 1 > MAX_DEPTH) {
                    if(children.get(i) == null) {
                        children.set(i, new DynamicQuadTree<>(childRects[i], depth + 1));
                    }

                    return children.get(i).insert(item, itemSize);
                }
            }
        }

        items.put(item, itemSize);
        return items;
    }

    public List<T> search(Rectangle rectArea) {
        List<T> listItems = new ArrayList<>();
        search(rectArea, listItems);
        return listItems;
    }

    public void search(Rectangle rectArea, List<T> listItems) {
        for(Map.Entry<T, Rectangle> p : items.entrySet()) {
            if(rectArea.overlaps(p.getValue())) {
                listItems.add(p.getKey());
            }
        }

        for(int i = 0; i < 4; i++) {
            if(children.get(i) != null) {
                if(rectArea.contains(childRects[i])) {
                    children.get(i).items(listItems);
                } else if(childRects[i].overlaps(rectArea)) {
                    children.get(i).search(rectArea, listItems);
                }
            }
        }
    }

    public void items(List<T> listItems) {
        listItems.addAll(items.keySet());

        for(int i = 0; i < 4; i++) {
            if(children.get(i) != null) {
                children.get(i).items(listItems);
            }
        }
    }

    public Rectangle area() {
        return rect;
    }
}
