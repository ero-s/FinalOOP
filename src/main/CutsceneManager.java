package main;

import object.OBJ_Door_Iron;
import object.OBJ_Shield_Blue;

import java.awt.*;

public class CutsceneManager {
    GamePanel gp;
    Graphics2D g2;

    public int sceneNum;
    public int scenePhase;
    int counter = 0;
    float alpha = 0f;
    int y;
    String endCredit;

    // scene number
    public final int NA = 0;
    public final int skeletonLord = 1;
    public final int ending = 2;

    public CutsceneManager(GamePanel gp) {
        this.gp = gp;

        endCredit = "Program/Music/Art\n"
                + "RyiSnow"
                + "\n\n\n\n\n\n\n\n\n\n\n\n\n"
                + "Special Thanks\n"
                + "Someone\n"
                + "Someone\n"
                + "Someone\n"
                + "Someone\n\n\n\n\n"
                + "Thank you for playing!";
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        switch(sceneNum) {
            //case skeletonLord: scene_skeletonLord(); break;
            case ending: scene_ending(); break;
        }
    }

//    public void scene_skeletonLord() {
//        if (scenePhase == 0) {
//            gp.bossBattleOn = true;
//
//            //shut doors
//            for (int i = 0; i < gp.obj[1].length; i++) {
//
//                if (gp.obj[gp.currentMap][i] == null) {
//                    gp.obj[gp.currentMap][i] = new OBJ_Door_Iron(gp);
//                    gp.obj[gp.currentMap][i].worldX = gp.tileSize*25;
//                    gp.obj[gp.currentMap][i].worldY = gp.tileSize*28;
//                    gp.obj[gp.currentMap][i].temp = true;
//                    gp.playSE(21);
//                    break;
//                }
//            }
//
//            //Search a vacant slot for the dummy
//            for (int i = 0; i < gp.npc[1].length; i++) {
//
//                if (gp.npc[gp.currentMap][i] == null) {
//                    gp.npc[gp.currentMap][i] = new PlayerDummy(gp);
//                    gp.npc[gp.currentMap][i].worldX = gp.player.worldX;
//                    gp.npc[gp.currentMap][i].worldY = gp.player.worldY;
//                    gp.npc[gp.currentMap][i].direction = gp.player.direction;
//                    break;
//                }
//            }
//
//            gp.player.drawing = false;
//
//            scenePhase++;
//        }
//
//        if(scenePhase == 1){
//
//        }
//
//        if(scenePhase == 2){
//
//            //Search the boss
//            for(int i = 0; i < gp.monster[1].length; i++){
//
//                if(gp.monster[gp.currentMap][i] != null &&
//                        gp.monster[gp.currentMap][i].name == MON_SkeletonLord.monName){
//
//                    gp.monster[gp.currentMap][i].sleep = false;
//                    gp.ui.npc = gp.monster[gp.currentMap][i];
//                    scenePhase++;
//                    break;
//                }
//            }
//        }
//
//        if(scenePhase == 3){
//
//            //The boss speaks
//            gp.ui.drawDialogueScreen();
//        }
//
//        if(scenePhase == 4){
//
//            //Return to the player
//
//            //Search the dummy
//            for(int i = 0; i < gp.npc[1].length; i++){
//
//                if(gp.npc[gp.currentMap][i] != null && gp.npc[gp.currentMap][i].name.equals(PlayerDummy.npcName)){
//                    //Restore the player position
//                    gp.player.worldX = gp.npc[gp.currentMap][i].worldX;
//                    gp.player.worldY = gp.npc[gp.currentMap][i].worldY;
//                    //Delete the dummy
//                    gp.npc[gp.currentMap][i] = null;
//                    break;
//                }
//            }
//
//            //Start drawing the player
//            gp.player.drawing = true;
//
//            //Reset
//            sceneNum = NA;
//            scenePhase = 0;
//            gp.gameState = gp.playState;
//
//            //Change the music
//            gp.stopMusic();
//            gp.playMusic(22);
//        }
//    }
    public void scene_ending(){

        if(scenePhase == 0){

            gp.stopMusic();
            gp.ui.npc = new OBJ_Shield_Blue(gp);
            scenePhase++;
        }

        if(scenePhase == 1){
            //Display dialogues
            gp.ui.drawDialogueScreen();
        }

        if(scenePhase == 2){
            //PLay the fanfare
            gp.playSE(4);
            scenePhase++;
        }

        if(scenePhase == 3){

            //Wait until the sound effect ends
            if(counterReached(300) == true){
                scenePhase++;
            }
        }

        if(scenePhase == 4){

            //The screen gets darker
            alpha += 0.005f;
            if(alpha > 1f){
                alpha = 1f;
            }
            drawBlackBackground(alpha);

            if(alpha == 1f){
                alpha = 0;
                scenePhase++;
            }
        }

        if(scenePhase == 5){

            drawBlackBackground(1f);

            alpha += 0.005f;
            if(alpha > 1f){
                alpha = 1f;
            }

            String text = "After the fierce battle with the Skeleton Lord,\n"
                    + "the Blue Boy finally found the legendary treasure.\n"
                    + "But this is not the end of his journey.\n"
                    + "The Blue Boy's adventure has just begun.";
            drawString(alpha, 38f, 200, text, 70);

            if(counterReached(600) == true){
                gp.playMusic(0);
                scenePhase++;
            }
        }

        if(scenePhase == 6){

            drawBlackBackground(1f);

            drawString(1f, 120f, gp.screenHeight/2, "Blue Boy Adventure", 40);

            if(counterReached(480) == true){
                scenePhase++;
            }
        }

        if(scenePhase == 7){

            drawBlackBackground(1f);

            y = gp.screenHeight/2;
            drawString(1f, 38f, gp.screenHeight/2, endCredit, 40);

            if(counterReached(480) == true){
                scenePhase++;
            }
        }

        if(scenePhase == 8){

            drawBlackBackground(1f);

            //Scrolling the credit
            y--;
            drawString(1f, 38f, y, endCredit, 40);
        }
    }

    public boolean counterReached(int target){

        boolean counterReached = false;

        counter++;
        if(counter > target){
            counterReached = true;
            counter = 0;
        }

        return counterReached;
    }
    public void drawBlackBackground(float alpha){

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.setColor(Color.black);
        g2.fillRect(0,0,gp.screenWidth, gp.screenHeight);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
    public void drawString(float alpha, float fontSize, int y, String text, int lineHeight){

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(fontSize));

        for(String line: text.split("\n")){
            int x = gp.ui.getXforCenteredText(line);
            g2.drawString(line, x, y);
            y += lineHeight;
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}
