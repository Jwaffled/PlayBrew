package com.waffle.animation;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * A class representing a normal animation
 */
public class SimpleAnimation extends Animation {
    /**
     * Instantiates a SimpleAnimation given an array of sprites
     * @param anim
     */
    public SimpleAnimation(Sprite[] anim) {
        super(anim);
    }

//    public SimpleAnimation(String dir) throws IOException {
//        super(dir);
//    }
//
//    public SimpleAnimation(String dir, int updateFrequency) throws IOException {
//        super(dir, updateFrequency);
//    }

    /**
     * Instantiates a SimpleAnimation given file path names
     * @param files path names all leading to image files
     * @throws IOException upon a bad/nonexistent file
     */
    public SimpleAnimation(String dir, String... files) throws IOException {
        super(dir, files);
    }

    /**
     * Checks if the animation is finished playing
     * @return whether the animation is finished playing
     */
    public boolean finished() {
        return index > frames.length;
    }

    /**
     * Gets the next frame of the animation. Will return the last frame indefinitely after finishing
     * @return the next frame of the animation
     */
    @Override
    public BufferedImage getFrame() {
        int ret = index;
        if(counter.isReady()) {
            if(ret >= frames.length) {
                ret = frames.length - 1;
            }
            index++;
        }

        counter.update();
        return frames[index].image;
    }

    /**
     * Gets the number of frames that have passed since the animation ended. If the animation hasn't ended, returns 0
     * @return the number of frames since the animation finished
     */
    public int framesSinceFinished() {
        if(index - frames.length > 0) {
            return index - frames.length;
        }
        return 0;
    }
}
