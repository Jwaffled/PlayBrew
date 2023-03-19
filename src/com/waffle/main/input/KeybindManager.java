package com.waffle.main.input;

import java.util.HashMap;
import java.util.Map;

// TODO: Differentiate between mouse and keyboard inputs more clearly; addMouseBind is kind of scuffed.
public class KeybindManager {
    private Map<String, Binding> keybindMap;

    public KeybindManager() {
        keybindMap = new HashMap<>();
    }

    public void addKeybind(String name, int keycode) {
        if(keybindMap.containsKey(name)) {
            throw new IllegalStateException("Cannot register keybind '" + name + "' more than once.");
        }

        keybindMap.put(name, new Binding(keycode, Input.getInstance()));
    }

    public void addKeybind(String name, int keycode, int modifierKeycode) {
        if(keybindMap.containsKey(name)) {
            throw new IllegalStateException("Cannot register keybind '" + name + "' more than once.");
        }

        keybindMap.put(name, new Binding(keycode, modifierKeycode, Input.getInstance()));
    }

    public void addMouseBind(String name, int mousecode) {
        if(keybindMap.containsKey(name)) {
            throw new IllegalStateException("Cannot register keybind '" + name + "' more than once.");
        }

        keybindMap.put(name, new Binding(-mousecode, Input.getInstance()));
    }

    public void addMouseBind(String name, int mousecode, int modifierCode) {
        if(keybindMap.containsKey(name)) {
            throw new IllegalStateException("Cannot register keybind '" + name + "' more than once.");
        }

        keybindMap.put(name, new Binding(-mousecode, modifierCode, Input.getInstance()));
    }

    public boolean triggered(String name) {
        Binding binding = keybindMap.get(name);
        if(binding == null) {
            throw new IllegalStateException("Keybind '" + name + "' does not exist (Did you register it?).");
        }

        return binding.triggered();
    }
}
