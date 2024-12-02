package entity;

import main.GamePanel;

import java.awt.*;
import java.util.Random;

public class NPC_CoffeeBean extends Entity {

    public NPC_CoffeeBean(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 1;

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;
        dialogueSet = -1;

        getImage();
        setDialogue();
    }

    public void getImage() {
        int i = 36;
        up1 = setup("/res/npc/coffeebean/up1", gp.tileSize+i, gp.tileSize+i);
        up2 = setup("/res/npc/coffeebean/up2", gp.tileSize+i, gp.tileSize+i);
        down1 = setup("/res/npc/coffeebean/down1", gp.tileSize+i, gp.tileSize+i);
        down2 = setup("/res/npc/coffeebean/down2", gp.tileSize+i, gp.tileSize+i);
        left1 = setup("/res/npc/coffeebean/left1", gp.tileSize+i, gp.tileSize+i);
        left2 = setup("/res/npc/coffeebean/left2", gp.tileSize+i, gp.tileSize+i);
        right1 = setup("/res/npc/coffeebean/right1", gp.tileSize+i, gp.tileSize+i);
        right2 = setup("/res/npc/coffeebean/right2", gp.tileSize+i, gp.tileSize+i);
    }

    public void setDialogue() {
        dialogues[0][0] = "No please give me one more day to pay my taxes.\n";
        dialogues[0][1] = "Give me back what you’ve seized.\n";
        dialogues[0][2] = "It is our family’s treasure.\n";
        dialogues[0][3] = "I can’t take this anymore… They are asking for too much.\n";
    }

    public void setAction() {

        if (onPath) {
            // int goalCol = 12;
            // int goalRow =  9;
            int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;

            searchPath(this,goalCol, goalRow);
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
