package main;

import entity.*;
import monster.*;
import object.*;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) { this.gp = gp; }

    public void setObject() {
        int mapNum = 0;
        int i = 0;
        gp.obj[mapNum][i] = new OBJ_Key(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 27;
        gp.obj[mapNum][i].worldY = gp.tileSize * 41;
        i++;

        gp.obj[mapNum][i] = new OBJ_FallDoor(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 13;
        gp.obj[mapNum][i].worldY = gp.tileSize * 10;
        i++;
        gp.obj[mapNum][i] = new OBJ_FallDoor(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 14;
        gp.obj[mapNum][i].worldY = gp.tileSize * 10;
        i++;
        gp.obj[mapNum][i] = new OBJ_FallDoor(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 15;
        gp.obj[mapNum][i].worldY = gp.tileSize * 10;
        i++;

        gp.obj[mapNum][i] = new OBJ_Door(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 41;
        gp.obj[mapNum][i].worldY = gp.tileSize * 20;
        i++;

        gp.obj[mapNum][i] = new OBJ_Door(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 42;
        gp.obj[mapNum][i].worldY = gp.tileSize * 20;
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

        gp.npc[mapNum][i] = new NPC_Portal(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 12; // -2 because imageWidth is 2
        gp.npc[mapNum][i].worldY = gp.tileSize * 7;
        i++;

        gp.npc[mapNum][i] = new NPC_Merchant(gp);
        gp.npc[mapNum][i].sleep = true;
        gp.npc[mapNum][i].worldX = gp.tileSize * 37;
        gp.npc[mapNum][i].worldY = gp.tileSize * 23;
        i++;

        if(gp.player.currentDialogueSet >=3){
            gp.obj[4][i] = new NPC_Pugtato(gp);
            gp.obj[4][i].sleep = true;
            gp.obj[4][i].worldX = gp.tileSize * 22;
            gp.obj[4][i].worldY = gp.tileSize * 33;
            i++;
        }

//        gp.obj[mapNum][i] = new NPC_Portal(gp);
//        gp.obj[mapNum][i].worldX = gp.tileSize * 14;
//        gp.obj[mapNum][i].worldY = gp.tileSize * 7;
//        i++;

        mapNum = 1;
        i = 0;

        gp.npc[mapNum][i] = new NPC_Merchant(gp);
        gp.npc[mapNum][i].sleep = true;
        gp.npc[mapNum][i].worldX = gp.tileSize * 38;
        gp.npc[mapNum][i].worldY = gp.tileSize * 14;
        i++;

        gp.npc[mapNum][i] = new NPC_CoffeeBean(gp);
        gp.npc[mapNum][i].sleep = true;
        gp.npc[mapNum][i].worldX = gp.tileSize * 28;
        gp.npc[mapNum][i].worldY = gp.tileSize * 14;
        i++;

        if(gp.player.currentDialogueSet >= 3){
            gp.npc[4][0] = new NPC_Pugtato(gp);
            gp.npc[4][0].sleep = true;
            gp.npc[4][0].worldX = gp.tileSize * 22;
            gp.npc[4][0].worldY = gp.tileSize * 33;
            i++;
        }

        mapNum = 2;
        i = 0;

        gp.npc[mapNum][i] = new NPC_OldMan(gp);
        gp.npc[mapNum][i].sleep = true;
        gp.npc[mapNum][i].worldX = gp.tileSize * 9;
        gp.npc[mapNum][i].worldY = gp.tileSize * 38;
        i++;

        gp.npc[mapNum][i] = new NPC_Turnip(gp);
        gp.npc[mapNum][i].sleep = true;
        gp.npc[mapNum][i].worldX = gp.tileSize * 12;
        gp.npc[mapNum][i].worldY = gp.tileSize * 38;
        i++;
    }

    public void setMonster() {
        int mapNum = 0;

        int i = 0;

        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 26;
        gp.monster[mapNum][i].worldY = gp.tileSize * 23;
        i++;

        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 18;
        gp.monster[mapNum][i].worldY = gp.tileSize * 21;
        i++;

        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 16;
        gp.monster[mapNum][i].worldY = gp.tileSize * 33;
        i++;

        gp.monster[mapNum][i] = new MON_Orc(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 22;
        gp.monster[mapNum][i].worldY = gp.tileSize * 41;
        i++;

        gp.monster[mapNum][i] = new MON_Orc(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 13;
        gp.monster[mapNum][i].worldY = gp.tileSize * 38;
        i++;


        mapNum = 1;
        i = 0;
        gp.monster[mapNum][i] = new MON_RedSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 33;
        gp.monster[mapNum][i].worldY = gp.tileSize * 15;
        i++;

        gp.monster[mapNum][i] = new MON_RedSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 23;
        gp.monster[mapNum][i].worldY = gp.tileSize * 17;
        i++;

        gp.monster[mapNum][i] = new MON_RedSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 37;
        gp.monster[mapNum][i].worldY = gp.tileSize * 27;
        i++;

        gp.monster[mapNum][i] = new MON_Bat(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 26;
        gp.monster[mapNum][i].worldY = gp.tileSize * 23;
        i++;

        gp.monster[mapNum][i] = new MON_Bat(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 23;
        gp.monster[mapNum][i].worldY = gp.tileSize * 27;
        i++;


        //WINTER
        mapNum = 2;
        i = 0;

        gp.monster[mapNum][i] = new MON_Snowman(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 43;
        gp.monster[mapNum][i].worldY = gp.tileSize * 9;
        i++;

        gp.monster[mapNum][i] = new MON_Snowman(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 39;
        gp.monster[mapNum][i].worldY = gp.tileSize * 13;
        i++;

        gp.monster[mapNum][i] = new MON_Snowman(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 40;
        gp.monster[mapNum][i].worldY = gp.tileSize * 16;
        i++;

        gp.monster[mapNum][i] = new MON_Snowman(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 37;
        gp.monster[mapNum][i].worldY = gp.tileSize * 19;
        i++;

        gp.monster[mapNum][i] = new MON_Snowman(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 34;
        gp.monster[mapNum][i].worldY = gp.tileSize * 20;
        i++;

        gp.monster[mapNum][i] = new MON_BlueSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 23;
        gp.monster[mapNum][i].worldY = gp.tileSize * 20;
        i++;

        gp.monster[mapNum][i] = new MON_BlueSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 23;
        gp.monster[mapNum][i].worldY = gp.tileSize * 16;
        i++;

        gp.monster[mapNum][i] = new MON_BlueSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 22;
        gp.monster[mapNum][i].worldY = gp.tileSize * 11;
        i++;

        gp.monster[mapNum][i] = new MON_BlueSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 15;
        gp.monster[mapNum][i].worldY = gp.tileSize * 23;
        i++;

        gp.monster[mapNum][i] = new MON_BlueSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 11;
        gp.monster[mapNum][i].worldY = gp.tileSize * 28;
        i++;

        gp.monster[mapNum][i] = new MON_BlueSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 8;
        gp.monster[mapNum][i].worldY = gp.tileSize * 9;
        i++;

        gp.monster[mapNum][i] = new MON_BlueSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 23;
        gp.monster[mapNum][i].worldY = gp.tileSize * 6;
        i++;

        gp.monster[mapNum][i] = new MON_BlueSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 26;
        gp.monster[mapNum][i].worldY = gp.tileSize * 14;
        i++;

        //SPRING
        mapNum = 3;
        i = 0;

        gp.monster[mapNum][i] = new MON_CursedOnion(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 18;
        gp.monster[mapNum][i].worldY = gp.tileSize * 32;
        i++;

        gp.monster[mapNum][i] = new MON_ZombieBroccoli(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 26;
        gp.monster[mapNum][i].worldY = gp.tileSize * 14;
        i++;

        //SUMMER DUNGEON
        mapNum = 4;
        i = 0;
        gp.monster[mapNum][i] = new MON_PickleRick(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 22;
        gp.monster[mapNum][i].worldY = gp.tileSize * 11;
        i++;

        gp.monster[mapNum][i] = new MON_Sigma(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 22;
        gp.monster[mapNum][i].worldY = gp.tileSize * 33;
        i++;

        gp.monster[mapNum][i] = new MON_Mogger(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 22;
        gp.monster[mapNum][i].worldY = gp.tileSize * 22;
        i++;

        gp.monster[mapNum][i] = new MON_Mewer(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 22;
        gp.monster[mapNum][i].worldY = gp.tileSize * 23;
        i++;


        //FALL DUNGEON
        mapNum = 5;
        i = 0;
        gp.monster[mapNum][i] = new MON_JackOLantern(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 25;
        gp.monster[mapNum][i].worldY = gp.tileSize * 16;
        i++;


        //WINTER DUNGEON
        mapNum = 6;
        i = 0;
        gp.monster[mapNum][i] = new MON_Jack(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 20;
        gp.monster[mapNum][i].worldY = gp.tileSize * 20;
        i++;

        gp.monster[mapNum][i] = new MON_Jill(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 28;
        gp.monster[mapNum][i].worldY = gp.tileSize * 20;
        i++;


    }

    public void setInteractiveTile() {
        int mapNum = 0;

        int i = 0;

        mapNum = 2;
        i = 0;
    }
}
