package com.waffle.struct;

import com.waffle.core.Pair;
import com.waffle.core.Rectangle;

import java.util.Map;

public class QuadTreeLocation<T> {
    public final Map<Integer, Pair<Rectangle, T>> container;
    public final int pointer;

    public QuadTreeLocation(Map<Integer, Pair<Rectangle, T>> map, int location) {
        container = map;
        pointer = location;
    }
}
