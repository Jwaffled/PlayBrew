package com.waffle.audio;

import com.waffle.core.FreeableResource;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;

public class SoundEffect implements FreeableResource {
    private final String path;
    private InputStream inputStream;
    private AudioInputStream audioStream;
    private Clip audioClip;
    private DataLine.Info info;
    private long clipTime;

    public SoundEffect(String pathToResource) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        path = pathToResource;
        inputStream = getClass().getClassLoader().getResourceAsStream(pathToResource);
        audioStream = AudioSystem.getAudioInputStream(inputStream);
        AudioFormat format = audioStream.getFormat();
        info = new DataLine.Info(Clip.class, format);
        audioClip = (Clip) AudioSystem.getLine(info);
        audioClip.open(audioStream);
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
        try {
            inputStream.close();
            audioStream.close();
        } catch(IOException e) {
            System.out.println("Audio system error: " + e.getMessage());
        }
    }
}
