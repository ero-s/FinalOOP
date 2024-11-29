package entity;

import java.awt.Rectangle;
import java.util.Random;

import main.GamePanel;

public class NPC_Portal extends Entity {

    public NPC_Portal(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 0;
        sleep = false;
        solidArea = new Rectangle();
        solidArea.x = worldX + solidAreaDefaultX;
        solidArea.y = worldY + solidAreaDefaultY;
        solidArea.width = 32;
        solidArea.height = 32;
        dialogueSet = -1;
        onPath = false;

        xOffset = gp.tileSize*4;
        yOffset = 0;

        int centerXOffset = xOffset;
        int centerYOffset = yOffset;

        switch (direction) {
            case "up":
                this.worldX -= centerXOffset;
                this.worldY -= gp.tileSize + centerYOffset/2;
                break;
            case "down":
                this.worldX -= centerXOffset;
                this.worldY += gp.tileSize - centerYOffset;
                break;
            case "left":
                this.worldX -= gp.tileSize + centerXOffset/2;
                this.worldY -= centerYOffset;
                break;
            case "right":
                this.worldX += gp.tileSize/2 - centerXOffset;
                this.worldY -= centerYOffset;
                break;
        }
        // Adjust initial projectile position based on direction

        getImage();
        setDialogue();
    }

    public void getImage() {
        int i = 2;
        int j = 4;
        up1 = setup("/res/npc/portal/up1", gp.tileSize*j, gp.tileSize*i);
        up2 = setup("/res/npc/portal/up2", gp.tileSize*j, gp.tileSize*i);
        down1 = setup("/res/npc/portal/down1", gp.tileSize*j, gp.tileSize*i);
        down2 = setup("/res/npc/portal/down2", gp.tileSize*j, gp.tileSize*i);
        left1 = setup("/res/npc/portal/left1", gp.tileSize*j, gp.tileSize*i);
        left2 = setup("/res/npc/portal/left2", gp.tileSize*j, gp.tileSize*i);
        right1 = setup("/res/npc/portal/right1", gp.tileSize*j, gp.tileSize*i);
        right2 = setup("/res/npc/portal/right2", gp.tileSize*j, gp.tileSize*i);
    }

    public void setDialogue() {


    }

    public void setAction() {

        if (onPath) {
            // int goalCol = 12;
            // int goalRow =  9;
            int goalCol = 14;
            int goalRow = 11;

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
        gp.eHandler.teleport(1,45,15);
        dialogueSet++;
        if(dialogues[dialogueSet][0] == null){
            dialogueSet = 0;                // resets to first set of dialogue
//            dialogueSet--;                // repeats the last set of dialogue
        }
        sleep = false;
        onPath = true;
    }
}
