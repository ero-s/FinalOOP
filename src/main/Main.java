package main;

import javax.swing.*;

public class Main {
    // MAIN METHOD

    public static JFrame window;


    public static void main(String[] args) {

        window = new JFrame();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Harvest Fest: A Brotherly Tale");
        new Main().setIcon();

        // Call GamePanel
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        gamePanel.config.loadConfig();
        if (gamePanel.fullScreenOn) {
            window.setUndecorated(true);
        }

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
    public void setIcon() {
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("res/player/boy_attack_right_2.png"));
        window.setIconImage(icon.getImage());
    }
}
