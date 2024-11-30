package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
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

    //background image
    private BufferedImage backgroundImage;


    public CutsceneManager(GamePanel gp) {
        this.gp = gp;
        // Store paragraphs as separate elements
        initializeTextPages();
        loadResources();
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        if (sceneNum == opening) {
            playOpeningScene();
        }
    }

    private void playOpeningScene() {
        final float FADE_STEP = 0.005f; // Control fade-in/out speed
        final int HOLD_DURATION = 200; // 10 seconds at 60 FPS

        // Skip scene if Enter key is pressed
        if (gp.keyH.escapePressed) {
            sceneNum = NA;
            gp.player.setDefaultValues();
            gp.gameState = gp.playState;
            scenePhase = 0;
        }

        // Handle the scene phases
        switch (scenePhase) {
            case 0: // Fade-in phase
                alpha = Math.min(alpha + FADE_STEP, 1f);
                if (alpha == 1f) {
                    scenePhase = 1;
                    counter = 0;
                }
                break;

            case 1: // Hold text phase
                if (++counter > HOLD_DURATION) {
                    scenePhase = 2;
                }
                break;

            case 2: // Fade-out phase
                alpha = Math.max(alpha - FADE_STEP, 0f);
                if (alpha == 0f) {
                    if (currentTextPage < openingTextPages.size() - 1) {
                        currentTextPage++;
                        scenePhase = 0; // Reset to fade-in for the next page
                    } else {
                        sceneNum = NA; // Reset the scene number
                        gp.gameState = gp.playState; // Transition to gameplay
                        scenePhase = 0; // Prepare for potential future scenes
                    }
                }
                break;
        }

        // Draw the background image if available, otherwise fallback to black
        if (backgroundImage != null) {
            g2.drawImage(backgroundImage, 0, 0, gp.screenWidth, gp.screenHeight, null);
        } else {
            g2.setColor(Color.black);
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        }

        // Apply alpha transparency effect
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

        // Draw the current text page
        drawParagraph(g2, openingTextPages.get(currentTextPage));

        // Reset composite to default for subsequent rendering
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
    private void initializeTextPages(){
        openingTextPages.add("In the heart of a dense forest lay Bukidgrown, a serene farming village where two brothers, Hakobe and his older sibling Andres, grew up under the warmth of their family's modest home.");
        openingTextPages.add("One fateful evening, Hakobe and Andres overheard their parents locked in a heated argument. The following days unraveled their lives—dividing the family as their parents separated.");
        openingTextPages.add("Hakobe left for the city, seeking a fresh start, while Andres remained tethered to the soil of Bukidgrown, clinging to the familiar. As their bond fades, Hakobe hears rumors of disappearances in Bukidgrown.");
        openingTextPages.add("After learning of a massacre in his hometown, he returns, sneaks past police, and discovers a dungeon gate where he and Andres once played. Then Hakobe entered.");
    }

    private void loadResources() {
        try {
            backgroundImage = ImageIO.read(getClass().getResource("/res/ui/openingScene.png"));
        } catch (IOException e) {
            e.printStackTrace();
            backgroundImage = null; // Handle missing image gracefully
        }
    }

    private void drawParagraph(Graphics2D g2, String text) {


        int margin = 100; // Larger margin for centering
        int lineSpacing = 40;
        int maxWidth = gp.screenWidth - 2 * margin;
        int yStart = gp.screenHeight - 180; // Start slightly above the center
        int y = yStart;

        // Set the color for the rectangle to black and draw it
        g2.setColor(Color.BLACK); // Set color for the rectangle
        g2.fillRect(100, 350, maxWidth, yStart - 200);

        g2.setColor(Color.WHITE);
        g2.drawRect(100, 350, maxWidth, yStart - 200);

        // Set the color for the text to white and draw it
        g2.setColor(Color.WHITE); // Set color for the text
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 28f));


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
