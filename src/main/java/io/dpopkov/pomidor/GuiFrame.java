package io.dpopkov.pomidor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.TimerTask;

public class GuiFrame extends JFrame {
    public static final String DEFAULT_WAV_FILENAME = "pomidor.wav";
    private WavPlayer wavPlayer;
    private final JTextField delayInputField;
    private final JButton startPomidorButton;
    private final java.util.Timer playTimer = new java.util.Timer("PlaySoundThread");
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
    private final JLabel countDownLabel;
    private Timer countDownTimer;
    private volatile boolean countDownRunning;
    private long startTime;
    private long duration;

    public GuiFrame() throws HeadlessException {
        setTitle("Pomidor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 100);
        setResizable(false);

        var panel = new JPanel();
        add(panel);

        var playOnceButton = new JButton("Play Test Once");
        playOnceButton.addActionListener(e -> wavPlayer.play());
        panel.add(playOnceButton);

        delayInputField = new JTextField(4);
        delayInputField.setEnabled(false);
        delayInputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                startPomidorButton.setEnabled(delayInputField.getText().trim().length() != 0);
            }
        });
        panel.add(delayInputField);

        startPomidorButton = new JButton("Start Pomidor");
        startPomidorButton.setEnabled(false);
        startPomidorButton.addActionListener(e -> {
            startPlayTimer();
            startCountDownTimer();
        });
        panel.add(startPomidorButton);

        countDownLabel = new JLabel();
        panel.add(countDownLabel);

        loadWavFile();
    }

    private void startPlayTimer() {
        String delayText = delayInputField.getText();
        boolean useMinutes = false;
        if (delayText.endsWith("m")) {
            useMinutes = true;
            delayText = delayText.substring(0, delayText.length() - 1);
        } else if (delayText.endsWith("s")) {
            delayText = delayText.substring(0, delayText.length() - 1);
        }
        int multiplier = 1000;
        if (useMinutes) {
            multiplier *= 60;
        }
        duration = Integer.parseInt(delayText) * multiplier;
        playTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                playSound();
            }
        }, duration);
        countDownRunning = true;
    }

    private void playSound() {
        wavPlayer.play();
        countDownRunning = false;
        countDownLabel.setText("");
    }

    private void startCountDownTimer() {
        startTime = System.currentTimeMillis();
        initCountDownTimer();
        countDownTimer.start();
    }

    private void initCountDownTimer() {
        countDownTimer = new Timer(500, e -> {
            if (startTime < 0) {
                startTime = System.currentTimeMillis();
            }
            long now = System.currentTimeMillis();
            long clockTime = now - startTime;
            if (!countDownRunning) {
                countDownTimer.stop();
                countDownLabel.setText("");
            }
            countDownLabel.setText(dateFormat.format(duration - clockTime));
        });
        countDownTimer.setInitialDelay(0);
    }

    private void loadWavFile() {
        String pathToResource = "/sound/wav/" + DEFAULT_WAV_FILENAME;
        String pathToFile = GuiFrame.class.getResource(pathToResource).getFile();
        wavPlayer = new WavPlayer(pathToFile);
        delayInputField.setEnabled(true);
    }
}
