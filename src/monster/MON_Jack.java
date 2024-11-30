package monster;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;
import object.OBJ_Rock;

import java.util.Random;

public class MON_Jack extends Entity {

    GamePanel gp;
    public static final String monName = "King Jack";
    public MON_Jack(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = type_monster;
        name = monName;
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 10;
        life = maxLife;
        attack = 8;
        defense = 2;
        exp = 10;
        knockBackPower = 5;

        int size = gp.tileSize*5;
        solidArea.x = 48;
        solidArea.y = 48;
        solidArea.width = size - 48 * 2;
        solidArea.height = size - 48;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attackArea.width = 170;
        attackArea.height = 170;
        motion1_duration = 25;
        motion2_duration = 50;

        getImage();
    }

    public void getImage() {
        int i = 5;
        up1 = setup("/res/monster/jack/up1", gp.tileSize*i*i, gp.tileSize*i);
        up2 = setup("/res/monster/jack/up2", gp.tileSize*i, gp.tileSize*i);
        down1 = setup("/res/monster/jack/down1", gp.tileSize*i, gp.tileSize*i);
        down2 = setup("/res/monster/jack/down2", gp.tileSize*i, gp.tileSize*i);
        left1 = setup("/res/monster/jack/left1", gp.tileSize*i, gp.tileSize*i);
        left2 = setup("/res/monster/jack/left2", gp.tileSize*i, gp.tileSize*i);
        right1 = setup("/res/monster/jack/right1", gp.tileSize*i, gp.tileSize*i);
        right2 = setup("/res/monster/jack/right2", gp.tileSize*i, gp.tileSize*i);
    }

    public void setDialogue() {
//        dialogues[0][0] = "Hello, traveler.";
//        dialogues[0][1] = "This island holds many secrets, some \nyou may find and others you might not.";
//        dialogues[0][2] = "I once sought the treasures here, \nbut now I merely observe.";
//        dialogues[0][3] = "May your journey be fruitful!";
//
//        dialogues[1][0] = "Greetings, adventurer.";
//        dialogues[1][1] = "The winds here tell tales of daring \nheroes and mysterious treasures.";
//        dialogues[1][2] = "Though my time has passed, I find joy \nin guiding those who follow the path.";
//        dialogues[1][3] = "Be wise, and tread carefully.";
//
//        dialogues[2][0] = "Ah, a brave soul!";
//        dialogues[2][1] = "This island isn't for the faint of heart. \nLegends say the treasure changes with \neach seeker.";
//        dialogues[2][2] = "Once, I sought glory here, but now I \nsavor the peace of these shores.";
//        dialogues[2][3] = "May fortune favor you, always.";

        // scene4 in winter
        dialogues[0][0] = "I'm sorry, Jill...";
    }

//    public void getAttackImage(){
//        attackUp1 = setup("/res/monster/mewer/mewer_attack_up_1", gp.tileSize*i, gp.tileSize*i * 2);
//        attackUp2 = setup("/res/monster/mewer/mewer_attack_up_2", gp.tileSize*i, gp.tileSize*i * 2);
//        attackDown1 = setup("/res/monster/mewer/mewer_attack_down_1", gp.tileSize*i, gp.tileSize*i * 2);
//        attackDown2 = setup("/res/monster/mewer/mewer_attack_down_2", gp.tileSize*i, gp.tileSize*i * 2);
//        attackLeft1 = setup("/res/monster/mewer/mewer_attack_left_1", gp.tileSize*i * 2, gp.tileSize*i);
//        attackLeft2 = setup("/res/monster/mewer/mewer_attack_left_2", gp.tileSize*i * 2, gp.tileSize*i);
//        attackRight1 = setup("/res/monster/mewer/mewer_attack_right_1", gp.tileSize*i * 2, gp.tileSize*i);
//        attackRight2 = setup("/res/monster/mewer/mewer_attack_right_2", gp.tileSize*i * 2, gp.tileSize*i);
//    }

    public void setAction() {
        if (onPath) {

            // Check if it stops chasing
            checkStopChasingOrNot(gp.player, 40, 100);

            // Search the direction to go
            searchPath(this, getGoalCol(gp.player), getGoalRow(gp.player));
        } else {
            // Check if it starts chasing
            checkStartChasingOrNot(gp.player, 5, 100);

            // Get a random direction
            getRandomDirection(120);
        }

        // Check if it attacks
        if(!attacking){
            checkAttackOrNot(30, gp.tileSize*4, gp.tileSize);
        }
    }

    public void damageReaction() {
        actionLockCounter = 0;
        direction = gp.player.direction;
        onPath = true;
    }

    public void checkDrop() {
        int i = new Random().nextInt(100) + 1;

        if (i < 50) {
            dropItem(new OBJ_Coin_Bronze(gp));
        }
        if (i >= 50 && i < 75) {
            dropItem(new OBJ_Heart(gp));
        }
        if (i >= 75 && i < 100) {
            dropItem(new OBJ_ManaCrystal(gp));
        }
    }
}
