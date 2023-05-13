package com.waffle.core;

import java.util.HashMap;
import java.util.Map;

public class SceneManager {
    private Map<String, Scene> scenes;
    private String currentSceneName;
    private Scene currentScene;
    private String previousSceneName;
    private Scene previousScene;

    /**
     * Constructs a scene manager with a specified map of scenes
     * @param scenes map of scenes
     */
    public SceneManager(Map<String, Scene> scenes) {
        this.scenes = scenes;
    }

    /**
     * Constructs a scene manager with no scenes by default
     */
    public SceneManager() {
        this.scenes = new HashMap<>();
    }

    /**
     * Adds a scene to the manager
     * @param name name of the scene
     * @param scene the scene to be added
     */
    public void addScene(String name, Scene scene) {
        scene.start();
        scenes.put(name, scene);
    }

    /**
     * Removes a scene from the manager
     * @param name the name of the scene to remove
     */
    public void removeScene(String name) {
        scenes.remove(name);
    }

    /**
     * Sets the current scene in the manager
     * @param name the name of the scene to set
     */
    public void setCurrentScene(String name) {
        if(!scenes.containsKey(name)) {
            throw new IllegalStateException("Scene with name '" + name + "' does not exist!");
        }
        previousScene = currentScene;
        previousSceneName = currentSceneName;
        currentSceneName = name;
        currentScene = scenes.get(name);
    }

    /**
     * Returns the current scene name
     * @return the current scene name
     */
    public String getCurrentSceneName() {
        return currentSceneName;
    }

    /**
     * Returns the previous scene name
     * @return the previous scene name
     */
    public String getPreviousSceneName() {
        return previousSceneName;
    }

    /**
     * Returns the current scene
     * @return the current scene
     */
    public Scene getCurrentScene() {
        return currentScene;
    }

    /**
     * Returns the previous scene, null if there wasn't any
     * @return the previous scene
     */
    public Scene getPreviousScene() {
        return previousScene;
    }
    public String toString() {
        return scenes.toString();
    }
}
