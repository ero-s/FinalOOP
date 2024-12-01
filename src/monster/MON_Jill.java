package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;
import object.OBJ_Rock;

public class MON_Jill extends Entity {

    GamePanel gp;
    public static final String monName = "Jill";

    public MON_Jill(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = type_monster;
        boss = true;
        name = monName;
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 4;
        life = maxLife;
        attack = 2;
        defense = 0;
        exp = 2;
        projectile = new OBJ_Rock(gp);

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

        up1 = setup("/res/monster/jill/up1", gp.tileSize*i, gp.tileSize*i);
        up2 = setup("/res/monster/jill/up2", gp.tileSize*i, gp.tileSize*i);
        down1 = setup("/res/monster/jill/down1", gp.tileSize*i, gp.tileSize*i);
        down2 = setup("/res/monster/jill/down2", gp.tileSize*i, gp.tileSize*i);
        left1 = setup("/res/monster/jill/left1", gp.tileSize*i, gp.tileSize*i);
        left2 = setup("/res/monster/jill/left2", gp.tileSize*i, gp.tileSize*i);
        right1 = setup("/res/monster/jill/right1", gp.tileSize*i, gp.tileSize*i);
        right2 = setup("/res/monster/jill/right2", gp.tileSize*i, gp.tileSize*i);
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

        // scene2 in fall circus
        dialogues[0][0] = "Who dares to enter our sanctuary.";
        dialogues[0][1] = "The villagers are not our concern. We protect what is ours.";

        // scene3 in fall circus
        dialogues[1][0] = "Jack, we must give up our sanctuary. I can't see you get hurt again.";
        dialogues[1][1] = "The spring has more than enough for all of us if we work together.";
    }

    public void setAction() {
        if (onPath) {

            // Search the direction to go
            searchPath(this,getGoalCol(gp.player), getGoalRow(gp.player));

            // Check if it shoots a projectile
            checkShootOrNot(60, 30);

        } else {
            // Check if it starts chasing
            checkStartChasingOrNot(gp.player, 15, 100);

            // Get a random direction
            getRandomDirection(120);
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
