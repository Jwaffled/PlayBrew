package com.waffle.audio;

import com.waffle.core.FreeableResource;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * A class managing a 2D (Stereo) sound effect<br>
 * MUST call <code>.free()</code> to prevent memory leaks
 */
public class StereoSoundEffect implements FreeableResource {
    private final Clip audioClip;
    private long clipTime;
    private final Map<FloatControl.Type, AudioPropertyController> controllerMap;

    /**
     * Constructs a sound effect from a path relative to the resources folder
     * @param pathToResource the path to the stereo sound file
     * @throws UnsupportedAudioFileException if the file is not a supported format
     * @throws IOException if the file is not found or is malformed
     * @throws LineUnavailableException if the line assigned to play this effect is in use
     */
    public StereoSoundEffect(String pathToResource) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(pathToResource);
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(inputStream);

        AudioFormat format = audioStream.getFormat();
        if(format.getChannels() < 2) {
            throw new IllegalArgumentException("Audio file must be stereo (supplied " + format.getChannels() + " channels).");
        }
        DataLine.Info info = new DataLine.Info(Clip.class, format);

        audioClip = (Clip) AudioSystem.getLine(info);
        audioClip.open(audioStream);

        inputStream.close();
        audioStream.close();

        controllerMap = new HashMap<>() {{
            put(
                    FloatControl.Type.PAN,
                    new AudioPropertyController((FloatControl)audioClip.getControl(FloatControl.Type.PAN))
            );
            put(
                    FloatControl.Type.BALANCE,
                    new AudioPropertyController((FloatControl)audioClip.getControl(FloatControl.Type.BALANCE))
            );
            put(
                    FloatControl.Type.MASTER_GAIN,
                    new AudioPropertyController((FloatControl)audioClip.getControl(FloatControl.Type.MASTER_GAIN))
            );
        }};
    }

    /**
     * Returns the pan control (used for spacial sound) associated with this effect
     * @return the pan control for this effect
     */
    public AudioPropertyController getPanControl() {
        return controllerMap.get(FloatControl.Type.PAN);
    }

    /**
     * Returns the balance control (used for spacial sound) associated with this effect
     * @return the balance control for this effect
     */
    public AudioPropertyController getBalanceControl() {
        return controllerMap.get(FloatControl.Type.BALANCE);
    }

    /**
     * Returns the volume control associated with this effect
     * @return the volume control for this effect
     */
    public AudioPropertyController getVolumeControl() {
        return controllerMap.get(FloatControl.Type.MASTER_GAIN);
    }

    /**
     * Adds a line listener to the clip to listen for specific audio events
     * @param l the LineListener to add
     */
    public void addLineListener(LineListener l) {
        audioClip.addLineListener(l);
    }

    /**
     * Starts the audio effect from the beginning
     */
    public void start() {
        if (!audioClip.isRunning()) {
            audioClip.setMicrosecondPosition(0);
            audioClip.start();
        }
    }

    /**
     * Sets the amount of times an effect will loop after it completes one playthrough
     * @param loops the amount of times an effect will loop
     */
    public void setLoopAmount(int loops) {
        audioClip.loop(loops);
    }

    /**
     * Resets the audio cue to the 0-second mark
     */
    public void restart() {
        audioClip.setMicrosecondPosition(0);
    }

    /**
     * Pauses the audio
     */
    public void pause() {
        clipTime = audioClip.getMicrosecondLength();
        audioClip.stop();
    }

    /**
     * Resumes audio from the saved clip time<br>
     * Should be used in combination with <code>pause()</code>
     */
    public void resume() {
        audioClip.setMicrosecondPosition(clipTime);
        audioClip.start();
    }

    /**
     * Frees the memory associated with this sound effect
     */
    public void free() {
        audioClip.close();
    }
}