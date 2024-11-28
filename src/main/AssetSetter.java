package main;

import entity.NPC_Andres;
import entity.NPC_BigRock;
import entity.NPC_Merchant;
import entity.NPC_OldMan;
import entity.NPC_Pugtato;
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
    }
    public void setNPC() {
        int mapNum = 0;
        int i = 0;

        gp.npc[mapNum][i] = new NPC_Pugtato(gp);
        gp.npc[mapNum][i].sleep = true;
        gp.npc[mapNum][i].worldX = gp.tileSize * 42;
        gp.npc[mapNum][i].worldY = gp.tileSize * 33;
        i++;



    }

    public void setMonster() {
        int mapNum = 0;

        int i = 0;


        mapNum = 4;
        gp.monster[mapNum][i] = new MON_PickleRick(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 22;
        gp.monster[mapNum][i].worldY = gp.tileSize * 11;
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
