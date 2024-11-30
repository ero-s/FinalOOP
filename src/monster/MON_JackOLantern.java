package monster;

import java.util.Random;

import entity.Entity;
import main.CutsceneManager;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;
import object.OBJ_Rock;

public class MON_JackOLantern extends Entity {

    GamePanel gp;
    public static final String monName = "Jack o'Lantern";
    public int monCount = 1;
    public int directionCounter = 0;
    public int actCounter = 0;
    public boolean isActing = false;

    public MON_JackOLantern(GamePanel gp) {
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
        getAttackImage();
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

        int i = new Random().nextInt(10) + 1;
        if(i == 1){
            int ran = new Random().nextInt(6) + 1;
            if(ran % 2 == 0){
                orchestra();
            } else if (!isActing && inRage){
                circusAct();
                isActing = true;
            } else if(ran == 4){
                orchestra();
                circusAct();
                isActing = true;
            }
        }
        if(actCounter == 360){
            actCounter = 0;
            speed = defaultSpeed;
            isActing = false;
        }
        actCounter++;
    }

    public void setAction() {
        if(!inRage && life < maxLife/2){
            inRage = true;

            getImage();
            defaultSpeed++;
            speed = defaultSpeed;
            attack += 4;
            defense += 3;
            orchestra();
            orchestra();
            circusAct();
            isActing = true;
        }

        if (getTileDistance(gp.player) > 5) {
            moveTowardPlayer(10);

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

    @Override
    public void scene() {
        gp.csManager.sceneNum = CutsceneManager.JACoLANTERN_BACKSTORY; // Set the cutscene number
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

    public void checkShootOrNot(int rate, int shotInterval){

        int i = new Random().nextInt(rate);
        if (i == 0 && !projectile.alive && shotAvailableCounter == shotInterval) {

            projectile.set(worldX, worldY, direction, true, this);

            // CHECK VACANCY
            for (int ii = 0; ii < gp.projectile[1].length; ii++) {
                if (gp.projectile[gp.currentMap][ii] == null) {
                    gp.projectile[gp.currentMap][ii] = projectile;
                    break;
                }
            }

            shotAvailableCounter = 0;
        }
    }

    public void orchestra(){
        int i = new Random().nextInt(100) + 1;

        if(i == 1){
            for(int j = 0; j < 8; j++){
                int col = new Random().nextInt(18,29) + 1;
                int row = new Random().nextInt(13,20) + 1;

                if(col % 2 == 0){
                    gp.monster[5][monCount] = new MON_Bat(gp);
                    gp.monster[5][monCount].worldX = gp.tileSize * col;
                    gp.monster[5][monCount].worldY = gp.tileSize * row;
                } else {
                    gp.monster[5][monCount] = new MON_RedSlime(gp);
                    gp.monster[5][monCount].worldX = gp.tileSize * col;
                    gp.monster[5][monCount].worldY = gp.tileSize * row;
                }
                monCount++;
            }
        }
    }

    public void circusAct(){
        speed = 10;

        if(directionCounter == 120){
            getRandomDirection(10);
            directionCounter = 0;
        }
        directionCounter++;
    }
}
