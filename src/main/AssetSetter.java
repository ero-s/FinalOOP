package main;

import entity.*;
import monster.*;
import object.*;
import tile_interactive.IT_DryTree;
import tile_interactive.IT_MetalPlate;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) { this.gp = gp; }

    public void setObject() {
        int mapNum = 0;
        int i = 0;

        gp.obj[mapNum][i] = new OBJ_BlueHeart(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 44;
        gp.obj[mapNum][i].worldY = gp.tileSize * 34;
        i++;


    }

    public void setNPC() {
        int mapNum = 0;
        int i = 0;

        gp.npc[mapNum][i] = new NPC_Pugtato(gp);
        gp.npc[mapNum][i].sleep = true;
        gp.npc[mapNum][i].worldX = gp.tileSize * 42;
        gp.npc[mapNum][i].worldY = gp.tileSize * 33;
        i++;

        mapNum = 1;
        i = 0;

        gp.npc[mapNum][i] = new NPC_CoffeeBean(gp);
        gp.npc[mapNum][i].sleep = true;
        gp.npc[mapNum][i].worldX = gp.tileSize * 42;
        gp.npc[mapNum][i].worldY = gp.tileSize * 33;
        i++;



    }

    public void setMonster() {
        int mapNum = 0;

        int i = 0;


        mapNum = 4;
        i = 0;
        gp.monster[mapNum][i] = new MON_PickleRick(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 22;
        gp.monster[mapNum][i].worldY = gp.tileSize * 11;
        i++;

        mapNum = 5;
        i = 0;
        gp.monster[mapNum][i] = new MON_JackOLantern(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 24;
        gp.monster[mapNum][i].worldY = gp.tileSize * 24;
        i++;
    }

    public void setInteractiveTile() {
        int mapNum = 0;

        int i = 0;

        mapNum = 2;
        i = 0;
    }
}
