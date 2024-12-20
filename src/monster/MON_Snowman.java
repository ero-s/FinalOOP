package monster;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

import java.util.Random;

public class MON_Snowman extends Entity {

    GamePanel gp;

    public MON_Snowman(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = type_monster;
        name = "Snowman";
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 60;
        life = maxLife;
        attack = 12;
        defense = 1;
        exp = 18;
        knockBackPower = 5;

        solidArea.x = 4;
        solidArea.y = 4;
        solidArea.width = 40;
        solidArea.height = 44;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attackArea.width = 48;
        attackArea.height = 48;
        motion1_duration = 40;
        motion2_duration = 80;

        getImage();
        getAttackImage();
    }

    public void getImage() {
        up1 = setup("/res/monster/snowman/snowman_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/res/monster/snowman/snowman_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/res/monster/snowman/snowman_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/res/monster/snowman/snowman_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/res/monster/snowman/snowman_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/res/monster/snowman/snowman_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("/res/monster/snowman/snowman_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/res/monster/snowman/snowman_right_2", gp.tileSize, gp.tileSize);
    }

    public void getAttackImage(){
        attackUp1 = setup("/res/monster/snowman/snowman_attack_up_1", gp.tileSize, gp.tileSize * 2);
        attackUp2 = setup("/res/monster/snowman/snowman_attack_up_2", gp.tileSize, gp.tileSize * 2);
        attackDown1 = setup("/res/monster/snowman/snowman_attack_down_1", gp.tileSize, gp.tileSize * 2);
        attackDown2 = setup("/res/monster/snowman/snowman_attack_down_2", gp.tileSize, gp.tileSize * 2);
        attackLeft1 = setup("/res/monster/snowman/snowman_attack_left_1", gp.tileSize * 2, gp.tileSize);
        attackLeft2 = setup("/res/monster/snowman/snowman_attack_left_2", gp.tileSize * 2, gp.tileSize);
        attackRight1 = setup("/res/monster/snowman/snowman_attack_right_1", gp.tileSize * 2, gp.tileSize);
        attackRight2 = setup("/res/monster/snowman/snowman_attack_right_2", gp.tileSize * 2, gp.tileSize);
    }

    public void setAction() {
        if (onPath) {

            // Check if it stops chasing
            checkStopChasingOrNot(gp.player, 15, 100);

            // Search the direction to go
            searchPath(this, getGoalCol(gp.player), getGoalRow(gp.player));
        } else {
            // Check if it starts chasing
            checkStartChasingOrNot(gp.player, 15, 100);

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
