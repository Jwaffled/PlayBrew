package com.waffle.audio;

import com.waffle.core.FreeableResource;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class StereoSoundEffect implements FreeableResource {
    private final Clip audioClip;
    private long clipTime;
    private final Map<FloatControl.Type, AudioPropertyController> controllerMap;

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

    public AudioPropertyController getPanControl() {
        return controllerMap.get(FloatControl.Type.PAN);
    }

    public AudioPropertyController getBalanceControl() {
        return controllerMap.get(FloatControl.Type.BALANCE);
    }

    public AudioPropertyController getVolumeControl() {
        return controllerMap.get(FloatControl.Type.MASTER_GAIN);
    }

    public void addLineListener(LineListener l) {
        audioClip.addLineListener(l);
    }

    public void start() {
        if (!audioClip.isRunning()) {
            audioClip.setMicrosecondPosition(0);
            audioClip.start();
        }
    }

    public void setLoopAmount(int loops) {
        audioClip.loop(loops);
    }

    public void restart() {
        audioClip.setMicrosecondPosition(0);
    }

    public void pause() {
        clipTime = audioClip.getMicrosecondLength();
        audioClip.stop();
    }

    public void resume() {
        audioClip.setMicrosecondPosition(clipTime);
        audioClip.start();
    }

    public void free() {
        audioClip.close();
    }
}