package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GamePanel gp;

    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, shotKeyPressed, spacePressed, skill1Pressed, escapePressed;

    // DEBUG
    public boolean showDebugTest = false;
    public boolean godModeOn = true;
    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // TITLE STATE
        if (gp.gameState == gp.titleState) { gp.stopMusic(); titleState(code); }

        // PLAY STATE
        else if (gp.gameState == gp.playState) { playState(code); }

        // PAUSE STATE
        else if (gp.gameState == gp.pauseState) { pauseState(code); }

        // DIALOGUE STATE
        else if (gp.gameState == gp.dialogueState || gp.gameState == gp.cutsceneState) { dialogueState(code); }

        // CHARACTER STATE
        else if (gp.gameState == gp.characterState) { characterState(code); }

        // OPTION STATE
        else if (gp.gameState == gp.optionState) { optionState(code); }

        // GAME OVER STATE
        else if (gp.gameState == gp.gameOverState) { gameOverState(code); }

        // TRADE STATE
        else if (gp.gameState == gp.tradeState) { tradeState(code); }
    }

    public void titleState(int code){
        //gp.stopMusic();
        if(gp.ui.titleScreenState == 0){
            if(code == KeyEvent.VK_W){
                gp.ui.commandNum--;
                if(gp.ui.commandNum < 0){
                    gp.ui.commandNum = 2;
                }
            }
            if(code == KeyEvent.VK_S){
                gp.ui.commandNum++;
                if(gp.ui.commandNum > 2){
                    gp.ui.commandNum = 0;
                }
            }
            if(code == KeyEvent.VK_ENTER){
                if(gp.ui.commandNum == 0){ // new game
                    gp.gameState = gp.cutsceneState;
                    gp.csManager.sceneNum = gp.csManager.OPENING;
                    gp.player.setDefaultValues();

                }

                if(gp.ui.commandNum == 1){ // load
                    gp.saveLoad.load();
                    gp.gameState = gp.playState;

                }
                if(gp.ui.commandNum == 2){ // exit
                    System.exit(0);
                }
            }
        }
        else if(gp.ui.titleScreenState == 1){// newgame with saved progress
            if(code == KeyEvent.VK_W){
                gp.ui.commandNum--;
                if(gp.ui.commandNum < 0){
                    gp.ui.commandNum = 1;
                }
            }
            if(code == KeyEvent.VK_S){
                gp.ui.commandNum++;
                if(gp.ui.commandNum > 1){
                    gp.ui.commandNum = 0;
                }
            }
            if(code == KeyEvent.VK_ENTER){
                if(gp.ui.commandNum == 0){ // back
                    gp.ui.titleScreenState = 0;
                }
                if(gp.ui.commandNum == 1){ // new game
                    gp.player.setDefaultValues();
                    gp.saveLoad.save();
                    // gp.gameState = gp.cutsceneState
                    gp.gameState = gp.playState;
                    gp.ui.titleScreenState = 0;
                }
            }
        }
        else if(gp.ui.titleScreenState == 2){// no saved progresses
            if(code == KeyEvent.VK_W){
                gp.ui.commandNum--;
                if(gp.ui.commandNum < 0){
                    gp.ui.commandNum = 1;
                }
            }
            if(code == KeyEvent.VK_S){
                gp.ui.commandNum++;
                if(gp.ui.commandNum > 0){
                    gp.ui.commandNum = 0;
                }
            }
            if(code == KeyEvent.VK_ENTER){
                if(gp.ui.commandNum == 0){ // back
                    gp.ui.titleScreenState = 0;
                }
            }
        }
    }

    public void playState(int code) {
        if (code == KeyEvent.VK_W) {
            // W button
            upPressed = true;
        }

        if (code == KeyEvent.VK_S) {
            // S button
            downPressed = true;
        }

        if (code == KeyEvent.VK_A) {
            // A button
            leftPressed = true;
        }

        if (code == KeyEvent.VK_D) {
            // D button
            rightPressed = true;
        }

        if (code == KeyEvent.VK_P) {
            // P button
            gp.gameState = gp.pauseState;
        }
        if (code == KeyEvent.VK_C) {
            gp.gameState = gp.characterState;
        }
        if (code == KeyEvent.VK_ENTER) {
            // Enter button
            enterPressed = true;
        }

        // Skills
        if (code == KeyEvent.VK_SPACE) {
            // space for guard button
            spacePressed = true;
        }

        if (code == KeyEvent.VK_F) {
            // F for shot button
            shotKeyPressed = true;
        }

        if(code == KeyEvent.VK_1){
            skill1Pressed = true;
        }

        //Options
        if (code == KeyEvent.VK_ESCAPE) {
            // ESC for settings button
            gp.gameState = gp.optionState;
        }



        // DEBUG

        if (code == KeyEvent.VK_R) {
            switch (gp.currentMap) {
                case 0:
                    gp.tileM.loadMap("/res/maps/worldV3.txt", 0);
                    break;
                case 1:
                    gp.tileM.loadMap("/res/maps/interior01.txt", 1);
                    break;
            }
        }

        if (code == KeyEvent.VK_G) {

            if(!godModeOn){
                godModeOn = true;
            } else if(godModeOn){
                godModeOn = false;
            }
        }
    }

    public void pauseState(int code) {
        if (code == KeyEvent.VK_P) {
            // P button
            gp.gameState = gp.playState;
        }
    }

    public void dialogueState(int code) {
        if (code == KeyEvent.VK_ENTER) {
            // ENTER button
            enterPressed = true;
        }
        if (code == KeyEvent.VK_ESCAPE) {
            // ENTER button
            escapePressed= true;
        }
    }

    public void characterState(int code) {
        if (code == KeyEvent.VK_C) {
            // C button
            gp.gameState = gp.playState;
        }

        if (code == KeyEvent.VK_ENTER) {
            gp.player.selectItem();
        }

        playerInventory(code);
    }

    public void optionState(int code) {
        if (code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.playState;
        }

        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }

        int maxCommandNum = 0;

        switch (gp.ui.subState) {
            case 0:
                maxCommandNum = 6;
                break;
            case 3:
                maxCommandNum = 2;
                break;
            case 4:
                maxCommandNum = 2;
                break;
            case 5:
                maxCommandNum = 1;
                break;
        }

        if (code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            gp.playSE(9);

            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = maxCommandNum;
            }
        }

        if (code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            gp.playSE(9);

            if (gp.ui.commandNum > maxCommandNum) {
                gp.ui.commandNum = 0;
            }
        }

        if (code == KeyEvent.VK_A) {
            if (gp.ui.subState == 0) {
                if (gp.ui.commandNum == 1 && gp.music.volumeScale > 0) {
                    gp.music.volumeScale--;
                    gp.music.checkVolume();
                    gp.playSE(9);
                }

                if (gp.ui.commandNum == 2 && gp.se.volumeScale > 0) {
                    gp.se.volumeScale--;
                    gp.playSE(9);
                }
            }
        }

        if (code == KeyEvent.VK_D) {
            if (gp.ui.subState == 0) {
                if (gp.ui.commandNum == 1 && gp.music.volumeScale < 5) {
                    gp.music.volumeScale++;
                    gp.music.checkVolume();
                    gp.playSE(9);
                }

                if (gp.ui.commandNum == 2 && gp.se.volumeScale < 5) {
                    gp.se.volumeScale++;
                    gp.playSE(9);
                }
            }
        }
    }

    public void gameOverState(int code) {
        if (code == KeyEvent.VK_W) {
            gp.ui.commandNum--;

            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = 1;
            }

            gp.playSE(9);
        }

        if (code == KeyEvent.VK_S) {
            gp.ui.commandNum++;

            if (gp.ui.commandNum > 1) {
                gp.ui.commandNum = 0;
            }

            gp.playSE(9);
        }

        if (code == KeyEvent.VK_ENTER) {
            if (gp.ui.commandNum == 0) {
                gp.resetGame(false);
                gp.gameState = gp.playState;
            } else if (gp.ui.commandNum == 1) {
                gp.stopMusic();
                gp.gameState = gp.titleState;
                gp.resetGame(true);
            }
        }
    }

    public void tradeState(int code) {
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }

        if (gp.ui.subState == 0) {
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNum--;

                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 2;
                }

                gp.playSE(9);
            }

            if (code == KeyEvent.VK_S) {
                gp.ui.commandNum++;

                if (gp.ui.commandNum > 2) {
                    gp.ui.commandNum = 0;
                }

                gp.playSE(9);
            }
        }

        if (gp.ui.subState == 1) {
            npcInventory(code);

            if (code == KeyEvent.VK_ESCAPE) {
                gp.ui.subState = 0;
            }
        }

        if (gp.ui.subState == 2) {
            playerInventory(code);

            if (code == KeyEvent.VK_ESCAPE) {
                gp.ui.subState = 0;
            }
        }
    }

    public void playerInventory(int code) {
        if (code == KeyEvent.VK_W) {
            if (gp.ui.playerSlotRow != 0) {
                gp.ui.playerSlotRow--;
                gp.playSE(9);
            }
        }

        if (code == KeyEvent.VK_A) {
            if (gp.ui.playerSlotCol != 0) {
                gp.ui.playerSlotCol--;
                gp.playSE(9);
            }
        }

        if (code == KeyEvent.VK_S) {
            if (gp.ui.playerSlotRow != 3) {
                gp.ui.playerSlotRow++;
                gp.playSE(9);
            }
        }

        if (code == KeyEvent.VK_D) {
            if (gp.ui.playerSlotCol != 4) {
                gp.ui.playerSlotCol++;
                gp.playSE(9);
            }
        }
    }

    public void npcInventory(int code) {
        if (code == KeyEvent.VK_W) {
            if (gp.ui.npcSlotRow != 0) {
                gp.ui.npcSlotRow--;
                gp.playSE(9);
            }
        }

        if (code == KeyEvent.VK_A) {
            if (gp.ui.npcSlotCol != 0) {
                gp.ui.npcSlotCol--;
                gp.playSE(9);
            }
        }

        if (code == KeyEvent.VK_S) {
            if (gp.ui.npcSlotRow != 3) {
                gp.ui.npcSlotRow++;
                gp.playSE(9);
            }
        }

        if (code == KeyEvent.VK_D) {
            if (gp.ui.npcSlotCol != 4) {
                gp.ui.npcSlotCol++;
                gp.playSE(9);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            // W button
            upPressed = false;
        }

        if (code == KeyEvent.VK_S) {
            // S button
            downPressed = false;
        }

        if (code == KeyEvent.VK_A) {
            // A button
            leftPressed = false;
        }

        if (code == KeyEvent.VK_D) {
            // D button
            rightPressed = false;
        }

        if (code == KeyEvent.VK_ENTER) {
            // enter for interact button
            enterPressed = false;
        }

        // Skills
        if (code == KeyEvent.VK_F) {
            // K for shot button
            shotKeyPressed = false;
        }

        if (code == KeyEvent.VK_SPACE) {
            // space for guard button
            spacePressed = false;
        }

        if(code == KeyEvent.VK_1){
            skill1Pressed = false;
        }
        if(code == KeyEvent.VK_ESCAPE){
            escapePressed = false;
        }

    }
}
