package com.waffle;

import com.waffle.core.Rectangle;
import com.waffle.core.Vec2f;
import com.waffle.struct.DynamicQuadTreeContainer;

public class Main {

    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "true");
        new GameTest();
//        Rectangle rect1 = new Rectangle(new Vec2f(), new Vec2f(50, 50));
//        Rectangle searchRect = new Rectangle(new Vec2f(), new Vec2f(1000, 1000));
//        DynamicQuadTreeContainer<String> c = new DynamicQuadTreeContainer<>(1000);
//        int id1 = c.insert("Hello", rect1);
//        int id2 = c.insert("World", new Rectangle(new Vec2f(10, 10), new Vec2f(50, 50)));
//        System.out.println(c.search(searchRect));
//        c.remove(id1);
//        System.out.println(c.search(searchRect));
//        c.remove(id2);
//        System.out.println(c.search(searchRect));
    }
}
