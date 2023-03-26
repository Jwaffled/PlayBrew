package com.waffle.animation;

import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class Animation {

    protected Sprite[] frames;
    protected int index;

    /**
     * Instantiates an Animation given an array of sprites
     * @param anim the array of sprites to be used
     */
    public Animation(Sprite[] anim) {
        frames = anim;
        index = 0;
    }

    /**
     * Instantiates an Animation given a series of file paths
     * @param files the pathnames containing image files
     * @throws IOException upon a bad/nonexistent file
     */
    public Animation(String... files) throws IOException {
        frames = new Sprite[files.length];
        for(int i = 0; i < frames.length; i++) {
            frames[index] = new Sprite(files[i]);
        }
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
