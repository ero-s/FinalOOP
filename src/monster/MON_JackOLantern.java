package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;
import object.OBJ_Rock;

public class MON_JackOLantern extends Entity {

    GamePanel gp;
    public static final String monName = "Jack o'Lantern";

    public MON_JackOLantern(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = type_monster;
        boss = true;
        name = monName;
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 80;
        life = maxLife;
        attack = 12;
        defense = 3;
        exp = 50;
        knockBackPower = 8;
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

    public void setDialogue() {
        dialogues[0][0] = "The ecstacy ";

    }

    public void getImage() {

        int i = 5;
        up1 = setup("/res/monster/jackolantern/up1", gp.tileSize*i, gp.tileSize*i);
        up2 = setup("/res/monster/jackolantern/up2", gp.tileSize*i, gp.tileSize*i);
        down1 = setup("/res/monster/jackolantern/down1", gp.tileSize*i, gp.tileSize*i);
        down2 = setup("/res/monster/jackolantern/down2", gp.tileSize*i, gp.tileSize*i);
        left1 = setup("/res/monster/jackolantern/left1", gp.tileSize*i, gp.tileSize*i);
        left2 = setup("/res/monster/jackolantern/left2", gp.tileSize*i, gp.tileSize*i);
        right1 = setup("/res/monster/jackolantern/right1", gp.tileSize*i, gp.tileSize*i);
        right2 = setup("/res/monster/jackolantern/right2", gp.tileSize*i, gp.tileSize*i);
    }

    public void getAttackImage(){

        int i = 5;
        attackUp1 = setup("/res/monster/jackolantern/up1", gp.tileSize*i, gp.tileSize*i);
        attackUp2 = setup("/res/monster/jackolantern/up2", gp.tileSize*i, gp.tileSize*i);
        attackDown1 = setup("/res/monster/jackolantern/down1", gp.tileSize*i, gp.tileSize*i);
        attackDown2 = setup("/res/monster/jackolantern/down2", gp.tileSize*i, gp.tileSize*i);
        attackLeft1 = setup("/res/monster/jackolantern/left1", gp.tileSize*i, gp.tileSize*i);
        attackLeft2 = setup("/res/monster/jackolantern/left2", gp.tileSize*i, gp.tileSize*i);
        attackRight1 = setup("/res/monster/jackolantern/right1", gp.tileSize*i, gp.tileSize*i);
        attackRight2 = setup("/res/monster/jackolantern/right2", gp.tileSize*i, gp.tileSize*i);
    }

    

    public void setAction() {
        if(!inRage && life < maxLife/2){
            inRage = true;
            getImage();
            defaultSpeed++;
            speed = defaultSpeed;
            attack += 2;
        }

        if (getTileDistance(gp.player) < 10) {
            moveTowardPlayer(60);
        } else {

            // Get a random direction
            getRandomDirection(60);
            checkShootOrNot(200, 60);
        }

        // Check if it attacks
        if(!attacking){
            checkAttackOrNot(60, gp.tileSize*7, gp.tileSize*5);
            checkShootOrNot(60, 30);
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
