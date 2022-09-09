package io.dpopkov.pomidor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
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

        GridLayout gridLayout = new GridLayout(3, 4);

        var panel = new JPanel();
        panel.setLayout(gridLayout);
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
                enableStartButton();
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

        JRadioButton radioButton25m = new JRadioButton("25m");
        radioButton25m.addActionListener(new DelayInputSetter("25m"));
        JRadioButton radioButton5m = new JRadioButton("5m");
        radioButton5m.addActionListener(new DelayInputSetter("5m"));
        ButtonGroup radioGroup = new ButtonGroup();
        radioGroup.add(radioButton25m);
        radioGroup.add(radioButton5m);
        panel.add(radioButton25m);

        countDownLabel = new JLabel();
        panel.add(countDownLabel);

        panel.add(radioButton5m);

        loadWavFile();
    }

    private void enableStartButton() {
        startPomidorButton.setEnabled(delayInputField.getText().trim().length() != 0);
    }

    private class DelayInputSetter implements ActionListener {
        private final String text;

        private DelayInputSetter(String text) {
            this.text = text;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            delayInputField.setText(text);
            enableStartButton();
        }
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
        String pathToFile;
        File currentDirFile = new File(DEFAULT_WAV_FILENAME);
        if (currentDirFile.exists()) {
            pathToFile = currentDirFile.getPath();
        } else {
            String pathToResource = "/sound/wav/" + DEFAULT_WAV_FILENAME;
            pathToFile = GuiFrame.class.getResource(pathToResource).getFile();
            File resourceFile = new File(pathToFile);
            if (!resourceFile.exists()) {
                throw new PomidorException("Cannot find sound file " + DEFAULT_WAV_FILENAME);
            }
        }
        wavPlayer = new WavPlayer(pathToFile);
        delayInputField.setEnabled(true);
    }
}
