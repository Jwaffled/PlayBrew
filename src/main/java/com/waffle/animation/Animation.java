package com.waffle.animation;

import com.waffle.core.UpdateCounter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class Animation {

    protected Sprite[] frames;
    protected int index;
    protected UpdateCounter counter;

    /**
     * Instantiates an Animation given an array of sprites
     * @param anim the array of sprites to be used
     */
    public Animation(Sprite[] anim) {
        frames = anim;
        index = 0;
        counter = new UpdateCounter(1);
    }

    /**
     * Instantiates an Animation given a series of file paths
     * @param files the pathnames containing image files
     * @throws IOException upon a bad/nonexistent file
     */
    public Animation(String dir, String... files) throws IOException {
        frames = new Sprite[files.length];
        for(int i = 0; i < frames.length; i++) {
            frames[i] = new Sprite(dir + files[i]);
        }
        counter = new UpdateCounter(1);
    }

    public Animation(String directory) throws IOException {
        final File f = new File(getClass().getClassLoader().getResource(directory).getPath());
        final String[] files = f.list();
        frames = new Sprite[files.length];
        for(int i = 0; i < files.length; i++) {
            frames[i] = new Sprite(directory + "/" + files[i]);
        }
        counter = new UpdateCounter(1);
    }

    public Animation(String dir, int updateFrequency) throws IOException {
        this(dir);
        counter = new UpdateCounter(updateFrequency);
    }

    public void setUpdateFrequency(int frequency) {
        counter = new UpdateCounter(frequency);
    }

    /**
     * Retrieves the next frame for rendering
     * @return the next image to render
     */
    public abstract BufferedImage getFrame();

    /**
     * Gets the numbered frame (starting at one (1)) the animation will return next
     * @return the number of the next frame
     */
    public int getFrameNumber() {
        return index + 1;
    }

    /**
     *  Returns the animation to its pre-played condition
     */
    public void reset() {
        index = 0;
    }

    /**
     * Gets the number of frames in the animation
     * @return the number of frames in the animation
     */
    public int getAnimationLength() {
        return frames.length;
    }




}
