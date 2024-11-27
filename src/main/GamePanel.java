package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import ai.PathFinder;
import data.SaveLoad;
import entity.Entity;
import entity.Player;
import environment.EnvironmentManager;
import tile.TileManager;
import tile_interactive.InteractiveTile;

//This class ties together all the game components:
//  - Managing screen size, tiles, entities, and game states.
//  - Running the game loop, which continuously updates and renders the game.
//  - Supporting additional features like full-screen mode, saving/loading, sound effects, and environment effects.

public class GamePanel extends JPanel implements Runnable {
    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile                          - Defines the original size of a single tile (or grid unit) in the game, measured in pixels.
    final int scale = 3; //                                                 - Sets a scaling factor to enlarge the tiles for better visibility
    public final int tileSize = originalTileSize * scale; // 48x48 tile     - Calculates the actual size of a tile after scaling. The size of each tile is now 16×3=48 pixels
    public final int maxScreenCol = 20; // Left to right                    - Sets the maximum number of tiles that can fit horizontally across the screen.
    public final int maxScreenRow = 12; // Top to bottom                    - Sets the maximum number of tiles that can fit vertically on the screen.
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels   - Calculates the total width of the game screen in pixels. screenWidth = 48x20=960 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels  - Calculates the total height of the game screen in pixels. screenWidth = 48x12=576 pixels

    // WORLD SETTINGS
    public int maxWorldCol;                                                 //Represents the maximum number of columns in the game world grid.
    public int maxWorldRow;                                                 //Represents the maximum number of rows in the game world grid.
    public final int maxMap = 10;                                           //Specifies the maximum number of distinct maps or levels the game can have.

    public int currentMap = 0;                                              //Tracks which map the player is currently in. (currentMap = 0 could represent the first map)

    // FULL SCREEN
    int screenWidth2 = screenWidth;                                         //assigning screenWidth to screenWidth2, serves as a modifiable version of the screen width. useful for toggling between fullscreen and windowed mode, where screenWidth2 can be adjusted dynamically while preserving the original screenWidth value for reference.
    int screenHeight2 = screenHeight;                                       //Similar to screenWidth2, this variable holds a modifiable version of the height of the screen in pixels, allowing for adjustments in fullscreen mode.
    BufferedImage tempScreen;                                               //Holds an off-screen temporary image of the game screen, prevent visual problems like flickering.
    Graphics2D g2;
    public boolean fullScreenOn = false;                                    //If fullScreenOn is true, the game adjusts screenWidth2 and screenHeight2, else, the game reverts to the original resolution using screenWidth and screenHeight

    // FPS
    int FPS = 60;                                                           //FPS refers to how many individual images (or "frames") the game can display in one second.

    // SYSTEM
    public TileManager tileM = new TileManager(this);                   //manages the game's tiles, helps with drawing and handling the tiles on the screen.
    public KeyHandler keyH = new KeyHandler(this);                      //handles user input from the keyboard. It keeps track of which keys are being pressed, like moving the player character or opening the menu.
    Sound music = new Sound();                                              //refers to background music.
    Sound se = new Sound();                                                 //refers to sound effects, such as when the player jumps, shoots, or other game actions occur.
    public CollisionChecker cChecker = new CollisionChecker(this);      //checks for collisions in the game (for example, whether the player hits an object or wall).
    public AssetSetter aSetter = new AssetSetter(this);                 //responsible for placing and organizing assets (like objects, characters, and items) in the game world. It helps decide where to place things like keys, enemies, and other items that the player can interact with.
    public UI ui = new UI(this);                                        //manages the user interface (everything the player sees on the screen that isn’t part of the game world, such as menus, health bars, and score displays).
    public EventHandler eHandler = new EventHandler(this);              //handles events in the game, such as when the player triggers certain actions or encounters something in the world.
    Config config = new Config(this);                                   //handles game settings or configurations, like saving user preferences (sound volume, game difficulty, or controls).
    public PathFinder pFinder = new PathFinder(this);                   //ensures that characters can move intelligently in the game world. Without it, characters would simply get stuck in walls or move randomly, which would make the game feel awkward or broken.
    Thread gameThread;                                                      //a thread is a way for your computer or game to do multiple things at the same time.
    public SaveLoad saveLoad = new SaveLoad(this);                      //handles saving and loading the game
    public EntityGenerator eGenerator = new EntityGenerator(this);      // responsible for creating these entities (uch as players, enemies, items, obstacles, or anything) at specific times or locations in the game world.
    public EnvironmentManager eManager = new EnvironmentManager(this);  //manages aspects of the game’s environment, such as the weather, time of day, or special environmental effects.

    // save
    public boolean hasSave;                                                 //used to track whether the game has a saved state or not

    // ENTITY AND OBJECT
    public Player player = new Player(this, keyH);                      //object of the class Player, representing the player character in the game.
    public Entity[][] obj = new Entity[maxMap][20];                         //defines a 2D array called "obj", which will hold game objects (like items, decorations, or interactable objects in the world).
    public Entity[][] npc = new Entity[maxMap][10];                         //This creates a 2D array called "npc", which stores Non-Player Characters (NPCs).
    public Entity[][] monster = new Entity[maxMap][20];                     //This creates a 2D array called "monster" for storing monsters in the game.
    public InteractiveTile[][] iTile = new InteractiveTile[maxMap][50];     //2D array that stores interactive tiles in the game, could represent tiles on the map that the player can interact with, such as doors, switches, or buttons.
    public Entity[][] projectile = new Entity[maxMap][20];                  //2D array called projectile to store projectiles (like arrows, bullets, or magic spells). 20 is the maximum number of projectiles that can be active on each map at once.
    // public ArrayList<Entity> projectileList = new ArrayList<>();         // ArrayList that will hold particles (like effects or visual elements) in the game,
    public ArrayList<Entity> particleList = new ArrayList<>();              //can represent temporary effects, such as explosions, sparks, magic, or damage effects
    ArrayList<Entity> entityList = new ArrayList<>();                       //meant to store all game entities dynamically. hold players, NPCs, monsters, projectiles, particles, and other objects as they are created or destroyed during the game.

    // GAME STATE
    public int gameState;                                                   //variable that holds the current state of the game. will decide which actions to take, such as showing a menu or updating the gameplay world.
    public final int titleState = 0;
    public final int playState = 1;                                         //enters this state when the player starts playing the game, and gameState will be set to 1.
    public final int pauseState = 2;                                        //When the game is paused, gameState will be 2
    public final int dialogueState = 3;                                     //enters this state when there is a conversation or dialogue happening in the game.
    public final int characterState = 4;                                    //
    public final int optionState = 5;
    public final int gameOverState = 6;
    public final int transitionState = 7;
    public final int tradeState = 8;
    public final int sleepState = 9;

    public final int cutsceneState = 11;

    //Others
    public boolean bossBattleOn = false;

    public GamePanel() {
        // Size of panel
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));

        // Background Color to black
        this.setBackground(Color.black);

        // Add rendering performance
        this.setDoubleBuffered(true);

        // Add keyboard recognition
        this.addKeyListener(keyH);

        // Make GamePanel focused to receive input
        this.setFocusable(true);

        saveLoad = new SaveLoad(this);
    }

    public void setupGame() {
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setInteractiveTile();
        eManager.setup();
        gameState = titleState;

        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();

        if (fullScreenOn) {
            setFullScreen();
        }
    }

    public void resetGame(boolean restart){
        player.setDefaultPositions();
        player.restoreStatus();
        aSetter.setNPC();
        aSetter.setMonster();

        if(restart){
            aSetter.setObject();
            aSetter.setInteractiveTile();
            player.setDefaultValues();
            eManager.lighting.resetDay();
        }
    }


    public void setFullScreen() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);

        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1_000_000_000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                // 1. UPDATE: update the information such as character position
                update();

                // 2. DRAW: draw the screen with updated information
                drawToTempScreen();
                drawToScreen();

                delta--;
                drawCount++;
            }

            if (timer >= 1_000_000_000) {
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        if (gameState == playState) {
            // PLAYER
            player.update();

            // NPC
            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != null) {
                    npc[currentMap][i].update();
                }
            }

            for (int i = 0; i < monster[1].length; i++) {
                if (monster[currentMap][i] != null) {
                    if (monster[currentMap][i].alive && !monster[currentMap][i].dying) {
                        monster[currentMap][i].update();
                    }
                    if (!monster[currentMap][i].alive) {
                        monster[currentMap][i].checkDrop();
                        monster[currentMap][i] = null;
                    }
                }
            }

            for (int i = 0; i < projectile[1].length; i++) {
                if (projectile[currentMap][i] != null) {
                    if (projectile[currentMap][i].alive) {
                        projectile[currentMap][i].update();
                    }
                    if (!projectile[currentMap][i].alive) {
                        projectile[currentMap][i] = null;
                    }
                }
            }

            for (int i = 0; i < particleList.size(); i++) {
                if (particleList.get(i) != null) {
                    if (particleList.get(i).alive) {
                        particleList.get(i).update();
                    }
                    if (!particleList.get(i).alive) {
                        particleList.remove(i);
                    }
                }
            }

            for (int i = 0; i < iTile[1].length; i++) {
                if (iTile[currentMap][i] != null) {
                    iTile[currentMap][i].update();
                }
            }
            eManager.update();
        }


        if (gameState == pauseState) {
        }
    }

    public void drawToTempScreen() {
        // TITLE SCREEN
        if (gameState == titleState) {
            ui.draw(g2);
        }
        // OTHERS
        else {
            // TILE
            tileM.draw(g2);

            // INTERACTIVE TILE
            for (int i = 0; i < iTile[1].length; i++) {
                if (iTile[currentMap][i] != null) {
                    iTile[currentMap][i].draw(g2);
                }
            }

            // ADD ENTITIES TO THE LIST
            entityList.add(player);

            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != null) {
                    entityList.add(npc[currentMap][i]);
                }
            }

            for (int i = 0; i < obj[1].length; i++) {
                if (obj[currentMap][i] != null) {
                    entityList.add(obj[currentMap][i]);
                }
            }

            for (int i = 0; i < monster[1].length; i++) {
                if (monster[currentMap][i] != null) {
                    entityList.add(monster[currentMap][i]);
                }
            }

            for (int i = 0; i < projectile[1].length; i++) {
                if (projectile[currentMap][i] != null) {
                    entityList.add(projectile[currentMap][i]);
                }
            }

            for (int i = 0; i < particleList.size(); i++) {
                if (particleList.get(i) != null) {
                    entityList.add(particleList.get(i));
                }
            }

            // SORT
            Collections.sort(entityList, new Comparator<Entity>() {

                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }

            });

            // DRAW ENTITIES
            for (int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2);
            }

            // EMPTY ENTITIES
            entityList.clear();

            //Environment
            eManager.draw(g2);

            // UI
            ui.draw(g2);


        }
    }

    public void drawToScreen() {
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic(int i) {
        music.setFile(i);
        music.stop();
    }

    public void playSE(int i) {
        se.setFile(i);
        se.play();
    }

    public void changeArea(){
        //TODO 34:43 missing method
    }

    public boolean getHasSave() {
        return hasSave;
    }

    public void setHasSave(boolean hasSave) {
        this.hasSave = hasSave;
    }

    public void removeTempEntity() {
        for (int mapNum = 0; mapNum < maxMap; mapNum++) {
            for (int i = 0; i < obj[1].length; i++) {
                if (obj[mapNum][i] != null && obj[mapNum][i].temp == true) {
                    obj[mapNum][i] = null;
                }
            }
        }
    }
}
