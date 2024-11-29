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
        maxLife = 30;
        life = maxLife;
        attack = 8;
        defense = 2;
        exp = 15;
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

        int i = 36;
        up1 = setup("/res/monster/mogger/up1", gp.tileSize+i, gp.tileSize+i);
        up2 = setup("/res/monster/mogger/up2", gp.tileSize+i, gp.tileSize+i);
        down1 = setup("/res/monster/mogger/down1", gp.tileSize+i, gp.tileSize+i);
        down2 = setup("/res/monster/mogger/down2", gp.tileSize+i, gp.tileSize+i);
        left1 = setup("/res/monster/mogger/left1", gp.tileSize+i, gp.tileSize+i);
        left2 = setup("/res/monster/mogger/left2", gp.tileSize+i, gp.tileSize+i);
        right1 = setup("/res/monster/mogger/right1", gp.tileSize+i, gp.tileSize+i);
        right2 = setup("/res/monster/mogger/right2", gp.tileSize+i, gp.tileSize+i);
    }

    public void getAttackImage(){

        int i = 36;

        attackUp1 = setup("/res/monster/mewer/up1", gp.tileSize+i, gp.tileSize+i);
        attackUp2 = setup("/res/monster/mewer/up2", gp.tileSize+i, gp.tileSize+i);
        attackDown1 = setup("/res/monster/mewer/down1", gp.tileSize+i, gp.tileSize+i);
        attackDown2 = setup("/res/monster/mewer/down2", gp.tileSize+i, gp.tileSize+i);
        attackLeft1 = setup("/res/monster/mewer/left1", gp.tileSize+i, gp.tileSize+i);
        attackLeft2 = setup("/res/monster/mewer/left2", gp.tileSize+i, gp.tileSize+i);
        attackRight1 = setup("/res/monster/mewer/right1", gp.tileSize+i, gp.tileSize+i);
        attackRight2 = setup("/res/monster/mewer/right2", gp.tileSize+i, gp.tileSize+i);
    }

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
