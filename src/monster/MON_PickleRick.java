package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.*;

public class MON_PickleRick extends Entity {

    GamePanel gp;
    public static final String monName = "Pickle Rick";

    public MON_PickleRick(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = type_monster;
        boss = true;
        name = monName;
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 5;
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
        up1 = setup("/res/monster/picklerick/up1", gp.tileSize*2, gp.tileSize*2);
        up2 = setup("/res/monster/picklerick/up2", gp.tileSize*2, gp.tileSize*2);
        down1 = setup("/res/monster/picklerick/down1", gp.tileSize*2, gp.tileSize*2);
        down2 = setup("/res/monster/picklerick/down2", gp.tileSize*2, gp.tileSize*2);
        left1 = setup("/res/monster/picklerick/left1", gp.tileSize*2, gp.tileSize*2);
        left2 = setup("/res/monster/picklerick/left2", gp.tileSize*2, gp.tileSize*2);
        right1 = setup("/res/monster/picklerick/right1", gp.tileSize*2, gp.tileSize*2);
        right2 = setup("/res/monster/picklerick/right2", gp.tileSize*2, gp.tileSize*2);
    }

    public void setAction() {
        if (onPath) {

            // Search the direction to go
            searchPath(getGoalCol(gp.player), getGoalRow(gp.player));

            // Check if it shoots a projectile
            checkShootOrNot(60, 20);

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
        dropItem(new OBJ_BlueHeart(gp));

    }
}
