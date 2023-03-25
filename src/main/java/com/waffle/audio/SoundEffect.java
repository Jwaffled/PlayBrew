package com.waffle.audio;

import com.waffle.core.FreeableResource;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class SoundEffect implements FreeableResource {
    private Clip audioClip;
    private long clipTime;
    private Map<FloatControl.Type, AudioPropertyController> controllerMap;

    public SoundEffect(String pathToResource) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(pathToResource);
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(inputStream);

        AudioFormat format = audioStream.getFormat();
        DataLine.Info info = new DataLine.Info(Clip.class, format);

        audioClip = (Clip) AudioSystem.getLine(info);
        audioClip.open(audioStream);

        inputStream.close();
        audioStream.close();

        controllerMap = new HashMap<>() {{
//            put(
//                    FloatControl.Type.SAMPLE_RATE,
//                    new AudioPropertyController((FloatControl)audioClip.getControl(FloatControl.Type.SAMPLE_RATE), FloatControl.Type.SAMPLE_RATE)
//            );
//            put(
//                    FloatControl.Type.PAN,
//                    new AudioPropertyController((FloatControl)audioClip.getControl(FloatControl.Type.PAN), FloatControl.Type.PAN)
//            );
//            put(
//                    FloatControl.Type.REVERB_RETURN,
//                    new AudioPropertyController((FloatControl)audioClip.getControl(FloatControl.Type.REVERB_RETURN), FloatControl.Type.REVERB_RETURN)
//            );
//            put(
//                    FloatControl.Type.REVERB_SEND,
//                    new AudioPropertyController((FloatControl)audioClip.getControl(FloatControl.Type.REVERB_SEND), FloatControl.Type.REVERB_SEND)
//            );
//            put(
//                    FloatControl.Type.BALANCE,
//                    new AudioPropertyController((FloatControl)audioClip.getControl(FloatControl.Type.BALANCE), FloatControl.Type.BALANCE)
//            );
            put(
                    FloatControl.Type.MASTER_GAIN,
                    new AudioPropertyController((FloatControl)audioClip.getControl(FloatControl.Type.MASTER_GAIN), FloatControl.Type.MASTER_GAIN)
            );
        }};
    }

//    public AudioPropertyController getSampleRateControl() {
//        return controllerMap.get(FloatControl.Type.SAMPLE_RATE);
//    }
//
//    public AudioPropertyController getPanControl() {
//        return controllerMap.get(FloatControl.Type.PAN);
//    }
//
//    public AudioPropertyController getPostReverbControl() {
//        return controllerMap.get(FloatControl.Type.REVERB_RETURN);
//    }
//
//    public AudioPropertyController getPreReverbControl() {
//        return controllerMap.get(FloatControl.Type.REVERB_SEND);
//    }
//
//    public AudioPropertyController getBalanceControl() {
//        return controllerMap.get(FloatControl.Type.BALANCE);
//    }

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
