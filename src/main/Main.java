package main;   //program belongs to a package named main

import javax.swing.*;

public class Main {
    // MAIN METHOD

    //window is a static variable that represents the game's main window.
    public static JFrame window;

    public static void main(String[] args) {
        window = new JFrame();                                                   //new window (JFrame) is created
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);          //program closes when the user clicks the "X" button
        window.setResizable(false);                                              //Prevents the user from changing the size of the game window.
        window.setTitle("Harvest Fest: A Brotherly Tale");                       //Gives the window a title that appears at the top.

        // Call GamePanel
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);  //GamePanel is added to the window, making it the visible part of the program.

        gamePanel.config.loadConfig();      //Calls a method (loadConfig) to load settings like full-screen mode.
        if (gamePanel.fullScreenOn) {
            window.setUndecorated(true);    //If full-screen is enabled, window.setUndecorated(true) removes the title bar and borders
        }

        window.pack();                       //Automatically sizes the window to fit its contents
        window.setLocationRelativeTo(null);  //Places the window in the center of the screen.
        window.setVisible(true);             //Displays the window on the screen.

        gamePanel.setupGame();              //setupGame() prepares the game (e.g., initializes variables, positions characters).
        gamePanel.startGameThread();        //startGameThread() starts the game loop, which handles updates (e.g., animations, movements) and drawing graphics.
    }
    public void setIcon() {
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("res/player/boy_attack_right_2.png"));
        window.setIconImage(icon.getImage());
    }
}
