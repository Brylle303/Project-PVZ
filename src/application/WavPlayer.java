package application;

import javax.sound.sampled.*;
import java.io.File;

public class WavPlayer {

    private static Clip clip;

    public static void play(String filePath, boolean loop) {
        stop();

        try {
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            clip = AudioSystem.getClip();
            clip.open(audioStream);

            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                clip.start();
            }

        } catch (Exception e) {
            System.out.println("Error playing WAV file: " + filePath);
            e.printStackTrace();
        }
    }

    public static void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}
