package com.waffle;

import com.waffle.core.Rectangle;
import com.waffle.core.Vec2f;
import com.waffle.struct.DynamicQuadTreeContainer;

public class Main {

    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "true");
        new GameTest();
//        DynamicQuadTreeContainer<String> container = new DynamicQuadTreeContainer<>(100);
//        container.resize(new Rectangle(new Vec2f(), new Vec2f(1000, 1000)));
//        int h = container.insert("Hello", new Rectangle(new Vec2f(), new Vec2f(20, 20)));
//        int gb = container.insert("Goodbye", new Rectangle(new Vec2f(10, 50), new Vec2f(30, 30)));
//        int test = container.insert("Test", new Rectangle(new Vec2f(100, 50), new Vec2f(30, 30)));
//        System.out.println(container.search(new Rectangle(new Vec2f(), new Vec2f(1000, 1000))));
//        container.remove(h);
//        container.remove(test);
//        container.remove(gb);
//        System.out.println(container.search(new Rectangle(new Vec2f(), new Vec2f(1000, 1000))));
    }
}
