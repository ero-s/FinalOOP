package main;

import entity.Entity;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font maruMonica;
    Entity e = new Entity(gp);
    BufferedImage heart_full, heart_half, heart_blank, crystal_full, crystal_blank, coin;
    public boolean messageOn = false;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public boolean gameFinished = false;
    public String currentDialogue = "";
    public int commandNum = 0;
    public int titleScreenState = 0;
    public int playerSlotCol = 0;
    public int playerSlotRow = 0;
    public int npcSlotCol = 0;
    public int npcSlotRow = 0;
    int subState = 0;
    int counter = 0;
    public Entity npc;
    public int charIndex = 0;
    public String combinedText = "";

    public UI(GamePanel gp) {
        this.gp = gp;

        try {
            InputStream is = getClass().getResourceAsStream("/res/font/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // CREATE HUD OBJECT
        Entity heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;

        Entity crystal = new OBJ_ManaCrystal(gp);
        crystal_full = crystal.image;
        crystal_blank = crystal.image2;

        Entity bronzeCoin = new OBJ_Coin_Bronze(gp);
        coin = bronzeCoin.down1;
    }

    public void addMessage(String text) {
        message.add(text);
        messageCounter.add(0);
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(maruMonica);
        g2.setColor(Color.white);

        // TITLE STATE
        if (gp.gameState == gp.titleState) { drawTitleScreen(); }

        // PLAY STATE
        if (gp.gameState == gp.playState) {
            drawPlayerLife();
            drawMonsterLife();
            drawMessage();
        }

        // PAUSE STATE
        if (gp.gameState == gp.pauseState) {
            drawPlayerLife();
            drawPauseScreen();
        }

        // DIALOGUE STATE
        if (gp.gameState == gp.dialogueState) { drawDialogueScreen(); }

        // CHARACTER STATE
        if (gp.gameState == gp.characterState) {
            drawCharacterScreen();
            drawInventory(gp.player, true);
        }

        // OPTION STATE
        if (gp.gameState == gp.optionState) { drawOptionScreen(); }

        // GAME OVER STATE
        if (gp.gameState == gp.gameOverState) { drawGameOverScreen(); }

        // TRANSITION STATE
        if (gp.gameState == gp.transitionState) { drawTransition(); }

        // TRADE STATE
        if (gp.gameState == gp.tradeState) { drawTradeScreen(); }

        // SLEEP STATE
        if (gp.gameState == gp.sleepState){ drawSleepScreen(); }
    }

    public void drawPlayerLife() {
        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;
        int i = 0;

        // DRAW MAX LIFE
        while (i < gp.player.maxLife / 2) {
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x += gp.tileSize;
        }

        // RESET
        x = gp.tileSize / 2;
        y = gp.tileSize / 2;
        i = 0;

        // DRAW CURRENT LIFE
        while (i < gp.player.life) {
            g2.drawImage(heart_half, x, y, null);
            i++;
            if (i < gp.player.life) {
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x += gp.tileSize;
        }

        // DRAW MAX MANA
        x = (gp.tileSize / 2) - 5;
        y = (int) (gp.tileSize * 1.5);
        i = 0;

        while (i < gp.player.maxMana) {
            g2.drawImage(crystal_blank, x, y, null);
            i++;
            x += 35;
        }

        // DRAW MANA
        x = (gp.tileSize / 2) - 5;
        y = (int) (gp.tileSize * 1.5);
        i = 0;

        while (i < gp.player.mana) {
            g2.drawImage(crystal_full, x, y, null);
            i++;
            x += 35;
        }
    }

    public void drawMonsterLife(){

        // MOSNTER HEALTH BAR
        for(int i = 0; i < gp.monster[1].length; i++){

            Entity monster = gp.monster[gp.currentMap][i];

            if(monster != null && monster.inCamera()) {
                if (monster.hpBarOn && !monster.boss) {
                    double oneScale = (double) gp.tileSize / monster.maxLife;
                    double hpBarValue = oneScale * monster.life;

                    g2.setColor(new Color(35, 35, 35));
                    g2.fillRect(monster.getScreenX() - 1, monster.getScreenY() - 16, gp.tileSize + 2, 12);

                    g2.setColor(new Color(255, 0, 30));
                    g2.fillRect(monster.getScreenX(), monster.getScreenY() - 15, (int) hpBarValue, 10);

                    monster.hpBarCounter++;

                    if (monster.hpBarCounter > 600) {
                        monster.hpBarCounter = 0;
                        monster.hpBarOn = false;
                    }
                } else if(monster.boss){

                    double oneScale = (double) gp.tileSize * 8 / monster.maxLife;
                    double hpBarValue = oneScale * monster.life;

                    int x = gp.screenWidth/2 - gp.tileSize * 4;
                    int y = gp.tileSize * 10;

                    g2.setColor(new Color(35, 35, 35));
                    g2.fillRect(x - 1, y - 1, gp.tileSize * 8 + 2, 22);

                    g2.setColor(new Color(255, 0, 30));
                    g2.fillRect(x, y, (int) hpBarValue, 20);

                    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24f));
                    g2.setColor(Color.white);
                    g2.drawString(monster.name, x + 4, y - 10);
                }
            }
        }


    }
    public void drawMessage() {
        int messageX = gp.tileSize;
        int messageY = gp.tileSize * 4;

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32f));

        for (int i = 0; i < message.size(); i++) {
            if (message.get(i) != null) {
                g2.setColor(Color.black);
                g2.drawString(message.get(i), messageX + 2, messageY + 2);

                g2.setColor(Color.white);
                g2.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i, counter);

                messageY += 50;

                if (messageCounter.get(i) > 180) {
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }

    public void drawCarrot(int x, int y, int width, int height){
        BufferedImage carrot = e.setup("/res/ui/carrot", gp.tileSize, gp.tileSize);
        g2.drawImage(carrot,x,y,width, height, null);
    }

    public void drawTitleScreen() {
        if (titleScreenState == 0) {

            //gp.stopMusic();
            BufferedImage title = e.setup("/res/ui/title", 2160, 1440);
            g2.drawImage(title,0,0,960, 580, null);


            String text ;
            int x;
            int y = gp.tileSize * 5;

            // MAIN MENU
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));

            text = "NEW GAME";
            x = getXforCenteredText(text);
            y += gp.tileSize * 3.5;
            g2.drawString(text, x, y);
            if (commandNum == 0) {
                drawCarrot(x-64, y-64, 64,64);
                if(gp.keyH.enterPressed){
                    gp.saveLoad.save();
                    titleScreenState = 1;
                }
            }

            text = "LOAD GAME";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 1) {
                drawCarrot(x-64, y-64, 64,64);
            }

            text = "QUIT";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 2) {
                drawCarrot(x-64, y-64, 64,64);
            }
        }

        else if (titleScreenState == 1) {
           drawLoadExist();
        }
        else if(titleScreenState == 2){
            drawNoLoad();
        }

    }
    public void drawLoadExist() {
        BufferedImage title = e.setup("/res/ui/title", 1080, 720);
        g2.drawImage(title,0,0,960, 580, null);
        int x,y;

        String text;

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32f));

        text = "Load exists, Are you sure to create game?";

        // shadow
        g2.setColor(Color.black);
        x = getXforCenteredText(text);
        y = gp.tileSize * 2;
        g2.drawString(text, x, y);

        //main(?)
        g2.setColor(Color.white);
        g2.drawString(text, x-4, y-4);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
        text = "BACK";
        x = getXforCenteredText(text);
        y += gp.tileSize * 2;
        g2.drawString(text, x, y);
        if(commandNum == 0){
            drawCarrot(x-gp.tileSize/2, y-gp.tileSize/2, gp.tileSize/2, gp.tileSize/2);
            if(gp.keyH.enterPressed){
                titleScreenState = 0;
                return;
            }
        }

        text = "NEW GAME";
        x = getXforCenteredText(text);
        y += gp.tileSize/2;
        g2.drawString(text, x, y);
        if(commandNum == 1){
            drawCarrot(x-gp.tileSize/2, y-gp.tileSize/2, gp.tileSize/2, gp.tileSize/2);
            if(gp.keyH.enterPressed){
                gp.player.setDefaultValues();
                gp.saveLoad.save();
//                drawNarrationDialogueScreen();
//                startNewGame();
                commandNum = 0;
            }
        }
    }

    public void drawNoLoad(){
        commandNum = 0;

        BufferedImage title = e.setup("/res/ui/title_1", 2160, 1440);
        g2.drawImage(title,0,0,960, 580, null);

        int x, y;

        String text;

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32f));

        text = "You have no saved progresses.";

        // Shadow
        g2.setColor(Color.black);
        x = getXforCenteredText(text);
        y = gp.tileSize * 5;
        g2.drawString(text, x, y);

        // Main text
        g2.setColor(Color.white);
        g2.drawString(text, x - 4, y - 4);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
        text = "BACK";
        x = getXforCenteredText(text);
        y += gp.tileSize * 2;

        g2.drawString(text, x, y);

        if (commandNum == 0) {
            drawCarrot(x - 64, y-64,64,64);
            if (gp.keyH.enterPressed) {
                titleScreenState = 0;
            }
        }
    }

    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSED";

        int x = getXforCenteredText(text);

        int y = gp.screenHeight / 2;

        g2.drawString(text, x, y);
    }

    public void drawDialogueScreen() {
        // WINDOW
        int x = gp.tileSize * 3;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 6);
        int height = gp.tileSize * 4;

        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
        x += gp.tileSize;
        y += gp.tileSize;
        if(npc.dialogues[npc.dialogueSet][npc.dialogueIndex] != null){
//            currentDialogue = npc.dialogues[npc.dialogueSet][npc.dialogueIndex];

            char characters[] = npc.dialogues[npc.dialogueSet][npc.dialogueIndex].toCharArray();
            if(charIndex < characters.length){
                gp.playSE(17);
                String s = String.valueOf((characters[charIndex]));
                combinedText = combinedText + s;
                currentDialogue = combinedText;
                charIndex++;
            }
            if(gp.keyH.enterPressed){
                charIndex = 0;
                combinedText = "";
                if(gp.gameState == gp.dialogueState && gp.gameState == gp.cutsceneState){
                    npc.dialogueIndex++;
                    gp.keyH.enterPressed = false;
                }
            }
        }
        else{
            npc.dialogueIndex = 0;
            if(gp.gameState == gp.dialogueState){
                gp.gameState = gp.playState;
            }
            if(gp.gameState == gp.cutsceneState){
                gp.csManager.scenePhase++;
            }
        }

        for(String line : currentDialogue.split("\n")){
            g2.drawString(line, x, y);
            y += 40;
        }
    }

    public void drawCharacterScreen() {
        // FRAME
        final int frameX = gp.tileSize * 2;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize * 5;
        final int frameHeight = gp.tileSize * 10;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // TEXT
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32f));

        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 35;

        // NAMES
        g2.drawString("Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Life", textX, textY);
        textY += lineHeight;
        g2.drawString("Mana", textX, textY);
        textY += lineHeight;
        g2.drawString("Strength", textX, textY);
        textY += lineHeight;
        g2.drawString("Dexterity", textX, textY);
        textY += lineHeight;
        g2.drawString("Attack", textX, textY);
        textY += lineHeight;
        g2.drawString("Defense", textX, textY);
        textY += lineHeight;
        g2.drawString("Exp", textX, textY);
        textY += lineHeight;
        g2.drawString("Next Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Coin", textX, textY);
        textY += lineHeight + 10;
        g2.drawString("Weapon", textX, textY);
        textY += lineHeight + 15;
        g2.drawString("Shield", textX, textY);
        textY += lineHeight;

        // VALUES
        int tailX = (frameX + frameWidth) - 30;
        textY = frameY + gp.tileSize; // RESET textY
        String value;

        value = String.valueOf(gp.player.level);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.mana + "/" + gp.player.maxMana);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.strength);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.dexterity);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.attack);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.defense);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.exp);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.nextLevelExp);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.coin);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY - 24, null);
        textY += gp.tileSize;
        g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize, textY - 24, null);

    }

    public void drawInventory(Entity entity, boolean cursor) {
        int frameX = 0;
        int frameY = 0;
        int frameWidth = 0;
        int frameHeight = 0;
        int slotCol = 0;
        int slotRow = 0;

        if (entity == gp.player) {
            // FRAME
            frameX = gp.tileSize * 12;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize * 6;
            frameHeight = gp.tileSize * 5;
            slotCol = playerSlotCol;
            slotRow = playerSlotRow;
        } else {
            // FRAME
            frameX = gp.tileSize * 2;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize * 6;
            frameHeight = gp.tileSize * 5;
            slotCol = npcSlotCol;
            slotRow = npcSlotRow;
        }

        // FRAME
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // SLOT
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize + 3;

        // DRAW PLAYER'S ITEM
        for (int i = 0; i < entity.inventory.size(); i++) {
            if (entity.inventory.get(i).equals(entity.currentWeapon)
                    || entity.inventory.get(i).equals(entity.currentShield)
                    ||entity.inventory.get(i).equals(entity.currentLight) ) {
                g2.setColor(new Color(240, 190, 90));
                g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
            }

            g2.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);

            // DISPLAY AMOUNT
            if(entity == gp.player && entity.inventory.get(i).amount > 1) {

                g2.setFont(g2.getFont().deriveFont(32f));
                int amountX;
                int amountY;

                String s = "" + entity.inventory.get(i).amount;
                amountX = getXforAlignToRightText(s, slotX + 44);
                amountY = slotY + gp.tileSize;

                // SHADOW
                g2.setColor(new Color(60,60,60));
                g2.drawString(s, amountX, amountY);
                // NUMBER
                g2.setColor(Color.white);
                g2.drawString(s, amountX - 3, amountY - 3);
            }
            slotX += slotSize;

            if (i == 4 || i == 9 || i == 14) {
                slotX = slotXstart;
                slotY += slotSize;
            }
        }

        // CURSOR
        if (cursor) {
            int cursorX = slotXstart + (slotSize * slotCol);
            int cursorY = slotYstart + (slotSize * slotRow);
            int cursorWidth = gp.tileSize;
            int cursorHeight = gp.tileSize;

            // DRAW CURSOR
            g2.setColor(Color.white);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

            // DESCRIPTION ITEM FRAME
            int dFrameX = frameX;
            int dFrameY = frameY + frameHeight;
            int dFrameWidth = frameWidth;
            int dFrameHeight = gp.tileSize * 3;

            // DRAW DESCRIPTION TEXT
            int textX = dFrameX + 20;
            int textY = dFrameY + gp.tileSize;
            g2.setFont(g2.getFont().deriveFont(28f));

            int itemIndex = getItemIndexOnSlot(slotCol, slotRow);

            if (itemIndex < entity.inventory.size()) {
                drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);

                for (String line : entity.inventory.get(itemIndex).description.split("\n")) {
                    g2.drawString(line, textX, textY);
                    textY += 32;
                }
            }
        }
    }

    public void drawGameOverScreen() {
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));

        text = "Game Over";

        // SHADOW
        g2.setColor(Color.black);
        x = getXforCenteredText(text);
        y = gp.tileSize * 4;
        g2.drawString(text, x, y);

        // MAIN TEXT
        g2.setColor(Color.white);
        g2.drawString(text, x - 4, y - 4);

        // RETRY
        g2.setFont(g2.getFont().deriveFont(50f));
        text = "Retry";
        x = getXforCenteredText(text);
        y += gp.tileSize * 4;
        g2.drawString(text, x, y);

        if (commandNum == 0) {
            drawCarrot(x-gp.tileSize/2, y-gp.tileSize/2, gp.tileSize/2, gp.tileSize/2);
        }

        // QUIT
        text = "Quit";
        x = getXforCenteredText(text);
        y += 55;
        g2.drawString(text, x, y);

        if (commandNum == 1) {
            drawCarrot(x-gp.tileSize/2, y-gp.tileSize/2, gp.tileSize/2, gp.tileSize/2);
        }
    }

    public void drawOptionScreen() {
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32f));

        // SUB WINDOW
        int frameX = gp.tileSize * 6;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 8;
        int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch (subState) {
            case 0: options_top(frameX, frameY); break;
            case 1: options_fullScreenNotification(frameX, frameY); break;
            case 2: options_control(frameX, frameY); break;
            case 3: options_endGameConfirmation(frameX, frameY); break;
            case 4: options_saveGame(frameX+gp.tileSize, frameY+gp.tileSize);break;
            case 5: options_SaveOnQuit(frameX, frameY);
        }

        gp.keyH.enterPressed = false;
    }

    public void options_saveGame(int frameX, int frameY){
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize * 3/2;
        commandNum = 0;
        currentDialogue = "Saving Game...";
        gp.saveLoad.save();

        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        // BACK
        textY += gp.tileSize * 6/2;
        g2.drawString("Back", textX, textY);
        if (commandNum == 0) {
            drawCarrot(textX-gp.tileSize/2, textY-gp.tileSize/2, gp.tileSize/2, gp.tileSize/2);
            if (gp.keyH.enterPressed) {
                subState = 0;
            }
        }
    }

    public void options_top(int frameX, int frameY) {
        int textX;
        int textY;

        // TITLE
        String text = "Options";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        // FULL SCREEN ON/OFF
        textX = frameX + gp.tileSize;
        textY += gp.tileSize * 2;
        g2.drawString("Full Screen", textX, textY);
        if (commandNum == 0) {
            drawCarrot(textX-gp.tileSize/2, textY-gp.tileSize/2, gp.tileSize/2, gp.tileSize/2);

            if (gp.keyH.enterPressed) {
                if (!gp.fullScreenOn) {
                    gp.fullScreenOn = true;
                } else if (gp.fullScreenOn) {
                    gp.fullScreenOn = false;
                }
                subState = 1;
            }
        }

        // MUSIC
        textY += gp.tileSize;
        g2.drawString("Music", textX, textY);
        if (commandNum == 1) {
            drawCarrot(textX-gp.tileSize/2, textY-gp.tileSize/2, gp.tileSize/2, gp.tileSize/2);
        }

        // SOUND EFFECT
        textY += gp.tileSize;
        g2.drawString("SE", textX, textY);
        if (commandNum == 2) {
            drawCarrot(textX-gp.tileSize/2, textY-gp.tileSize/2, gp.tileSize/2, gp.tileSize/2);
        }

        // CONTROL
        textY += gp.tileSize;
        g2.drawString("Control", textX, textY);
        if (commandNum == 3) {
            drawCarrot(textX-gp.tileSize/2, textY-gp.tileSize/2, gp.tileSize/2, gp.tileSize/2);

            if (gp.keyH.enterPressed) {
                subState = 2;
                commandNum = 0;
            }
        }

        // SAVE GAME
        textY += gp.tileSize;
        g2.drawString("Save", textX, textY);
        if (commandNum == 4) {
            drawCarrot(textX-gp.tileSize/2, textY-gp.tileSize/2, gp.tileSize/2, gp.tileSize/2);

            if (gp.keyH.enterPressed) {
                subState = 4; //saveGame
                commandNum = 0;
            }
        }

        // END GAME
        textY += gp.tileSize;
        g2.drawString("End Game", textX, textY);
        if (commandNum == 5) {
            drawCarrot(textX-gp.tileSize/2, textY-gp.tileSize/2, gp.tileSize/2, gp.tileSize/2);

            if (gp.keyH.enterPressed) {
                subState = 3; // endGame
                commandNum = 0;
            }
        }

        // BACK
        textY += gp.tileSize;
        g2.drawString("Back", textX, textY);
        if (commandNum == 6) {
            drawCarrot(textX-gp.tileSize/2, textY-gp.tileSize/2, gp.tileSize/2, gp.tileSize/2);

            if (gp.keyH.enterPressed) {
                gp.gameState = gp.playState;
                commandNum = 0;
            }
        }

        // FULL SCREEN CHECK BOX
        textX = frameX + (int) (gp.tileSize * 4.5);
        textY = frameY + gp.tileSize * 2 + 24;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, 24, 24);

        if (gp.fullScreenOn) {
            g2.fillRect(textX, textY, 24, 24);
        }

        // MUSIC VOLUME BOX
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, 24);
        int volumeWidth = 24 * gp.music.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        // SOUND EFFECT VOLUME BOX
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, 24);
        volumeWidth = 24 * gp.se.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        gp.config.saveConfig();
    }

    public void options_fullScreenNotification(int frameX, int frameY) {
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize * 3;

        currentDialogue = "The change will take \neffect after restarting \nthe game.";

        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        // BACK OPTION
        textY = frameY + gp.tileSize * 9;
        g2.drawString("Back", textX, textY);

        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);

            if (gp.keyH.enterPressed) {
                subState = 0;
            }
        }
    }

    public void options_control(int frameX, int frameY) {
        int textX;
        int textY;

        // TITLE
        String text = "Control";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        textX = frameX + gp.tileSize;
        textY += gp.tileSize;

        g2.drawString("Move", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Confirm/Attack", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Shoot/Cast", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Character Screen", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Pause", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Options", textX, textY);
        textY += gp.tileSize;

        textX = frameX + gp.tileSize * 6;
        textY = frameY + gp.tileSize * 2;

        g2.drawString("WASD", textX, textY);
        textY += gp.tileSize;
        g2.drawString("ENTER", textX, textY);
        textY += gp.tileSize;
        g2.drawString("F", textX, textY);
        textY += gp.tileSize;
        g2.drawString("C", textX, textY);
        textY += gp.tileSize;
        g2.drawString("P", textX, textY);
        textY += gp.tileSize;
        g2.drawString("ESC", textX, textY);
        textY += gp.tileSize;

        // BACK
        textX = frameX + gp.tileSize;
        textY = frameY + gp.tileSize * 9;
        g2.drawString("Back", textX, textY);

        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);

            if (gp.keyH.enterPressed) {
                subState = 0;
                commandNum = 3;
            }
        }
    }

    public void options_endGameConfirmation(int frameX, int frameY) {

        int lineHeight = gp.tileSize;
        int textX = frameX + gp.tileSize;
        int textY = frameY + lineHeight;

        currentDialogue = "Quit the game and return\nto the title screen?";

        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        // Check for navigation input to switch between "Yes" and "No"
        if (gp.keyH.upPressed) {
            commandNum = (commandNum == 0) ? 1 : 0;
            gp.keyH.upPressed = false;  // Reset key state
        } else if (gp.keyH.downPressed) {
            commandNum = (commandNum == 1) ? 0 : 1;
            gp.keyH.downPressed = false;  // Reset key state
        }

        // YES option
        String text = "Yes";
        textX = getXforCenteredText(text);
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if (commandNum == 0) {
            drawCarrot(textX-gp.tileSize/2, textY-gp.tileSize/2, gp.tileSize/2, gp.tileSize/2);
            if (gp.keyH.enterPressed) {
                commandNum = 0;
                subState = 5;
                gp.stopMusic();
            }
        }

        // NO option
        text = "No";
        textX = getXforCenteredText(text);
        textY += 40;
        g2.drawString(text, textX, textY);

        if (commandNum == 1) {
            drawCarrot(textX-gp.tileSize/2, textY-gp.tileSize/2, gp.tileSize/2, gp.tileSize/2);
            if (gp.keyH.enterPressed) {
                subState = 0;
                commandNum = 0;
            }
        }
    }

    public void options_SaveOnQuit(int frameX, int frameY){
        int lineHeight = gp.tileSize;
        int textX = frameX + gp.tileSize;
        int textY = frameY + lineHeight;

        currentDialogue = "Would you like to save\nbefore quitting?";

        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        // Check for navigation input to switch between "Yes" and "No"
        if (gp.keyH.upPressed) {
            commandNum = (commandNum == 0) ? 1 : 0;
            gp.keyH.upPressed = false;  // Reset key state
        } else if (gp.keyH.downPressed) {
            commandNum = (commandNum == 1) ? 0 : 1;
            gp.keyH.downPressed = false;  // Reset key state
        }

        // YES option
        String text = "Yes";
        textX = getXforCenteredText(text);
        textY += lineHeight;
        g2.drawString(text, textX, textY);
        if (commandNum == 0) {
            drawCarrot(textX-gp.tileSize/2, textY-gp.tileSize/2, gp.tileSize/2, gp.tileSize/2);
            if (gp.keyH.enterPressed) {
                gp.saveLoad.save();
                gp.stopMusic();
                commandNum = 0;
                subState = 0;
                gp.gameState = gp.titleState;
            }
        }

        // NO option
        text = "No";
        textX = getXforCenteredText(text);
        textY += 40;
        g2.drawString(text, textX, textY);
        if (commandNum == 1) {
            drawCarrot(textX-gp.tileSize/2, textY-gp.tileSize/2, gp.tileSize/2, gp.tileSize/2);

            if (gp.keyH.enterPressed) {
                gp.stopMusic();
                commandNum = 0;
                subState = 0;
                gp.gameState = gp.titleState;
            }
        }
    }

    public void drawTransition() {
        counter++;
        g2.setColor(new Color(0, 0, 0, counter * 5));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        if (counter == 50) {
            counter = 0;

            gp.gameState = gp.playState;
            gp.currentMap = gp.eHandler.tempMap;

            gp.player.worldX = gp.tileSize * gp.eHandler.tempCol;
            gp.player.worldY = gp.tileSize * gp.eHandler.tempRow;

            gp.eHandler.previousEventX = gp.player.worldX;
            gp.eHandler.previousEventY = gp.player.worldY;
        }
    }

    public void drawTradeScreen() {
        switch (subState) {
            case 0:
                trade_select();
                break;
            case 1:
                trade_buy();
                break;
            case 2:
                trade_sell();
                break;
        }

        gp.keyH.enterPressed = false;
    }

    public void trade_select() {
        npc.dialogueSet = 0;
        drawDialogueScreen();

        // DRAW WINDOW
        int x = gp.tileSize * 15;
        int y = gp.tileSize * 4;
        int width = gp.tileSize * 3;
        int height = (int) (gp.tileSize * 3.5);

        drawSubWindow(x, y, width, height);

        // DRAW TEXTS
        x += gp.tileSize;
        y += gp.tileSize;
        g2.drawString("Buy", x, y);

        if (commandNum == 0) {
            g2.drawString(">", x - 24, y);

            if (gp.keyH.enterPressed) {
                subState = 1;
            }
        }

        y += gp.tileSize;

        g2.drawString("Sell", x, y);

        if (commandNum == 1) {
            g2.drawString(">", x - 24, y);

            if (gp.keyH.enterPressed) {
                subState = 2;
            }
        }

        y += gp.tileSize;

        g2.drawString("Leave", x, y);

        if (commandNum == 2) {
            g2.drawString(">", x - 24, y);

            if (gp.keyH.enterPressed) {
                commandNum = 0;
                npc.startDialogue(npc, 1);
            }
        }
    }

    public void trade_buy() {
        // DRAW PLAYER INVENTORY
        drawInventory(gp.player, false);

        // DRAW NPC INVENTORY
        drawInventory(npc, true);

        // DRAW HINT WINDOW
        int x = gp.tileSize * 2;
        int y = gp.tileSize * 9;
        int width = gp.tileSize * 6;
        int height = gp.tileSize * 2;

        drawSubWindow(x, y, width, height);
        g2.drawString("[ESC] Back", x + 24, y + 60);

        // DRAW PLAYER COIN WINDOW
        x = gp.tileSize * 12;
        y = gp.tileSize * 9;
        width = gp.tileSize * 6;
        height = gp.tileSize * 2;

        drawSubWindow(x, y, width, height);
        g2.drawString("Your Coin: " + gp.player.coin, x + 24, y + 60);

        // DRAW PRICE WINDOW
        int itemIndex = getItemIndexOnSlot(npcSlotCol, npcSlotRow);
        if (itemIndex < npc.inventory.size()) {
            x = (int) (gp.tileSize * 5.5);
            y = (int) (gp.tileSize * 5.5);
            width = (int) (gp.tileSize * 2.5);
            height = gp.tileSize;

            drawSubWindow(x, y, width, height);
            g2.drawImage(coin, x + 10, y + 8, 32, 32, null);

            int price = npc.inventory.get(itemIndex).price;
            String text = "" + price;
            x = getXforAlignToRightText(text, gp.tileSize * 8 - 20);
            g2.drawString(text, x, y + 34);

            // BUY AN ITEM
            if (gp.keyH.enterPressed) {
                if (npc.inventory.get(itemIndex).price > gp.player.coin) {
                    subState = 0;
                    npc.startDialogue(npc, 2);
                } else {
                    if(gp.player.canObtainItem(npc.inventory.get(itemIndex))){
                        gp.player.coin -= npc.inventory.get(itemIndex).price;
                    } else {
                        subState = 0;
                        npc.startDialogue(npc, 3);
                    }
                }
            }
        }
    }

    public void trade_sell() {
        // DRAW PLAYER INVENTORY
        drawInventory(gp.player, true);

        int x;
        int y;
        int width;
        int height;

        // DRAW HINT WINDOW
        x = gp.tileSize * 2;
        y = gp.tileSize * 9;
        width = gp.tileSize * 6;
        height = gp.tileSize * 2;

        drawSubWindow(x, y, width, height);
        g2.drawString("[ESC] Back", x + 24, y + 60);

        // DRAW PLAYER COIN WINDOW
        x = gp.tileSize * 12;
        y = gp.tileSize * 9;
        width = gp.tileSize * 6;
        height = gp.tileSize * 2;

        drawSubWindow(x, y, width, height);
        g2.drawString("Your Coin: " + gp.player.coin, x + 24, y + 60);

        // DRAW PRICE WINDOW
        int itemIndex = getItemIndexOnSlot(playerSlotCol, playerSlotRow);
        if (itemIndex < gp.player.inventory.size()) {
            x = (int) (gp.tileSize * 15.5);
            y = (int) (gp.tileSize * 5.5);
            width = (int) (gp.tileSize * 2.5);
            height = gp.tileSize;

            drawSubWindow(x, y, width, height);
            g2.drawImage(coin, x + 10, y + 8, 32, 32, null);

            int price = gp.player.inventory.get(itemIndex).price / 2;
            String text = "" + price;
            x = getXforAlignToRightText(text, gp.tileSize * 18 - 20);
            g2.drawString(text, x, y + 34);

            // SELL AN ITEM
            if (gp.keyH.enterPressed) {
                if (gp.player.inventory.get(itemIndex) == gp.player.currentWeapon
                        || gp.player.inventory.get(itemIndex) == gp.player.currentShield) {
                    commandNum = 0;
                    subState = 0;
                    npc.startDialogue(npc, 4);
                } else {
                    if(gp.player.inventory.get(itemIndex).amount > 1){
                        gp.player.inventory.get(itemIndex).amount--;
                    } else {
                        gp.player.inventory.remove(itemIndex);
                    }
                    gp.player.coin += price;
                }
            }
        }
    }

    public int getItemIndexOnSlot(int slotCol, int slotRow) {
        int itemIndex = slotCol + (slotRow * 5);

        return itemIndex;
    }
    public void drawSleepScreen() {
        counter++;
        if (counter < 120) {
            gp.eManager.lighting.filterAlpha += 0.01f;

            if (gp.eManager.lighting.filterAlpha > 1f) {
                gp.eManager.lighting.filterAlpha = 1f;
            }
        }

        if (counter >= 120) {
            gp.eManager.lighting.filterAlpha -= 0.01f;

            if (gp.eManager.lighting.filterAlpha <= 0f) {
                gp.eManager.lighting.filterAlpha = 0f;
                counter = 0;
                gp.eManager.lighting.dayState = gp.eManager.lighting.day;
                gp.eManager.lighting.dayCounter = 0;
                gp.gameState = gp.playState;
                gp.player.getImage();
            }
        }
    }
    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0, 0, 0, 210);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    public int getXforCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;

        return x;
    }

    public int getXforAlignToRightText(String text, int tailX) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length;

        return x;
    }
}
