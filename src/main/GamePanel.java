package main;

import ai.PathFinder;
import data.SaveLoad;
import entity.Entity;
import entity.Player;
import tile.TileManager;
import tile_interactive.InteractiveTile;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends Canvas implements Runnable {

    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;
    public final int tileSize = originalTileSize * scale; // 48x48 tile
    public final int maxScreenCol = 20; // Left to right
    public final int maxScreenRow = 12; // Top to bottom
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // WORLD SETTINGS
    public int maxWorldCol;
    public int maxWorldRow;
    public final int maxMap = 10;
    public int currentMap = 0;

    // FULL SCREEN
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    public Graphics2D g2;
    public boolean fullScreenOn = false;

    // FPS
    int FPS = 60;

    // SYSTEM
    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    Config config = new Config(this);
    public PathFinder pFinder = new PathFinder(this);
    Thread gameThread;
    public SaveLoad saveLoad = new SaveLoad(this);
    public EntityGenerator eGenerator = new EntityGenerator(this);

    // save
    public boolean hasSave;
    // ENTITY AND OBJECT
    public Player player = new Player(this, keyH);
    public Entity[][] obj = new Entity[maxMap][20];
    public Entity[][] npc = new Entity[maxMap][20];
    public Entity[][] monster = new Entity[maxMap][250];
    public InteractiveTile[][] iTile = new InteractiveTile[maxMap][50];
    public Entity[][] projectile = new Entity[maxMap][20];
    public ArrayList<Entity> particleList = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>();

    // GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int optionState = 5;
    public final int gameOverState = 6;
    public final int transitionState = 7;
    public final int tradeState = 8;
    public final int sleepState = 9;

    public final int cutsceneState = 11;

    //Others
    public boolean bossBattleOn = false;

    public CutsceneManager csManager = new CutsceneManager(this);

    public GamePanel() {
        // Size of canvas
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));

        // Background Color to black
        this.setBackground(Color.black);

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
        gameState = titleState;

        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();

        if (fullScreenOn) {
            setFullScreen();
        }

    }

    public void resetGame(boolean restart){
        stopMusic();
        player.setDefaultPositions();
        player.restoreStatus();
        aSetter.setNPC();
        aSetter.setMonster();

        if(restart){
            aSetter.setObject();
            aSetter.setInteractiveTile();
            player.setDefaultValues();
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

        createBufferStrategy(2); // Double-buffering for better performance
        BufferStrategy bufferStrategy = getBufferStrategy();

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
                Graphics g = bufferStrategy.getDrawGraphics();
                drawToScreen(g);
                g.dispose();
                bufferStrategy.show();

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
                        monster[currentMap][i].scene();
                        monster[currentMap][i] = null;
                    }

//                    if (monster[currentMap][i] == monster[6][0] && monster[currentMap][i+1] == monster[6][1]) {
//                        if(monster[currentMap][i].life == 0 && monster[currentMap][i+1].life == 0){
//                            if(monster[currentMap][i].boss && monster[currentMap][i+1].boss){
//                                monster[currentMap][i].scene();
//                            }
//                            monster[currentMap][i].checkDrop();
//                           // monster[currentMap][i] = null;
//                           // monster[currentMap][i+1]= null;
//                        }
//                       // monster[currentMap][i] = null;;
//
//                    }
                }
            }



//            if (!monster[6][0].alive && !monster[6][1].alive) {
//                monster[6][0].scene();
//                monster[6][0] = null;
//            }

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
        }

        if (gameState == pauseState) {
            // Add logic for pause state if needed
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

            // Sort entities based on the position
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    return Integer.compare(e1.worldY, e2.worldY);
                }
            });

            // DRAW ENTITIES
            for (Entity e : entityList) {
                if (e != null) {
                    e.draw(g2);
                }
            }

            // RESET ENTITY LIST AFTER DRAWING
            entityList.clear();

            //CUTSCENES
            csManager.draw(g2);

            // UI
            ui.draw(g2);
        }
    }

    public void drawToScreen(Graphics g) {
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
    }

    public void playMusic(int i){
        music.setFile(i);
        music.loop();
    }

    public void stopMusic() {
        music.stop();
        se.stop();
    }

    public void playSE(int i){
        se.setFile(i);
        se.play();
    }

    public void setHasSave(boolean hasSave) {
        this.hasSave = hasSave;
    }

    public boolean getHasSave(){
        return hasSave;
    }
}