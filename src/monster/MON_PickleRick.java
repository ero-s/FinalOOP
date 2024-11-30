package monster;

import entity.Entity;
import main.GamePanel;
import object.*;
import object.Skills_PK.PR_AcidSplash;
import object.Skills_PK.PR_SludgeBomb;

import java.util.Random;

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
        maxLife = 435;
        life = maxLife;
        maxMana = 50;
        mana = maxMana;
        attack = 9;
        defense = 2;
        exp = 30;
        knockBackPower = 8;

        projectile = new PR_SludgeBomb(gp);
        projectile.setUser(this);

        skill1 = new PR_AcidSplash(gp);
        skill1.setUser(this);

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
        up1 = setup("/res/monster/picklerick/up1", gp.tileSize*i, gp.tileSize*i);
        up2 = setup("/res/monster/picklerick/up2", gp.tileSize*i, gp.tileSize*i);
        down1 = setup("/res/monster/picklerick/down1", gp.tileSize*i, gp.tileSize*i);
        down2 = setup("/res/monster/picklerick/down2", gp.tileSize*i, gp.tileSize*i);
        left1 = setup("/res/monster/picklerick/left1", gp.tileSize*i, gp.tileSize*i);
        left2 = setup("/res/monster/picklerick/left2", gp.tileSize*i, gp.tileSize*i);
        right1 = setup("/res/monster/picklerick/right1", gp.tileSize*i, gp.tileSize*i);
        right2 = setup("/res/monster/picklerick/right2", gp.tileSize*i, gp.tileSize*i);
    }

    public void getAttackImage(){

        int i = 5;
        attackUp1 = setup("/res/monster/picklerick/up1", gp.tileSize*i, gp.tileSize*i);
        attackUp2 = setup("/res/monster/picklerick/up2", gp.tileSize*i, gp.tileSize*i);
        attackDown1 = setup("/res/monster/picklerick/down1", gp.tileSize*i, gp.tileSize*i);
        attackDown2 = setup("/res/monster/picklerick/down2", gp.tileSize*i, gp.tileSize*i);
        attackLeft1 = setup("/res/monster/picklerick/left1", gp.tileSize*i, gp.tileSize*i);
        attackLeft2 = setup("/res/monster/picklerick/left2", gp.tileSize*i, gp.tileSize*i);
        attackRight1 = setup("/res/monster/picklerick/right1", gp.tileSize*i, gp.tileSize*i);
        attackRight2 = setup("/res/monster/picklerick/right2", gp.tileSize*i, gp.tileSize*i);
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
        }

        if (getTileDistance(gp.player) < 10) {
            moveTowardPlayer(20);

            // Consistently use SludgeBomb and occasionally use AcidSplash
            if (new Random().nextInt(0, 100) < 20) { // 20% chance to use AcidSplash
                acidSplash();
            } else {
                shootProjectile(); // SludgeBomb
            }

        } else {
            // Random movement
            getRandomDirection(60);

            // Randomly decide to shoot SludgeBomb
            if (new Random().nextInt(0, 100) < 30) { // 30% chance to shoot
                shootProjectile();
            }
        }

        // Check for melee attacks
        if (!attacking) {
            checkAttackOrNot(60, gp.tileSize * 7, gp.tileSize * 5);
        }
    }


    public void damageReaction() {
        actionLockCounter = 0;
    }

    public void checkDrop() {

        dropItem(new OBJ_BlueHeart(gp));
        dropItem(new OBJ_Key(gp));

    }

    public void acidSplash(){
        if (shotAvailableCounter >= 30 && skill1.haveResource(this)){
            skill1.set(worldX, worldY, direction, true, this);

            // CHECK VACANCY
            for (int j = 0; j < gp.projectile[1].length; j++) {
                if (gp.projectile[gp.currentMap][j] == null) {
                    gp.projectile[gp.currentMap][j] = skill1;
                    break;
                }
            }
            gp.playSE(10);
        }
    }

    private void shootProjectile() {
        if (shotAvailableCounter >= 30 && projectile.haveResource(this)) {
            projectile.set(worldX, worldY, direction, true, this);

            // Place the projectile in the game world
            for (int i = 0; i < gp.projectile[1].length; i++) {
                gp.projectile[gp.currentMap][i] = projectile;
                break;
            }
            shotAvailableCounter = 0;
            gp.playSE(10); // Play shooting sound
        }
    }

    //nag rest


}
