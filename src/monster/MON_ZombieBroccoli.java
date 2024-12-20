package monster;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

import java.util.Random;

public class MON_ZombieBroccoli extends Entity {

    GamePanel gp;

    public MON_ZombieBroccoli(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = type_monster;
        name = "Zombie Broccoli";
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
        up1 = setup("/res/monster/zombiebroccoli/up1", gp.tileSize, gp.tileSize);
        up2 = setup("/res/monster/zombiebroccoli/up2", gp.tileSize, gp.tileSize);
        down1 = setup("/res/monster/zombiebroccoli/down1", gp.tileSize, gp.tileSize);
        down2 = setup("/res/monster/zombiebroccoli/down2", gp.tileSize, gp.tileSize);
        left1 = setup("/res/monster/zombiebroccoli/left1", gp.tileSize, gp.tileSize);
        left2 = setup("/res/monster/zombiebroccoli/left2", gp.tileSize, gp.tileSize);
        right1 = setup("/res/monster/zombiebroccoli/right1", gp.tileSize, gp.tileSize);
        right2 = setup("/res/monster/zombiebroccoli/right2", gp.tileSize, gp.tileSize);
    }

//    public void getImage() {
//        up1 = setup("/res/monster/zombiebroccoli/up1", gp.tileSize, gp.tileSize);
//        up2 = setup("/res/monster/zombiebroccoli/up2", gp.tileSize, gp.tileSize);
//        down1 = setup("/res/monster/zombiebroccoli/down1", gp.tileSize, gp.tileSize);
//        down2 = setup("/res/monster/zombiebroccoli/down2", gp.tileSize, gp.tileSize);
//        left1 = setup("/res/monster/zombiebroccoli/left1", gp.tileSize, gp.tileSize);
//        left2 = setup("/res/monster/zombiebroccoli/left2", gp.tileSize, gp.tileSize);
//        right1 = setup("/res/monster/zombiebroccoli/right1", gp.tileSize, gp.tileSize);
//        right2 = setup("/res/monster/zombiebroccoli/right2", gp.tileSize, gp.tileSize);
//    }

    public void setAction() {
        if (onPath) {

            // Check if it stops chasing
            checkStopChasingOrNot(gp.player, 40, 100);

            // Search the direction to go
            searchPath(this,getGoalCol(gp.player), getGoalRow(gp.player));
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
