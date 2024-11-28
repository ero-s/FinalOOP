package entity;

import java.awt.Rectangle;
import java.util.Random;

import main.GamePanel;

public class NPC_Pugtato extends Entity {

    public NPC_Pugtato(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 1;
        sleep = true;
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
        up1 = setup("/res/npc/pugtato/up1", gp.tileSize+i, gp.tileSize+i);
        up2 = setup("/res/npc/pugtato/up2", gp.tileSize+i, gp.tileSize+i);
        down1 = setup("/res/npc/pugtato/down1", gp.tileSize+i, gp.tileSize+i);
        down2 = setup("/res/npc/pugtato/down2", gp.tileSize+i, gp.tileSize+i);
        left1 = setup("/res/npc/pugtato/left1", gp.tileSize+i, gp.tileSize+i);
        left2 = setup("/res/npc/pugtato/left2", gp.tileSize+i, gp.tileSize+i);
        right1 = setup("/res/npc/pugtato/right1", gp.tileSize+i, gp.tileSize+i);
        right2 = setup("/res/npc/pugtato/right2", gp.tileSize+i, gp.tileSize+i);
    }

    public void setDialogue() {

        // story! // summeville
        dialogues[0][0] = "You've finally woken up,\nstrange… being?";
        dialogues[0][1] = "It seems like you’re completely\nunaware of what happened to you";
        dialogues[0][2] = "I found you lying on the beach.";
        dialogues[0][3] = "It seemed like you needed\nhelp so I sheltered you";
        dialogues[0][4] = "Although I was scared of you\nat first, my curiosity got the best of me.";
        dialogues[0][5] = "You’re in Summerville, a capital\nof one of the four great seasons";
        dialogues[0][6] = "As for your family, I’ve never\nseen people who look like you.";


        // scene 2
        dialogues[1][0] = "Help me, traveler! It’s a rotten one!";
        dialogues[1][1] = "Grab the compost hammer. It’s powered by the roots!";

        // after narration
        dialogues[2][0] = "Off to adventure we go!";

        //gates at the summerville castle
        //dialogues[3][0] = "";

        // fall circus
        dialogues[3][0] = "Let's go. He's just a fraud.";

        //scene1 in Winter
        dialogues[4][0] = "We can help you evacuate.";
        dialogues[4][1] = "We need that water.";

        //scene2 in Winter
        dialogues[5][0] = "The village is in danger. Without water, they won't survive the winter.";

        //scene3 in Winter
        dialogues[6][0] = "So, you’re really going to share with us.";

        //scene4 in Winter
        dialogues[7][0] = "The villagers are safe now. Time to head to Spring.";
    }

    public void setAction() {

        if (onPath) {
            // int goalCol = 12;
            // int goalRow =  9;
            int goalCol = 14 * gp.tileSize;
            int goalRow = 10 * gp.tileSize;

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

    public void speak(int dialogueSet) {
        facePlayer();
        startDialogue(this, dialogueSet);
        dialogueSet++;
        if(dialogues[dialogueSet][0] == null){
            dialogueSet = 0;                // resets to first set of dialogue
//            dialogueSet--;                // repeats the last set of dialogue
        }
        sleep = false;
        onPath = true;
    }
}
