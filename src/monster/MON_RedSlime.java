package monster;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;
import object.OBJ_Rock;

import java.util.Random;

public class MON_RedSlime extends Entity {

    GamePanel gp;

    public MON_RedSlime(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = type_monster;
        name = "Red Slime";
        defaultSpeed = 2;
        speed = defaultSpeed;
        maxLife = 15;
        life = maxLife;
        attack = 4;
        defense = 1;
        exp = 6;
        projectile = new OBJ_Rock(gp);

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public void getImage() {
        up1 = setup("/res/monster/redslime/redslime_down_1", gp.tileSize, gp.tileSize);
        up2 = setup("/res/monster/redslime/redslime_down_2", gp.tileSize, gp.tileSize);
        down1 = setup("/res/monster/redslime/redslime_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/res/monster/redslime/redslime_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/res/monster/redslime/redslime_down_1", gp.tileSize, gp.tileSize);
        left2 = setup("/res/monster/redslime/redslime_down_2", gp.tileSize, gp.tileSize);
        right1 = setup("/res/monster/redslime/redslime_down_1", gp.tileSize, gp.tileSize);
        right2 = setup("/res/monster/redslime/redslime_down_2", gp.tileSize, gp.tileSize);
    }

    public void setAction() {
        if (onPath) {

            // Check if it stops chasing
            checkStopChasingOrNot(gp.player, 15, 100);

            // Search the direction to go
            searchPath(this,getGoalCol(gp.player), getGoalRow(gp.player));

            // Check if it shoots a projectile
            checkShootOrNot(200, 30);
        } else {
            // Check if it starts chasing
            checkStartChasingOrNot(gp.player, 5, 100);

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
