package monster;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;
import object.OBJ_Rock;
import object.Skills_Jack.OBJ_Blizzard;
import object.Skills_Jack.OBJ_Icycle;

import java.util.AbstractList;
import java.util.Random;
import java.util.random.RandomGenerator;

public class MON_Jack extends Entity {

    GamePanel gp;
    int skill1Counter = 0;
    boolean ultUsed = false;
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
        projectile = new OBJ_Icycle(gp);
        skill1 = new OBJ_Blizzard(gp);
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
    public void getAttackImage(){
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

    public void setAction() {
        if (!inRage && life < maxLife / 2) {
            inRage = true;
            getImage();
            defaultSpeed++;
            speed = defaultSpeed;
            attack += 2;
            if(skill1Counter == 3600){
                blizzard();
                skill1Counter = 0;
            }
            icycle();
        }

        if (getTileDistance(gp.player) < 10) {
            moveTowardPlayer(20);
            // Randomly decide to shoot SludgeBomb
            if (skill1Counter == 4800) { // 30% chance to shoot
                ;blizzard();
                skill1Counter = 0;
            }
            else{
                icycle();
            }
        } else {
            // Random movement
            getRandomDirection(60);
            if (new Random().nextInt(0, 100)+1 < 40) { // 30% chance to shoot
                if(!skill1.alive){
                    blizzard();
                }
            }
            else{
                icycle();
            }
        }

        // Check for melee attack\[-??/p00ol,lo87nmn
        if (!attacking) {
            checkAttackOrNot(60, gp.tileSize * 7, gp.tileSize * 5);
        }
        skill1Counter++;
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

    private void icycle() {
        if ((shotAvailableCounter >= 30) && projectile.haveResource(this)) {
            projectile.set(worldX, worldY, direction, true, this);

            // Place the projectile in the game world
            for (int i = 0; i < gp.projectile[1].length; i++) {
                if(gp.projectile[gp.currentMap][i] == null){
                    gp.projectile[gp.currentMap][i] = projectile;
                }
                break;
            }
            shotAvailableCounter = 0;
            gp.playSE(10); // Play shooting sound
        }
    }

    private void blizzard() {
        if (shotAvailableCounter >= 30 && skill1.haveResource(this)) {
            this.skill1.set(worldX, worldY, direction, true, this);
            this.skill1.subtractResource(this);

            // CHECK VACANCY
            for (int i = 0; i < gp.projectile[1].length; i++) {
                if (gp.projectile[gp.currentMap][i] == null) {
                    gp.projectile[gp.currentMap][i] = this.skill1;
                    break;
                }
            }
            shotAvailableCounter = 0;
            gp.playSE(10);
        }
    }
}
