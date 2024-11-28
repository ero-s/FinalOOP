package entity;

import java.awt.Rectangle;
import java.util.Random;

import main.GamePanel;

public class NPC_Andres extends Entity {

    public NPC_Andres(GamePanel gp) {
        super(gp);

        direction = "down";
        name = "Andres";
        speed = 1;
        sleep = true;
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
        up1 = setup("/res/npc/andres/up1", gp.tileSize*2, gp.tileSize*2);
        up2 = setup("/res/npc/andres/up2", gp.tileSize*2, gp.tileSize*2);
        down1 = setup("/res/npc/andres/down1", gp.tileSize*2, gp.tileSize*2);
        down2 = setup("/res/npc/andres/down2", gp.tileSize*2, gp.tileSize*2);
        left1 = setup("/res/npc/andres/left1", gp.tileSize*2, gp.tileSize*2);
        left2 = setup("/res/npc/andres/left2", gp.tileSize*2, gp.tileSize*2);
        right1 = setup("/res/npc/andres/right1", gp.tileSize*2, gp.tileSize*2);
        right2 = setup("/res/npc/andres/right2", gp.tileSize*2, gp.tileSize*2);
    }

    public void setDialogue() {
        dialogues[0][0] = "Another fool enters this cursed place. Leave now, or share its fate!";
        dialogues[0][1] = "Why do you feel... familiar? Why does this pain feel so much heavier now? Who... are you?";
        dialogues[0][2] = "Hakobe, I remember now—I once protected you, but now I’ve become your greatest threat.";
    }

    public void setAction() {

        if (onPath) {
            // int goalCol = 12;
            // int goalRow =  9;
            int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;

            searchPath(goalCol, goalRow);
        } else {

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
            }
        }
    }

    public void speak() {
        facePlayer();
        startDialogue(this, dialogueSet);
        dialogueSet++;
        if(dialogues[dialogueSet] != null){
            dialogueSet = 0;                // resets to first set of dialogue
//            dialogueSet--;                // repeats the last set of dialogue
        }

//        onPath = true;
    }
}
