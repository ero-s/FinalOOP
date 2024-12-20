package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class CutsceneManager {
    private final GamePanel gp;
    private Graphics2D g2;

    public int sceneNum;
    public int scenePhase;
    private int counter = 0;
    private float alpha = 0f; // Transparency for fade effect
    private final List<String> openingTextPages = new ArrayList<>();
    private final List<String> pickleRickBackstoryTextPages = new ArrayList<>();
    private final List<String> jacOLanternBackstoryTextPages = new ArrayList<>();
    private final List<String> jackNJillBackstoryTextPages = new ArrayList<>();
    private final List<String> cursedOnionHalfSceneBackstoryTextPages = new ArrayList<>();
    private final List<String> cursedOnionBackstoryTextPages = new ArrayList<>();
    private int currentTextPage = 0;

    // Scene numbers
    public static final int NA = 0;
    public static final int OPENING = 1;
    public static final int PICKLE_RICK_BACKSTORY = 2;
    public static final int JACoLANTERN_BACKSTORY = 3;
    public static final int JACKNJILL_BACKSTORY = 4;
    public static final int CURSED_ONION_HALF_SCENE_BACKSTORY = 5;
    public static final int CURSED_ONION_BACKSTORY = 6;



    // Background images
    private BufferedImage openingBackground;
    private BufferedImage pickleRickBackground;
    private BufferedImage jacBackground;
    private BufferedImage jackNJillBackground;
    private BufferedImage cursedOnionHalfSceneBackground;
    private BufferedImage cursedOnionBackground;


    public CutsceneManager(GamePanel gp) {
        this.gp = gp;
        initializeTextPages();
        loadResources();
    }

    private void loadResources() {
        try {
            openingBackground = ImageIO.read(getClass().getResource("/res/ui/openingScene.png"));
            pickleRickBackground = ImageIO.read(getClass().getResource("/res/ui/pickleRickCS.png"));
            jacBackground = ImageIO.read(getClass().getResource("/res/ui/jacOLanternCS.png"));
            jackNJillBackground = ImageIO.read(getClass().getResource("/res/ui/jackNJillCS.png"));
            cursedOnionHalfSceneBackground = ImageIO.read(getClass().getResource("/res/ui/cursedOnionHalfSceneCS.png"));
            cursedOnionBackground = ImageIO.read(getClass().getResource("/res/ui/cursedOnionCS.png"));
        } catch (IOException e) {
            e.printStackTrace();
            openingBackground = null;
            pickleRickBackground = null;
            jacBackground = null;
            jackNJillBackground = null;
            cursedOnionHalfSceneBackground = null;
            cursedOnionBackground = null;

        }
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        switch (sceneNum) {
            case OPENING:
                playScene(openingBackground, openingTextPages);
                break;
            case PICKLE_RICK_BACKSTORY :
                playScene(pickleRickBackground, pickleRickBackstoryTextPages);
                break;
            case JACoLANTERN_BACKSTORY :
                playScene(jacBackground, jacOLanternBackstoryTextPages);
                break;
            case JACKNJILL_BACKSTORY :
                playScene(jackNJillBackground, jackNJillBackstoryTextPages);
                break;
            case CURSED_ONION_HALF_SCENE_BACKSTORY :
                playScene(cursedOnionHalfSceneBackground, cursedOnionHalfSceneBackstoryTextPages);
                break;
            case CURSED_ONION_BACKSTORY :
                playScene(cursedOnionBackground, cursedOnionBackstoryTextPages);
                break;
        }
    }

    private void playScene(BufferedImage backgroundImage, List<String> textPages) {
        final float FADE_STEP = 0.005f; // Control fade-in/out speed
        final int HOLD_DURATION = 200; // 10 seconds at 60 FPS

        if (gp.keyH.escapePressed) {
            resetScene();
            return;
        }

        switch (scenePhase) {
            case 0 :
                alpha = Math.min(alpha + FADE_STEP, 1f);
                if (alpha == 1f) {
                    scenePhase = 1;
                    counter = 0;
                }
                break;
            case 1 :
                if (++counter > HOLD_DURATION) {
                    scenePhase = 2;
                }
                break;
            case 2 :
                alpha = Math.max(alpha - FADE_STEP, 0f);
                if (alpha == 0f) {
                    if (currentTextPage < textPages.size() - 1) {
                        currentTextPage++;
                        scenePhase = 0;
                    } else {
                        resetScene();
                    }
                }
                break;
        }

        renderScene(backgroundImage, textPages);
    }

    private void resetScene() {
        sceneNum = NA;
       // gp.player.setDefaultValues();
        gp.gameState = gp.playState;
        scenePhase = 0;
    }

    private void renderScene(BufferedImage backgroundImage, List<String> textPages) {
        if (backgroundImage != null) {
            g2.drawImage(backgroundImage, 0, 0, gp.screenWidth, gp.screenHeight, null);
        } else {
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        }

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        drawParagraph(textPages.get(currentTextPage));
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    private void initializeTextPages() {
        openingTextPages.add("In the heart of a dense forest lay Bukidgrown, a serene farming village where two brothers, Hakobe and his older sibling Andres, grew up under the warmth of their family's modest home.");
        openingTextPages.add("One fateful evening, Hakobe and Andres overheard their parents locked in a heated argument. The following days unraveled their lives—dividing the family as their parents separated.");
        openingTextPages.add("Hakobe left for the city, seeking a fresh start, while Andres remained tethered to the soil of Bukidgrown, clinging to the familiar. As their bond fades, Hakobe hears rumors of disappearances in Bukidgrown.");
        openingTextPages.add("After learning of a massacre in his hometown, he returns, sneaks past police, and discovers a dungeon gate where he and Andres once played. Then Hakobe entered.");

        pickleRickBackstoryTextPages.add("Pickle Rick grew up in poverty which made him develop a strong obsession with money and power, he witnessed the cruelty of the world, where those who are without wealth or power were mistreated.");
        pickleRickBackstoryTextPages.add("This then sowed the seeds of in the future he wanted to be the one who has power over everything and makes people do his bidding. He then fell in love, but the woman he loved was taken away and became a slave");
        pickleRickBackstoryTextPages.add("he became more powerless because he was incapable of buying her. He worked tirelessly desperately trying to earn money, but she was murdered one day.");
        pickleRickBackstoryTextPages.add("This led Pickle Rick to bear more anger in the world which made him start enslaving people and letting them work tirelessly like he did. He was mad at the world that he made others suffer like he did");
        pickleRickBackstoryTextPages.add("North of where summer thrives, a town of scares lies. A being of immense power lays dormant, ruling with fear Jack O' Lantern waits for your arrival.");

        jacOLanternBackstoryTextPages.add("Jac was once known as a gifted performer, a child prodigy. Jac was once known as a gifted performer, a child prodigy, and his family's pride and joy.");
        jacOLanternBackstoryTextPages.add("As he grew older, Jac’s parents showered him with expectations but rarely offered affection or support. Instead of nurturing his talents, they demanded perfection, driving him to practice relentlessly.");
        jacOLanternBackstoryTextPages.add("Their indifference cut deeply, fostering a growing resentment within him. Jac felt like a mere prop in their show, overshadowed by their brilliance and neglect.");
        jacOLanternBackstoryTextPages.add("Years later he became the best ever performer there ever was but was blinded by his ideals which led to him enslaving people and making them suffer.");
        jacOLanternBackstoryTextPages.add("This sadistic behavior was a boost to his ego, and this eventually started this cruel rule on the circus. ");

        jackNJillBackstoryTextPages.add("Jack and Jill were once ordinary children, but after the snow village was attacked by bandits, they were forced to fend for themselves leaving the village behind and hid in a cave.");
        jackNJillBackstoryTextPages.add("They learned to rely on each other in their darkest times. Jack, protective and fierce, embraced his darker instincts, ");
        jackNJillBackstoryTextPages.add("while Jill became cunning and strategic, using her intelligence to navigate the harsh world around them. With no trust in the people, they shut off any sort of connections.");

        cursedOnionBackstoryTextPages.add("Cursed onion, who brings misfortune to all it touches, causing crops to wither and die, has been Andres the whole time. Andres stumbled upon an erupting dungeon bound to turn into an outbreak");
        cursedOnionBackstoryTextPages.add("To seal the dungeon he encountered a nearby dungeon researcher who had been monitoring the gate, the researcher says that in order for it to be sealed one must embrace the curse of the dungeon.");
        cursedOnionBackstoryTextPages.add("The curse served as a means to protect Hakobe, as he knew that dark forces were emerging from the dungeons. By becoming a cursed guardian,");
        cursedOnionBackstoryTextPages.add("he inadvertently became a shield for Hakobe, isolating himself to keep him safe from the growing threats until they could reunite and restore balance together.");

        cursedOnionHalfSceneBackstoryTextPages.add("The farm was their safe haven, where every corner held a story of their childhood adventures, from building forts in the hay to watching sunsets that painted the sky with hope.");
    }

    private void drawParagraph(String text) {
        int margin = 100;
        int lineSpacing = 40;
        int maxWidth = gp.screenWidth - 2 * margin;
        int yStart = gp.screenHeight - 180;
        int y = yStart;

        g2.setColor(Color.BLACK);
        g2.fillRect(margin, 350, maxWidth, yStart - 200);

        g2.setColor(Color.WHITE);
        g2.drawRect(margin, 350, maxWidth, yStart - 200);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 28f));

        StringTokenizer tokenizer = new StringTokenizer(text);
        StringBuilder line = new StringBuilder();

        while (tokenizer.hasMoreTokens()) {
            String word = tokenizer.nextToken();
            String testLine = line + word + " ";
            if (g2.getFontMetrics().stringWidth(testLine) > maxWidth) {
                drawCenteredLine(line.toString(), y);
                line = new StringBuilder(word + " ");
                y += lineSpacing;
            } else {
                line.append(word).append(" ");
            }
        }
        drawCenteredLine(line.toString(), y);
    }

    private void drawCenteredLine(String text, int y) {
        int x = (gp.screenWidth - g2.getFontMetrics().stringWidth(text)) / 2;
        g2.drawString(text, x, y);
    }
}
