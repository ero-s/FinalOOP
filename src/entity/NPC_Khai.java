package entity;

import main.GamePanel;

import java.awt.*;
import java.util.Random;

public class NPC_Khai extends Entity {

    public NPC_Khai(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 1;

        solidArea = new Rectangle();
        solidArea.x = 0;
        solidArea.y = 0;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 64;
        solidArea.height = 64;
        dialogueSet = -1;

        getImage();
        setDialogue();
    }

    public void getImage() {
        up1 = setup("/res/npc/khai/up1", gp.tileSize, gp.tileSize);
        up2 = setup("/res/npc/khai/up2", gp.tileSize, gp.tileSize);
        down1 = setup("/res/npc/khai/down1", gp.tileSize, gp.tileSize);
        down2 = setup("/res/npc/khai/down2", gp.tileSize, gp.tileSize);
        left1 = setup("/res/npc/khai/left1", gp.tileSize, gp.tileSize);
        left2 = setup("/res/npc/khai/left2", gp.tileSize, gp.tileSize);
        right1 = setup("/res/npc/khai/right1", gp.tileSize, gp.tileSize);
        right2 = setup("/res/npc/khai/right2", gp.tileSize, gp.tileSize);
    }

    public void setDialogue() {
        dialogues[0][0] = "Welcome to BukidGrooooooooooown!\nMy name is Khai.";
        dialogues[0][1] = "Goodluck on your journey!";
        dialogues[0][2] = "Quote of the week";
        dialogues[0][3] = "Practice makes progress.\nTake your time,\nbut use it wisely.";

    }

    public void setAction() {

        if (onPath) {
            // int goalCol = 12;
            // int goalRow =  9;
            int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;

            searchPath(this, goalCol, goalRow);
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
        if(dialogues[dialogueSet][0] == null){
            dialogueSet = 0;                // resets to first set of dialogue
//            dialogueSet--;                // repeats the last set of dialogue
        }

//        onPath = true;
    }
}
