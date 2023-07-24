package io.dpopkov.pomidor;

import javax.swing.*;

import static io.dpopkov.pomidor.AppConstants.*;

public class GuiMain {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                GuiFrame frame = new GuiFrame();
                if (containsOption(args, OPTION_DELAY_25_MINUTES)) {
                    frame.setDelay(DELAY_25_MINUTES);
                }
                frame.setVisible(true);
            } catch (PomidorException e) {
                System.out.println(e.getMessage());
                System.exit(0);
            }
        });
    }

    private static boolean containsOption(String[] args, String option) {
        for (String a : args) {
            if (option.equals(a)) {
                return true;
            }
        }
        return false;
    }
}
