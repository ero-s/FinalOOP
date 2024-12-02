package monster;

import java.util.Random;

import entity.Entity;
import main.CutsceneManager;
import main.GamePanel;
import object.*;
import object.OBJ_TrophyJoker;

public class MON_CursedOnion extends Entity {

    GamePanel gp;
    public static final String monName = "\"Cursed Onion\"";
    public int monCount = 1;

    public MON_CursedOnion(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = type_monster;
        boss = true;
        name = monName;
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 660;
        life = maxLife;
        attack = 12;
        defense = 6;
        exp = 50;
        knockBackPower = 8;


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

    public void setDialogue() {
        dialogues[0][0] = "The ecstacy ";

    }

    public void getImage() {

        int i = 5;
        up1 = setup("/res/monster/cursedonion/up1", gp.tileSize*i, gp.tileSize*i);
        up2 = setup("/res/monster/cursedonion/up2", gp.tileSize*i, gp.tileSize*i);
        down1 = setup("/res/monster/cursedonion/down1", gp.tileSize*i, gp.tileSize*i);
        down2 = setup("/res/monster/cursedonion/down2", gp.tileSize*i, gp.tileSize*i);
        left1 = setup("/res/monster/cursedonion/left1", gp.tileSize*i, gp.tileSize*i);
        left2 = setup("/res/monster/cursedonion/left2", gp.tileSize*i, gp.tileSize*i);
        right1 = setup("/res/monster/cursedonion/right1", gp.tileSize*i, gp.tileSize*i);
        right2 = setup("/res/monster/cursedonion/right2", gp.tileSize*i, gp.tileSize*i);
    }

    public void getAttackImage(){

        int i = 5;
        attackUp1 = setup("/res/monster/cursedonion/up1", gp.tileSize*i, gp.tileSize*i);
        attackUp2 = setup("/res/monster/cursedonion/up2", gp.tileSize*i, gp.tileSize*i);
        attackDown1 = setup("/res/monster/cursedonion/down1", gp.tileSize*i, gp.tileSize*i);
        attackDown2 = setup("/res/monster/cursedonion/down2", gp.tileSize*i, gp.tileSize*i);
        attackLeft1 = setup("/res/monster/cursedonion/left1", gp.tileSize*i, gp.tileSize*i);
        attackLeft2 = setup("/res/monster/cursedonion/left2", gp.tileSize*i, gp.tileSize*i);
        attackRight1 = setup("/res/monster/cursedonion/right1", gp.tileSize*i, gp.tileSize*i);
        attackRight2 = setup("/res/monster/cursedonion/right2", gp.tileSize*i, gp.tileSize*i);
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

        int i = new Random().nextInt(20) + 1;
        if(i == 1){
            arise();
        }
    }

    public void setAction() {
        if (!gp.monster[6][1].alive) {
            inRage = true;
            getImage();
            defaultSpeed++;
            speed = defaultSpeed;
            attack += 2;
            if(skill1Counter == 3600){
                acidSplash();
            }
            if(!ultUsed){
                selfReliance();
                ultUsed = true;
            }

            shootProjectile();
        }

        if (getTileDistance(gp.player) < 10) {
            moveTowardPlayer(20);
            // Randomly decide to shoot SludgeBomb
            if (skill1Counter == 4800) { // 30% chance to shoot
                acidSplash();
            }
            else{
                shootProjectile();
            }
        } else {
            // Random movement
            getRandomDirection(60);
            if (new Random().nextInt(0, 100)+1 < 40) { // 30% chance to shoot
                if(!skill1.alive){
                    acidSplash();
                }
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
        direction = gp.player.direction;
        onPath = true;
    }

//    public void scene(){
//        gp.csManager.sceneNum = CutsceneManager.CURSED_ONION_BACKSTORY; // Set the cutscene number
    public void scene() {
        gp.csManager.sceneNum = CutsceneManager.CURSED_ONION_BACKSTORY; // Set the c // Set the cutscene number
        gp.gameState = gp.cutsceneState; // Switch game state
        gp.csManager.scenePhase = 0;

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



                    gp.monster[4][monCount] = new MON_ZombieBroccoli(gp);
                    gp.monster[4][monCount].worldX = gp.tileSize * col;
                    gp.monster[4][monCount].worldY = gp.tileSize * row;

                monCount++;
            }
        }
