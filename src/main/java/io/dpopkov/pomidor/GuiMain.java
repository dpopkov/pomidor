package io.dpopkov.pomidor;

import javax.swing.*;

public class GuiMain {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                JFrame frame = new GuiFrame();
                frame.setVisible(true);
            } catch (PomidorException e) {
                System.out.println(e.getMessage());
                System.exit(0);
            }
        });
    }
}
