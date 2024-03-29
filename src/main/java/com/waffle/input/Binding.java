package com.waffle.input;

public class Binding {
    public final static int NO_BUTTON = -255;
    private final int button, modifier;
    private final Input input;
    public Binding(int code, Input input) {
        button = code;
        modifier = NO_BUTTON;
        this.input = input;
    }
    public Binding(int codeOne, int codeTwo, Input input) {
        button = codeOne;
        modifier = codeTwo;
        this.input = input;
    }
    public boolean triggered() {
        if(modifier == NO_BUTTON) {
            return input.read(button);
        }

        return input.read(modifier) && input.read(button);
    }
}
