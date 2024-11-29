package entity;

import main.GamePanel;

import java.awt.*;
import java.util.Random;

public class NPC_Portal extends Entity {

    public NPC_Portal(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 0;
        sleep = false;
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;
        dialogueSet = 0;

        getImage();
        setDialogue();
    }

    public void getImage() {
        int i = 0;
        up1 = setup("/res/npc/portal_0", gp.tileSize*2, gp.tileSize+i);
        up2 = setup("/res/npc/portal_1", gp.tileSize*2, gp.tileSize+i);
        down1 = setup("/res/npc/portal_2", gp.tileSize*2, gp.tileSize+i);
        down2 = setup("/res/npc/portal_3", gp.tileSize*2, gp.tileSize+i);
        left1 = setup("/res/npc/portal_4", gp.tileSize*2, gp.tileSize+i);
        left2 = setup("/res/npc/portal_5", gp.tileSize*2, gp.tileSize+i);
        right1 = setup("/res/npc/portal_0", gp.tileSize*2, gp.tileSize+i);
        right2 = setup("/res/npc/portal_1", gp.tileSize*2, gp.tileSize+i);
    }

    public void setDialogue() {

        // story! // summeville
        dialogues[0][0] = "Teleporting to...";

    }

    public void setAction() {
        actionLockCounter++;

        if (actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1;

            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75 && i <= 100) {
                direction = "right";
            }

            actionLockCounter = 0;
//        if (onPath) {
//            // int goalCol = 12;
//            // int goalRow =  9;
//            int goalCol = 14 * gp.tileSize;
//            int goalRow = 10 * gp.tileSize;
//
//            searchPath(goalCol, goalRow);
//        } else {
//
//            actionLockCounter++;
//
//            if (actionLockCounter == 120) {
//                Random random = new Random();
//                int i = random.nextInt(100) + 1;
//
//                if (i <= 25) {
//                    direction = "up";
//                }
//                if (i > 25 && i <= 50) {
//                    direction = "down";
//                }
//                if (i > 50 && i <= 75) {
//                    direction = "left";
//                }
//                if (i > 75 && i <= 100) {
//                    direction = "right";
//                }
//
//                actionLockCounter = 0;
//            }
//        }
        }
    }

    public void speak(int dialogueSet) {
        facePlayer();
        startDialogue(this, dialogueSet);
        gp.eHandler.teleport(1,45,15);
        dialogueSet++;
        if(dialogues[dialogueSet][0] == null){
            dialogueSet = 0;                // resets to first set of dialogue
               dialogueSet--;                // repeats the last set of dialogue
        }
    }
}

