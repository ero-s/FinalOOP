package monster;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;
import object.OBJ_Rock;

import java.util.Random;

public class MON_Mogger extends Entity {

    GamePanel gp;

    public MON_Mogger(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = type_monster;
        name = "Mogger";
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 10;
        life = maxLife;
        attack = 8;
        defense = 2;
        exp = 10;
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
    }

    public void getImage() {
        up1 = setup("/res/monster/mogger/up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/res/monster/mogger/up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/res/monster/mogger/down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/res/monster/mogger/down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/res/monster/mogger/left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/res/monster/mogger/left_2", gp.tileSize, gp.tileSize);
        right1 = setup("/res/monster/mogger/right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/res/monster/mogger/right_2", gp.tileSize, gp.tileSize);
    }

//    public void getAttackImage(){
//        attackUp1 = setup("/res/monster/mogger/mogger_attack_up_1", gp.tileSize, gp.tileSize * 2);
//        attackUp2 = setup("/res/monster/mogger/mogger_attack_up_2", gp.tileSize, gp.tileSize * 2);
//        attackDown1 = setup("/res/monster/mogger/mogger_attack_down_1", gp.tileSize, gp.tileSize * 2);
//        attackDown2 = setup("/res/monster/mogger/mogger_attack_down_2", gp.tileSize, gp.tileSize * 2);
//        attackLeft1 = setup("/res/monster/mogger/mogger_attack_left_1", gp.tileSize * 2, gp.tileSize);
//        attackLeft2 = setup("/res/monster/mogger/mogger_attack_left_2", gp.tileSize * 2, gp.tileSize);
//        attackRight1 = setup("/res/monster/mogger/mogger_attack_right_1", gp.tileSize * 2, gp.tileSize);
//        attackRight2 = setup("/res/monster/mogger/mogger_attack_right_2", gp.tileSize * 2, gp.tileSize);
//    }

    public void setAction() {
        if (onPath) {

            // Check if it stops chasing
            checkStopChasingOrNot(gp.player, 40, 100);

            // Search the direction to go
            searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
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
