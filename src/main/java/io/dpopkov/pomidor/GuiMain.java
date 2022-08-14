package io.dpopkov.pomidor;

import javax.swing.*;

public class GuiMain {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new GuiFrame();
            frame.setVisible(true);
        });
    }
}
