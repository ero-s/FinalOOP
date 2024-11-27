package main;

import entity.NPC_Andres;
import entity.NPC_BigRock;
import entity.NPC_Merchant;
import entity.NPC_OldMan;
import entity.NPC_Pugtato;
import monster.MON_Bat;
import monster.MON_GreenSlime;
import monster.MON_Orc;
import monster.MON_SkeletonLord;
import object.*;
import tile_interactive.IT_DryTree;
import tile_interactive.IT_MetalPlate;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) { this.gp = gp; }

    public void setObject() {
        int mapNum = 0;
        int i = 0;

        gp.obj[mapNum][i] = new OBJ_Axe(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 33;
        gp.obj[mapNum][i].worldY = gp.tileSize * 21;
        i++;

        gp.obj[mapNum][i] = new OBJ_Chest(gp);
        gp.obj[mapNum][i].setLoot(new OBJ_Potion_Red(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize * 24;
        gp.obj[mapNum][i].worldY = gp.tileSize * 21;
        i++;

        gp.obj[mapNum][i] = new OBJ_Door(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 14;
        gp.obj[mapNum][i].worldY = gp.tileSize * 28;
        i++;

        gp.obj[mapNum][i] = new OBJ_Door(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 22;
        gp.obj[mapNum][i].worldY = gp.tileSize * 21;
        i++;

        gp.obj[mapNum][i] = new OBJ_Door(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 12;
        gp.obj[mapNum][i].worldY = gp.tileSize * 12;
        i++;

        gp.obj[mapNum][i] = new OBJ_Lantern(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 37;
        gp.obj[mapNum][i].worldY = gp.tileSize * 21;
        i++;

        gp.obj[mapNum][i] = new OBJ_Potion_Red(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 21;
        gp.obj[mapNum][i].worldY = gp.tileSize * 20;
        i++;

        gp.obj[mapNum][i] = new OBJ_Potion_Red(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 21;
        gp.obj[mapNum][i].worldY = gp.tileSize * 20;
        i++;

        gp.obj[mapNum][i] = new OBJ_Potion_Red(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 21;
        gp.obj[mapNum][i].worldY = gp.tileSize * 20;
        i++;

        gp.obj[mapNum][i] = new OBJ_Potion_Red(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 21;
        gp.obj[mapNum][i].worldY = gp.tileSize * 20;
        i++;

        mapNum = 2;
        i = 0;
        gp.obj[mapNum][i] = new OBJ_Chest(gp);
        gp.obj[mapNum][i].setLoot(new OBJ_Pickaxe(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize * 40;
        gp.obj[mapNum][i].worldY = gp.tileSize * 41;
        i++;

//        gp.obj[mapNum][i] = new OBJ_Door_Iron(gp);
//        gp.obj[mapNum][i].worldX = gp.tileSize * 18;
//        gp.obj[mapNum][i].worldY = gp.tileSize * 23;
        i++;
    }

    public void setNPC() {
        int mapNum = 0;
        int i = 0;

        gp.npc[mapNum][i] = new NPC_Pugtato(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 21;
        gp.npc[mapNum][i].worldY = gp.tileSize * 21;
        i++;

        gp.npc[mapNum][i] = new NPC_OldMan(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 21;
        gp.npc[mapNum][i].worldY = gp.tileSize * 21;
        i++;

        gp.npc[mapNum][i] = new NPC_Andres(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 13;
        gp.npc[mapNum][i].worldY = gp.tileSize * 14;
        i++;

        // MAP 1
        mapNum = 1;
        i = 0;
        gp.npc[mapNum][i] = new NPC_Merchant(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 12;
        gp.npc[mapNum][i].worldY = gp.tileSize * 7;
        i++;

        mapNum = 2;
        i = 0;
        gp.npc[mapNum][i] = new NPC_BigRock(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 20;
        gp.npc[mapNum][i].worldY = gp.tileSize * 25;
        i++;

        gp.npc[mapNum][i] = new NPC_BigRock(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 11;
        gp.npc[mapNum][i].worldY = gp.tileSize * 18;
        i++;

        gp.npc[mapNum][i] = new NPC_BigRock(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 23;
        gp.npc[mapNum][i].worldY = gp.tileSize * 14;
        i++;
    }

    public void setMonster() {
        int mapNum = 0;

        int i = 0;


        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 23;
        gp.monster[mapNum][i].worldY = gp.tileSize * 36;
        i++;

        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 32;
        gp.monster[mapNum][i].worldY = gp.tileSize * 3;
        i++;

        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 23;
        gp.monster[mapNum][i].worldY = gp.tileSize * 37;
        i++;

        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 24;
        gp.monster[mapNum][i].worldY = gp.tileSize * 37;
        i++;

        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 34;
        gp.monster[mapNum][i].worldY = gp.tileSize * 42;
        i++;

        gp.monster[mapNum][i] = new MON_Orc(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 12;
        gp.monster[mapNum][i].worldY = gp.tileSize * 33;
        i++;

        mapNum = 1;
        i = 0;

        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 32;
        gp.monster[mapNum][i].worldY = gp.tileSize * 3;
        i++;

        mapNum = 2;
        i = 0;

        gp.monster[mapNum][i] = new MON_Bat(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 34;
        gp.monster[mapNum][i].worldY = gp.tileSize * 39;
        i++;

        gp.monster[mapNum][i] = new MON_Bat(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 36;
        gp.monster[mapNum][i].worldY = gp.tileSize * 25;
        i++;

        gp.monster[mapNum][i] = new MON_Bat(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 39;
        gp.monster[mapNum][i].worldY = gp.tileSize * 26;
        i++;

        gp.monster[mapNum][i] = new MON_Bat(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 28;
        gp.monster[mapNum][i].worldY = gp.tileSize * 11;
        i++;

        gp.monster[mapNum][i] = new MON_Bat(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 10;
        gp.monster[mapNum][i].worldY = gp.tileSize * 19;
        i++;

        mapNum = 3;
        i = 0;

        gp.monster[mapNum][i] = new MON_SkeletonLord(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 23;
        gp.monster[mapNum][i].worldY = gp.tileSize * 16;
        i++;
    }

    public void setInteractiveTile() {
        int mapNum = 0;

        int i = 0;

        gp.iTile[mapNum][i] = new IT_DryTree(gp, 27, 12);i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 28, 12);i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 29, 12);i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 30, 12);i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 32, 12);i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 33, 12);i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 18, 40);i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 17, 40);i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 16, 40);i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 15, 40);i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 14, 40);i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 13, 40);i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 12, 40);i++;

        mapNum = 2;
        i = 0;

        gp.iTile[mapNum][i] = new IT_MetalPlate(gp, 20, 22);i++;
        gp.iTile[mapNum][i] = new IT_MetalPlate(gp, 8, 17);i++;
        gp.iTile[mapNum][i] = new IT_MetalPlate(gp, 15, 28);i++;
    }
}
