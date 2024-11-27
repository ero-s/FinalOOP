package main;

import entity.Entity;
import entity.NPC_Andres;
import entity.PlayerDummy;
import object.OBJ_Door_Iron;

import java.awt.*;

public class CutsceneManager {
    GamePanel gp;
    Graphics2D g2;

    public int sceneNum;
    public int scenePhase;

    // scene number
    public final int NA = 0;
    public final int skeletonLord = 1;
    public final int andres = 2;

    public CutsceneManager(GamePanel gp) {
        this.gp = gp;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        switch(sceneNum) {
            case skeletonLord: scene_skeletonLord(); break;
            case andres: scene_Andres(); break;
        }
    }

    public void scene_skeletonLord() {
        if (scenePhase == 0) {
            gp.bossBattleOn = true;

            //shut doors
            for (int i = 0; i < gp.obj[1].length; i++) {

                if (gp.obj[gp.currentMap][i] == null) {
                    gp.obj[gp.currentMap][i] = new OBJ_Door_Iron(gp);
                    gp.obj[gp.currentMap][i].worldX = gp.tileSize*25;
                    gp.obj[gp.currentMap][i].worldY = gp.tileSize*28;
                    gp.obj[gp.currentMap][i].temp = true;
                    gp.playSE(21);
                    break;
                }
            }
            scenePhase++;
        }
    }

    public void scene_Andres(){
        if (scenePhase == 0){
            gp.player.drawing = false;
            scenePhase++;
        }
        if (scenePhase == 1){
            // Search a vacant slot for the dummy
            for (int i = 0; i < gp.npc[1].length; i++) {
                if (gp.npc[gp.currentMap][i] == null) {
                    gp.npc[gp.currentMap][i] = new PlayerDummy(gp);
                    gp.npc[gp.currentMap][i].worldX = gp.player.worldX;
                    gp.npc[gp.currentMap][i].worldY = gp.player.worldY;
                    gp.npc[gp.currentMap][i].direction = gp.player.direction;
                    break;
                }
            }
            gp.player.worldY-=2; // can be x or y, depending if you want camera to go vertical or horizontal
            if(gp.player.worldY < gp.tileSize * 14){
                scenePhase++;
            }
        }
        if(scenePhase == 2){
            // Search the boss
            for (int i = 0; i < gp.npc[gp.currentMap].length; i++) {
                if (gp.npc[gp.currentMap][i] != null && gp.npc[gp.currentMap][i].name == NPC_Andres.npcName) {
                    gp.npc[gp.currentMap][i].sleep = false;
                    gp.ui.npc = gp.npc[gp.currentMap][i];
                    scenePhase++;
                    break;
                }
            }
        }
        if(scenePhase == 3){
            gp.ui.drawDialogueScreen();
        }

    }
}
