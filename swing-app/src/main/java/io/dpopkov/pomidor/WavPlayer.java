package io.dpopkov.pomidor;

import javax.sound.sampled.*;

public class WavPlayer {
    private final BufferedFile bufferedFile;

    public WavPlayer(String pathToFile) {
        bufferedFile = new BufferedFile(pathToFile);
    }

    public void play() {
        new Thread(() -> {
            try (AudioInputStream inputStream = AudioSystem.getAudioInputStream(bufferedFile.getInputStream())) {
                Clip clip = AudioSystem.getClip();
                AudioListener audioListener = new AudioListener();
                clip.addLineListener(audioListener);
                clip.open(inputStream);
                clip.start();
                audioListener.waitUntilDone();
            } catch (Exception e) {
                throw new PomidorException("Failed to play sound", e);
            }
        }).start();
    }
}
