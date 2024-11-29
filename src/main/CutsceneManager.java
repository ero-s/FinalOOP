package main;

import java.awt.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class CutsceneManager {
    GamePanel gp;
    Graphics2D g2;

    public int sceneNum;
    public int scenePhase;
    int counter = 0;
    float alpha = 0f; // Transparency for fade effect
    ArrayList<String> openingTextPages = new ArrayList<>();
    int currentTextPage = 0;

    // Scene numbers
    public final int NA = 0;
    public final int opening = 1;

    public CutsceneManager(GamePanel gp) {
        this.gp = gp;
        // Store paragraphs as separate elements
        openingTextPages.add("In the heart of a dense forest lay Bukidgrown, a serene farming village where two brothers, Hakobe and his older sibling Andres, grew up under the warmth of their family's modest home.");
        openingTextPages.add("One fateful evening, Hakobe and Andres overheard their parents locked in a heated argument. The following days unraveled their lives—dividing the family as their parents separated.");
        openingTextPages.add("Hakobe left for the city, seeking a fresh start, while Andres remained tethered to the soil of Bukidgrown, clinging to the familiar. As their bond fades, Hakobe hears rumors of disappearances in Bukidgrown.");
        openingTextPages.add("After learning of a massacre in his hometown, he returns, sneaks past police, and discovers a dungeon gate where he and Andres once played. Then Hakobe entered.");
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        if (sceneNum == opening) {
            playOpeningScene();
        }
    }

    private void playOpeningScene() {
        if (scenePhase == 0) {
            // Fade-in effect
            alpha += 0.005f;
            if (alpha >= 1f) {
                alpha = 1f;
                scenePhase = 1;
                counter = 0;
            }
        } else if (scenePhase == 1) {
            // Hold text
            counter++;
            if (counter > 600) { // Hold for 10 seconds at 60 FPS
                scenePhase = 2;
            }
        } else if (scenePhase == 2) {
            // Fade-out effect
            alpha -= 0.005f;
            if (alpha <= 0f) {
                alpha = 0f;
                if (currentTextPage < openingTextPages.size() - 1) {
                    currentTextPage++;
                    scenePhase = 0; // Reset to fade-in for the next page
                } else {
                    sceneNum = NA; // Reset the scene number
                    gp.gameState = gp.playState; // Transition to gameplay
                    scenePhase = 0; // Reset phase for potential future scenes
                }
            }
        }

        // Draw the black background
        g2.setColor(Color.black);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // Draw the current paragraph
        drawParagraph(g2, openingTextPages.get(currentTextPage));
    }

    private void drawParagraph(Graphics2D g2, String text) {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 28f));
        g2.setColor(new Color(1f, 1f, 1f, alpha)); // Use alpha for transparency

        int margin = 100; // Larger margin for centering
        int lineSpacing = 40;
        int maxWidth = gp.screenWidth - 2 * margin;

        int yStart = (gp.screenHeight / 2) - 100; // Start slightly above the center
        int y = yStart;

        // Wrap text to fit within screen width using StringTokenizer
        StringTokenizer tokenizer = new StringTokenizer(text);
        StringBuilder line = new StringBuilder();

        while (tokenizer.hasMoreTokens()) {
            String word = tokenizer.nextToken();
            String testLine = line + word + " ";
            if (g2.getFontMetrics().stringWidth(testLine) > maxWidth) {
                // Draw the line when it exceeds the maximum width
                int x = (gp.screenWidth - g2.getFontMetrics().stringWidth(line.toString())) / 2; // Center the line
                g2.drawString(line.toString(), x, y);
                line = new StringBuilder(word + " ");
                y += lineSpacing;
            } else {
                line.append(word).append(" ");
            }
        }
        // Draw the last line
        int x = (gp.screenWidth - g2.getFontMetrics().stringWidth(line.toString())) / 2;
        g2.drawString(line.toString(), x, y);
    }
}
