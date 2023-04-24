package com.waffle.core;

public class Pair<One, Two> {
    public final One first;
    public final Two second;

    public Pair(One o, Two t) {
        first = o;
        second = t;
    }
}
