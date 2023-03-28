package com.waffle.animation;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * A class representing an animation that can loop after reaching the end
 */
public class LoopingAnimation extends Animation {
    private int loopPoint;
    private int numLoops;

    /**
     * Instantiates a LoopingAnimation given an array of sprites
     * @param anim the array of sprites to use
     */
    public LoopingAnimation(Sprite[] anim) {
        super(anim);
        loopPoint = 0;
        numLoops = 0;
    }

    public LoopingAnimation(String dir) throws IOException {
        super(dir);
    }

    public LoopingAnimation(String dir, int updateFrequency) throws IOException {
        super(dir, updateFrequency);
    }

    /**
     * Instantiates a LoopingAnimation given file path names
     * @param files the path names all leading to image files
     * @throws IOException upon a bad/nonexistent file
     */
    public LoopingAnimation(String dir, String... files) throws IOException {
        super(dir, files);
        loopPoint = 0;
        numLoops = 0;
    }

    /**
     * Instantiates a LoopingAnimation given an array of sprites and a frame index to return to
     * @param loopPoint the frame index to return to
     * @param anim the array of sprites to use
     */
    public LoopingAnimation(int loopPoint, Sprite[] anim) {
        super(anim);
        if(loopPoint >= anim.length || loopPoint < 0) {
            throw new IndexOutOfBoundsException("Cannot set loop point (" + loopPoint + ") outside of the animation [0," + frames.length + ")");
        }
        this.loopPoint = loopPoint;
        numLoops = 0;
    }

    /**
     * Instantiates a LoopingAnimation given file path names and a frame index to return to
     * @param loopPoint the frame index to return to
     * @param files the path names (all leading to image files)
     * @throws IOException upon a bad/nonexistent file
     */
    public LoopingAnimation(int loopPoint, String dir, String... files) throws IOException {
        super(dir, files);
        if(loopPoint >= files.length || loopPoint < 0) {
            throw new IndexOutOfBoundsException("Cannot set loop point (" + loopPoint + ") outside of the animation [0," + frames.length + ")");
        }
        this.loopPoint = loopPoint;
    }

    /**
     * Gets the next frame of the animation
     * @return the next frame of the animation
     */
    @Override
    public BufferedImage getFrame() {
        int ret = index;
        if(counter.isReady()) {
            index++;
            if(index == frames.length) {
                index = loopPoint;
                numLoops++;
            }
        }

        counter.update();

        return frames[ret].image;
    }

    /**
     * Returns the number of times this animation has looped
     * @return the number of times this animation has looped
     */
    public int getNumLoops() {
        return numLoops;
    }

    /**
     * Resets the LoopingAnimation to its original state
     */
    public void reset() {
        super.reset();
        numLoops = 0;
    }

    /**
     * Gets the index the animation loops back to
     * @return the index the animation loops back to
     */
    public int getLoopPoint() {
        return loopPoint;
    }
}
