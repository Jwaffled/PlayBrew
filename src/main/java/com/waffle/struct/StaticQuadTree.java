package com.waffle.struct;

import com.waffle.core.Pair;
import com.waffle.core.Rectangle;
import com.waffle.core.Vec2f;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class StaticQuadTree<T> {
    public final static int MAX_DEPTH = 8;
    protected int depth;
    protected Rectangle rect;
    protected Rectangle[] childRects = new Rectangle[4];
    protected ArrayList<StaticQuadTree<T>> children = new ArrayList<>(4);
    protected ArrayList<Pair<Rectangle, T>> items;

    public StaticQuadTree() {
        rect = new Rectangle(new Vec2f(0, 0), new Vec2f(100, 100));
        depth = 0;
        items = new ArrayList<>();
        for(int i = 0; i < 4; i++) {
            children.add(null);
        }
        resize(rect);
    }

    public StaticQuadTree(Rectangle size, int depth) {
        rect = size;
        this.depth = depth;
        items = new ArrayList<>();
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

    public void insert(T item, Rectangle itemSize) {
        for(int i = 0; i < 4; i++) {
            if(childRects[i].contains(itemSize)) {
                if(depth + 1 > MAX_DEPTH) {
                    if(children.get(i) == null) {
                        children.set(i, new StaticQuadTree<>(childRects[i], depth + 1));
                    }

                    children.get(i).insert(item, itemSize);
                    return;
                }
            }
        }

        items.add(new Pair<>(itemSize, item));
    }

    public List<T> search(Rectangle rectArea) {
        LinkedList<T> listItems = new LinkedList<>();
        search(rectArea, listItems);
        return listItems;
    }

    public void search(Rectangle rectArea, List<T> listItems) {
        for(Pair<Rectangle, T> p : items) {
            if(rectArea.overlaps(p.first)) {
                listItems.add(p.second);
            }
        }

        for(int i = 0; i < 4; i++) {
            if(children.size() > i && children.get(i) != null) {
                if(rectArea.contains(childRects[i])) {
                    children.get(i).items(listItems);
                } else if(childRects[i].overlaps(rectArea)) {
                    children.get(i).search(rectArea, listItems);
                }
            }
        }
    }

    public void items(List<T> listItems) {
        for(Pair<Rectangle, T> p : items) {
            listItems.add(p.second);
        }

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
