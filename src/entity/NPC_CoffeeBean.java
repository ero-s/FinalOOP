package entity;

import java.awt.Rectangle;
import java.util.Random;

import main.GamePanel;

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
        up1 = setup("/res/npc/coffeebean/up1", gp.tileSize, gp.tileSize);
        up2 = setup("/res/npc/coffeebean/up2", gp.tileSize, gp.tileSize);
        down1 = setup("/res/npc/coffeebean/down1", gp.tileSize, gp.tileSize);
        down2 = setup("/res/npc/coffeebean/down2", gp.tileSize, gp.tileSize);
        left1 = setup("/res/npc/coffeebean/left1", gp.tileSize, gp.tileSize);
        left2 = setup("/res/npc/coffeebean/left2", gp.tileSize, gp.tileSize);
        right1 = setup("/res/npc/coffeebean/right1", gp.tileSize, gp.tileSize);
        right2 = setup("/res/npc/coffeebean/right2", gp.tileSize, gp.tileSize);
    }

    public void setDialogue() {
        dialogues[0][0] = "Hello, traveler.";
        dialogues[0][1] = "This island holds many secrets, some \nyou may find and others you might not.";
        dialogues[0][2] = "I once sought the treasures here, \nbut now I merely observe.";
        dialogues[0][3] = "May your journey be fruitful!";

        dialogues[1][0] = "Greetings, adventurer.";
        dialogues[1][1] = "The winds here tell tales of daring \nheroes and mysterious treasures.";
        dialogues[1][2] = "Though my time has passed, I find joy \nin guiding those who follow the path.";
        dialogues[1][3] = "Be wise, and tread carefully.";

        dialogues[2][0] = "Ah, a brave soul!";
        dialogues[2][1] = "This island isn't for the faint of heart. \nLegends say the treasure changes with \neach seeker.";
        dialogues[2][2] = "Once, I sought glory here, but now I \nsavor the peace of these shores.";
        dialogues[2][3] = "May fortune favor you, always.";

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
        if(dialogues[dialogueSet][0] == null){
            dialogueSet = 0;                // resets to first set of dialogue
//            dialogueSet--;                // repeats the last set of dialogue
        }

//        onPath = true;
    }
}
