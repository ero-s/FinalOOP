package monster;

import entity.Entity;
import main.CutsceneManager;
import main.GamePanel;
import object.Jill_Skills.JILL_IceSlicer;
import object.OBJ_BlueHeart;
import object.OBJ_Key;

import java.util.Random;

public class MON_Jill extends Entity {

    GamePanel gp;
    private int skill1Counter = 0;
    boolean ultUsed = false;
    public static final String monName = "              Jill";

    public MON_Jill(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = type_monster;
        boss = true;
        name = monName;
        defaultSpeed = 1;
        speed = defaultSpeed;
        //maxLife = 435;
        maxLife = 5;
        life = maxLife;
        maxMana = 50;
        mana = maxMana;
        attack = 9;
        defense = 2;
        exp = 30;
        knockBackPower = 8;

        projectile = new JILL_IceSlicer(gp);
        projectile.setUser(this);

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


        projectile.xOffset = (gp.tileSize * 2 - gp.tileSize) / 2;
        projectile.yOffset = (gp.tileSize * 2 - gp.tileSize) / 2;

        getImage();
        getAttackImage();
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

    public void getAttackImage(){

        int i = 5;
        attackUp1 = setup("/res/monster/jill/up1", gp.tileSize*i, gp.tileSize*i);
        attackUp2 = setup("/res/monster/jill/up2", gp.tileSize*i, gp.tileSize*i);
        attackDown1 = setup("/res/monster/jill/down1", gp.tileSize*i, gp.tileSize*i);
        attackDown2 = setup("/res/monster/jill/down2", gp.tileSize*i, gp.tileSize*i);
        attackLeft1 = setup("/res/monster/jill/left1", gp.tileSize*i, gp.tileSize*i);
        attackLeft2 = setup("/res/monster/jill/left2", gp.tileSize*i, gp.tileSize*i);
        attackRight1 = setup("/res/monster/jill/right1", gp.tileSize*i, gp.tileSize*i);
        attackRight2 = setup("/res/monster/jill/right2", gp.tileSize*i, gp.tileSize*i);
    }

    public void update() {
        if(!sleep){
            if (knockBack) {
                checkCollision();
                if (collisionOn) {
                    knockBackCounter = 0;
                    knockBack = false;
                    speed = defaultSpeed;
                } else if (!collisionOn) {
                    spriteCounter++;
                    if (spriteCounter > 26) {
                        if (spriteNum == 1) {
                            spriteNum = 2;
                        } else if (spriteNum == 2) {
                            spriteNum = 1;
                        }
                        spriteCounter = 0;
                    }
                    switch (knockBackDirection) {
                        case "up": worldY -= speed; break;
                        case "down": worldY += speed; break;
                        case "left": worldX -= speed; break;
                        case "right": worldX += speed; break;
                    }
                }
                knockBackCounter++;
                if (knockBackCounter == 10) {
                    knockBackCounter = 0;
                    knockBack = false;
                    speed = defaultSpeed;
                }
            } else if(attacking){
                attacking();
            }
            else {
                setAction();
                checkCollision();

                // IF COLLISION IS FALSE, PLAYER CAN MOVE
                if (!collisionOn) {
                    switch (direction) {
                        case "up": worldY -= speed; break;
                        case "down": worldY += speed; break;
                        case "left": worldX -= speed; break;
                        case "right": worldX += speed; break;
                    }
                }
                spriteCounter++;
                if (spriteCounter > 26) {
                    if (spriteNum == 1) {
                        spriteNum = 2;
                    } else if (spriteNum == 2) {
                        spriteNum = 1;
                    }
                    spriteCounter = 0;
                }
            }
            if (invincible) {
                invincibleCounter++;
                if (invincibleCounter > 40) {
                    invincible = false;
                    invincibleCounter = 0;
                }
            }

            if (shotAvailableCounter < 30) {
                shotAvailableCounter++;
            }

            if(offBalance){
                offBalanceCounter++;
                if(offBalanceCounter > 60){
                    offBalance = false;
                    offBalanceCounter = 0;
                }
            }
        }
    }

    public void setAction() {
        if (!inRage && life < maxLife / 2) {
            inRage = true;
            getImage();
            defaultSpeed++;
            speed = defaultSpeed;
            attack += 2;
            if(skill1Counter == 3600){
//                acidSplash();
            }
            shootProjectile();
        }

        if (getTileDistance(gp.player) < 10) {
            moveTowardPlayer(20);
            // Randomly decide to shoot SludgeBomb
            if (skill1Counter == 4800) { // 30% chance to shoot
//                acidSplash();
            }
            else{
                shootProjectile();
            }
        } else {
            // Random movement
            getRandomDirection(60);
            if (new Random().nextInt(0, 100)+1 < 40) { // 30% chance to shoot
//                if(!skill1.alive){
////                    acidSplash();
//                }
            }
            else{
                shootProjectile();
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
    }

    public void scene(){
        gp.csManager.sceneNum = CutsceneManager.JACKNJILL_BACKSTORY; // Set the cutscene number
        gp.gameState = gp.cutsceneState; // Switch game state
        gp.csManager.scenePhase = 0;

    }
    public void checkDrop() {

        dropItem(new OBJ_BlueHeart(gp));
        dropItem(new OBJ_Key(gp));

    }
//
//    public void acidSplash(){
//        if (shotAvailableCounter >= 30 && skill1.haveResource(this)) {
//            this.skill1.set(worldX, worldY, direction, true, this);
//            this.skill1.subtractResource(this);
//
//            // CHECK VACANCY
//            for (int i = 0; i < gp.projectile[1].length; i++) {
//                if (gp.projectile[gp.currentMap][i] == null) {
//                    gp.projectile[gp.currentMap][i] = this.skill1;
//                    break;
//                }
//            }
//            shotAvailableCounter = 0;
//            gp.playSE(10);
//        }
//    }

    private void shootProjectile() {
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

    public void selfReliance(){
        gp.monster[4][4] = new MON_PickleRick(gp);
        gp.monster[4][4].life = maxLife/2;
        gp.monster[4][4].inRage = true;
        gp.monster[4][4].worldX = gp.tileSize * 22;
        gp.monster[4][4].worldY = gp.tileSize * 25;
    }
    //nag rest
}