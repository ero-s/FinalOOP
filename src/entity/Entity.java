package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;
import monster.MON_SkeletonLord;

public class Entity {
    GamePanel gp;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1,
            attackRight2, guardUp, guardDown, guardLeft, guardRight;
    public BufferedImage image, image2, image3;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public int solidAreaDefaultX, solidAreaDefaultY, mapCollisionDefaultX, mapCollisionDefaultY;
    public boolean collision = false;
    public String dialogues[][] = new String[20][20];
    public Entity attacker;
    public Entity linkedEntity;
    public boolean temp = false;

    // STATE
    public int worldX, worldY;
    public String direction = "down";
    public int spriteNum = 1;
    public int dialogueIndex = 0;
    public int dialogueSet = 0;
    public boolean collisionOn = false;
    public boolean invincible = false;
    public boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    public boolean hpBarOn = false;
    public boolean onPath = false;
    public boolean knockBack = false;
    public String knockBackDirection;
    public boolean guarding = false;
    public boolean transparent = false;
    public boolean offBalance = false;
    public boolean inRage = false;
    public Entity loot;
    public boolean opened = false;
    public boolean sleep = false;
    public Rectangle mapCollision;

    // COUNTER
    public int spriteCounter = 0;
    public int actionLockCounter = 0;
    public int invincibleCounter = 0;
    public int shotAvailableCounter = 0;
    int dyingCounter = 0;
    public int hpBarCounter = 0;
    int knockBackCounter = 0;
    public int guardCounter = 0;
    int offBalanceCounter = 0;

    // CHARACTER ATTRIBUTES
    public String name;
    public int defaultSpeed;
    public int speed;
    public int maxLife;
    public int life;
    public int maxMana;
    public int mana;
    public int ammo;
    public int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public int motion1_duration;
    public int motion2_duration;
    public Entity currentWeapon;
    public Entity currentShield;
    public Projectile projectile;
    public Projectile skill1;
    public Entity currentLight;
    public boolean boss;


    // ITEM ATTRIBUTES
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;
    public int value;
    public int attackValue;
    public int defenseValue;
    public String description = "";
    public int useCost;
    public int price;
    public int knockBackPower = 0;
    public boolean stackable;
    public int amount = 1;
    public int lightRadius;

    // TYPE
    public int type;
    public final int type_player = 0;
    public final int type_npc = 1;
    public final int type_monster = 2;
    public final int type_sword = 3;
    public final int type_axe = 4;
    public final int type_shield = 5;
    public final int type_consumable = 6;
    public final int type_pickupOnly = 7;
    public final int type_obstacle = 8;
    public final int type_light = 9;
    public final int type_pickaxe = 10;

    public Entity(GamePanel gp) { this.gp = gp; }

    public int getScreenX(){
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        return screenX;
    }

    public int getScreenY(){
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        return screenY;
    }


    public int getLeftX() { return worldX + solidArea.x; }

    public int getRightX() { return worldX + solidArea.x + solidArea.width; }

    public int getTopY() { return worldY + solidArea.y; }

    public int getBottomY() { return worldY + solidArea.y + solidArea.height; }

    public int getCol() { return (worldX + solidArea.x) / gp.tileSize; }

    public int getRow() { return (worldY + solidArea.y) / gp.tileSize; }

    public int getCenterX(){

        int centerX = worldX + left1.getWidth()/2;
        return centerX;
    }

    public int getCenterY(){

        int centerY = worldX + up1.getWidth()/2;
        return centerY;
    }
    public int getXdistance(Entity target){
        int xDistance = Math.abs(getCenterX() - target.getCenterX());
        return xDistance;
    }

    public int getYdistance(Entity target){
        int yDistance = Math.abs(worldY - target.worldY);
        return yDistance;
    }

    public int getTileDistance(Entity target){
        int tileDistance = (getXdistance(target) + getYdistance(target) / gp.tileSize);
        return tileDistance;
    }

    public int getGoalCol(Entity target){
        int goalCol = (target.worldX + target.solidArea.x) / gp.tileSize;
        return goalCol;
    }

    public int getGoalRow(Entity target){
        int goalRow = (target.worldY + target.solidArea.y) / gp.tileSize;
        return goalRow;
    }

    public void setAction() {}

    public void move(String direction){}

    public void damageReaction() {}
    public void setLoot(Entity loot){}

    public void interact(){}
    public void facePlayer(){
        switch (gp.player.direction) {
            case "up": direction = "down"; break;
            case "down": direction = "up"; break;
            case "left": direction = "right"; break;
            case "right": direction = "left"; break;
        }
    }

    public boolean use(Entity entity) { return  false; }

    public void checkDrop() { }

    public void dropItem(Entity droppedItem) {
        for (int i = 0; i < gp.obj[1].length; i++) {
            if (gp.obj[gp.currentMap][i] == null) {
                gp.obj[gp.currentMap][i] = droppedItem;
                gp.obj[gp.currentMap][i].worldX = worldX;
                gp.obj[gp.currentMap][i].worldY = worldY;
                break;
            }
        }
    }
    public void speak() {

    }
    public void startDialogue(Entity entity, int setNum){
        gp.gameState = gp.dialogueState;
        gp.ui.npc = entity;
        dialogueSet = setNum;
    }

    public Color getParticleColor() {
        Color color = null;
        return color;
    }

    public int getParticleSize() {
        int size = 0;
        return size;
    }

    public int getParticleSpeed() {
        int speed = 0;
        return speed;
    }

    public int getParticleMaxLife() {
        int maxLife = 0;
        return maxLife;
    }

    public void generateParticle(Entity generator, Entity target) {
        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getParticleMaxLife();

        Particle p1 = new Particle(gp, target, color, size, speed, maxLife, -2, -1);
        Particle p2 = new Particle(gp, target, color, size, speed, maxLife, 2, -1);
        Particle p3 = new Particle(gp, target, color, size, speed, maxLife, -2, 1);
        Particle p4 = new Particle(gp, target, color, size, speed, maxLife, 2, 1);
        gp.particleList.add(p1);
        gp.particleList.add(p2);
        gp.particleList.add(p3);
        gp.particleList.add(p4);
    }

    public void checkCollision() {

        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);
        gp.cChecker.checkEntity(this, gp.iTile);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if (this.type == type_monster && contactPlayer) {
            damagePlayer(attack);
        }
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

        System.out.println(gp.player.worldX/gp.tileSize + " " + gp.player.worldY/gp.tileSize);
    }

    public void checkAttackOrNot(int rate, int straight, int horizontal){

        boolean targetInRange = false;
        int xDis = getXdistance(gp.player);
        int yDis = getYdistance(gp.player);

        switch(direction){
            case "up":
                if(gp.player.getCenterY() < getCenterY() && yDis < straight && xDis < horizontal){
                    targetInRange = true;
                }
                break;
            case "down":
                if(gp.player.getCenterY() > getCenterY() && yDis < straight && xDis < horizontal){
                    targetInRange = true;
                }
                break;
            case "left":
                if(gp.player.getCenterX() < getCenterX() && xDis < straight && yDis < horizontal){
                    targetInRange = true;
                }
                break;
            case "right":
                if(gp.player.getCenterY() > getCenterX() && xDis < straight && yDis < horizontal){
                    targetInRange = true;
                }
                break;
        }

        if(targetInRange){
            // Check if it initiates an attack
            int i = new Random().nextInt(rate);
            if(i == 0){
                attacking = true;
                spriteNum = 1;
                spriteCounter = 0;
                shotAvailableCounter = 0;
            }
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

    public void checkStartChasingOrNot(Entity target, int distance, int rate){

        if(getTileDistance(target) < distance){
            int i = new Random().nextInt(rate);
            if(i == 0){
                onPath = true;
            }
        }
    }

    public void checkStopChasingOrNot(Entity target, int distance, int rate){

        if(getTileDistance(target) > distance){
            int i = new Random().nextInt(rate);
            if(i == 0){
                onPath = false;
            }
        }
    }

    public void getRandomDirection(int interval){

        actionLockCounter++;

        if (actionLockCounter == interval) {

            Random random = new Random();
            int chance = random.nextInt(100) + 1;

            if (chance <= 25) { direction = "up"; }
            if (chance > 25 && chance <= 50) { direction = "down"; }
            if (chance > 50 && chance <= 75) { direction = "left"; }
            if (chance > 75 && chance <= 100) { direction = "right"; }
            actionLockCounter = 0;
        }
    }

    public void moveTowardPlayer(int interval){

        actionLockCounter++;
        if (actionLockCounter > interval) {
            if(getXdistance(gp.player) > getYdistance(gp.player)){
                if(gp.player.getCenterX() < getCenterX()){
                    direction = "left";
                } else {
                    direction = "right";
                }
            } else if(getXdistance(gp.player) < getYdistance(gp.player)){
                if(gp.player.getCenterY() > getCenterY()){
                    direction = "up";
                } else {
                    direction = "down";
                }
            }
            actionLockCounter++;
        }
    }

    public String getOppositeDirection(String direction){

        String oppositeDirection = "";

        switch(direction){
            case "up": oppositeDirection = "down"; break;
            case "down": oppositeDirection = "up"; break;
            case "left": oppositeDirection = "right"; break;
            case "right": oppositeDirection = "left"; break;
        }

        return oppositeDirection;
    }

    public void attacking() {
        spriteCounter++;

        if (spriteCounter <= motion1_duration) {
            spriteNum = 1;
        }
        if (spriteCounter > motion1_duration && spriteCounter <= motion2_duration) {
            spriteNum = 2;

            // SAVE CURRENT worldX/Y & solidArea
            int currentworldX = worldX;
            int currentworldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            // ADJUST PLAYER worldX/Y FOR attackArea
            switch (direction) {
                case "up": worldY -= attackArea.height; break;
                case "down": worldY += attackArea.height; break;
                case "left": worldX -= attackArea.width; break;
                case "right": worldX += attackArea.width; break;
            }

            // CHANGE attackArea INTO solidArea
            solidAreaWidth = attackArea.width;
            solidAreaHeight = attackArea.height;

            if(type == type_monster){
                if(gp.cChecker.checkPlayer(this)){
                    damagePlayer(attack);
                }
            } else { // Player
                // CHECK MONSTER COLLISION WITH THE UPDATED worldX/Y & solidArea
                int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
                gp.player.damageMonster(monsterIndex, this, attack, currentWeapon.knockBackPower);

                int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
                gp.player.damageInteractiveTile(iTileIndex);

                int projectileIndex = gp.cChecker.checkEntity(this, gp.projectile);
                gp.player.damageProjectile(projectileIndex);
            }



            // RESTORE THE ORIGINAL DATA
            worldX = currentworldX;
            worldY = currentworldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        if (spriteCounter > motion2_duration) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    public void damagePlayer(int attack) {
        if (!gp.player.invincible) {

            int damage = attack - gp.player.defense;

            // Get an opposite direction of this attacker
            String canGuardDirection = getOppositeDirection(direction);

            if(gp.player.guarding && gp.player.direction.equals(canGuardDirection)){

                // Parry
                if(gp.player.guardCounter < 10){
                    damage = 0;
                    gp.playSE(16);
                    setKnockBack(this, gp.player, knockBackPower);
                    offBalance = true;
                    spriteCounter =- 60;
                } else {
                    // Normal guard
                    damage /= 3;
                    gp.playSE(15);
                }
            } else {
                // Not guarding
                gp.playSE(6);
                if (damage < 1) {
                    damage = 1;
                }
            }

            if(damage != 0){
                gp.player.transparent = true;
                setKnockBack(gp.player, this, knockBackPower);
            }

            gp.player.life -= damage;
            gp.player.invincible = true;
        }
    }

    public void setKnockBack(Entity target, Entity attacker, int knockBackPower) {

        this.attacker = attacker;
        target.knockBackDirection = attacker.direction;
        target.speed += knockBackPower;
        target.knockBack = true;
    }



    public boolean inCamera(){
        boolean inCamera = false;

        if (worldX + gp.tileSize * 5 > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize * 5 > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
            inCamera = true;
        }
        return inCamera;
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (inCamera()) {

            int tempScreenX = getScreenX();
            int tempScreenY = getScreenY();

            switch (direction) {
                case "up":
                    if (!attacking) {
                        if (spriteNum == 1) { image = up1; }
                        if (spriteNum == 2) { image = up2; }
                    }
                    if (attacking) {
                        tempScreenY = getScreenY() - up1.getHeight();
                        if (spriteNum == 1) { image = attackUp1; }
                        if (spriteNum == 2) { image = attackUp2; }
                    }
                    break;
                case "down":
                    if (!attacking) {
                        if (spriteNum == 1) { image = down1; }
                        if (spriteNum == 2) { image = down2; }
                    }
                    if (attacking) {
                        if (spriteNum == 1) { image = attackDown1; }
                        if (spriteNum == 2) { image = attackDown2; }
                    }
                    break;
                case "left":
                    if (!attacking) {
                        if (spriteNum == 1) { image = left1; }
                        if (spriteNum == 2) { image = left2; }
                    }
                    if (attacking) {
                        tempScreenX = getScreenX() - left1.getWidth();
                        if (spriteNum == 1) { image = attackLeft1; }
                        if (spriteNum == 2) { image = attackLeft2; }
                    }
                    break;
                case "right":
                    if (!attacking) {
                        if (spriteNum == 1) { image = right1; }
                        if (spriteNum == 2) { image = right2; }
                    }
                    if (attacking) {
                        if (spriteNum == 1) { image = attackRight1; }
                        if (spriteNum == 2) { image = attackRight2; }
                    }
                    break;
            }

            if (invincible) {
                hpBarOn = true;
                hpBarCounter = 0;
                changeAlpha(g2, 0.4f);
            }

            if (dying) {
                dyingAnimation(g2);
            }

            g2.drawImage(image, tempScreenX, tempScreenY, null);

            changeAlpha(g2, 1f);
        }
        if(onPath && alive && !dying){
            gp.tileM.drawPath(g2);
        }
    }

    public void dyingAnimation(Graphics2D g2) {
        dyingCounter++;

        int i = 5;

        if (dyingCounter <= i) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i && dyingCounter <= i * 2) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i * 2 && dyingCounter <= i * 3) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i * 3 && dyingCounter <= i * 4) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i * 4 && dyingCounter <= i * 5) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i * 5 && dyingCounter <= i * 6) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i * 6 && dyingCounter <= i * 7) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i * 7 && dyingCounter <= i * 8) {
            changeAlpha(g2, 1f);
        }

        if (dyingCounter > i * 8) {
            alive = false;
        }
    }

    public void changeAlpha(Graphics2D g2, float alphaValue) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }

    public BufferedImage setup(String imagePath, int width, int height) {

        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {

            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, width, height);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void searchPath(int goalCol, int goalRow) {
        int startCol = (worldX + solidArea.x) / gp.tileSize;
        int startRow = (worldY + solidArea.y) / gp.tileSize;

        gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow);

        if (gp.pFinder.search()) {
            // NEXT WORLD X / Y
            int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
            int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;

            // ENTITY SOLID AREA POSITION
            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBottomY = worldY + solidArea.y + solidArea.height;

            if (enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "up";
            } else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "down";
            } else if (enTopY >= nextY && enBottomY < nextY + gp.tileSize) {
                // LEFT OR RIGHT
                if (enLeftX > nextX) {
                    direction = "left";
                }
                if (enLeftX < nextX) {
                    direction = "right";
                }
            } else if (enTopY > nextY && enLeftX > nextX) {
                // UP OR LEFT
                direction = "up";
                checkCollision();

                if (collisionOn) {
                    direction = "left";
                }
            } else if (enTopY > nextY && enLeftX < nextX) {
                // UP OR RIGHT
                direction = "up";
                checkCollision();

                if (collisionOn) {
                    direction = "right";
                }
            } else if (enTopY < nextY && enLeftX > nextX) {
                // DOWN OR LEFT
                direction = "down";
                checkCollision();

                if (collisionOn) {
                    direction = "left";
                }
            } else if (enTopY < nextY && enLeftX < nextX) {
                // DOWN OR RIGHT
                direction = "down";
                checkCollision();

                if (collisionOn) {
                    direction = "right";
                }
            }

            // IF REACH ThE GOAL, STOP THE SEARCH
            // int nextCol = gp.pFinder.pathList.get(0).col;
            // int nextRow = gp.pFinder.pathList.get(0).row;

            // if (nextCol == goalCol && nextRow == goalRow) {
            // onPath = false;
            // }
        }
    }

    public int getDetected(Entity user, Entity target[][], String targetName){
        int index = 999;

// Check the surrounding object
        int nextWorldX = user.getLeftX();
        int nextWorldY = user.getTopY();

        switch (user.direction) {
            case "up": nextWorldY = user.getTopY() - gp.player.speed; break;
            case "down": nextWorldY = user.getBottomY() + gp.player.speed; break;
            case "left": nextWorldX = user.getLeftX() - gp.player.speed; break;
            case "right": nextWorldX = user.getRightX() + gp.player.speed; break;
        }

        int col = nextWorldX / gp.tileSize;
        int row = nextWorldY / gp.tileSize;

        for (int i = 0; i < target[1].length; i++) {
            if (target[gp.currentMap][i] != null) {
                if (target[gp.currentMap][i].getCol() == col &&
                        target[gp.currentMap][i].getRow() == row &&
                        target[gp.currentMap][i].name.equals(targetName)) {
                    index = i;
                    break;
                }
            }
        }
        return index;
    }

    public int getSpeed() { return speed; }

    public void setSpeed(int speed) { this.speed = speed; }

    public int getDefaultSpeed() { return defaultSpeed;}

    public void setDefaultSpeed(int defaultSpeed) {this.defaultSpeed = defaultSpeed; }

    public int getMaxLife() { return maxLife; }

    public void setMaxLife(int maxLife) { this.maxLife = maxLife; }

    public int getLife() { return life; }

    public void setLife(int life) { this.life = life; }

    public int getMaxMana() { return maxMana; }

    public void setMaxMana(int maxMana) { this.maxMana = maxMana; }

    public int getMana() { return mana; }

    public void setMana(int mana) { this.mana = mana; }

    public int getAmmo() { return ammo; }

    public void setAmmo(int ammo) { this.ammo = ammo; }

    public int getLevel() { return level; }

    public void setLevel(int level) { this.level = level; }

    public int getDexterity() { return dexterity; }

    public void setDexterity(int dexterity) { this.dexterity = dexterity; }

    public int getStrength() { return strength; }

    public void setStrength(int strength) { this.strength = strength; }

    public int getAttack() { return attack; }

    public void setAttack(int attack) { this.attack = attack; }

    public int getDefense() { return defense; }

    public void setDefense(int defense) { this.defense = defense; }

    public int getExp() { return exp; }

    public void setExp(int exp) { this.exp = exp; }

    public int getNextLevelExp() { return nextLevelExp; }

    public void setNextLevelExp(int nextLevelExp) { this.nextLevelExp = nextLevelExp; }

    public int getCoin() { return coin; }

    public void setCoin(int coin) { this.coin = coin; }

    public Entity getCurrentWeapon() { return currentWeapon; }

    public void setCurrentWeapon(Entity currentWeapon) { this.currentWeapon = currentWeapon; }

    public int getValue() { return value; }

    public void setValue(int value) { this.value = value; }

    public Entity getCurrentShield() { return currentShield; }

    public void setCurrentShield(Entity currentShield) { this.currentShield = currentShield; }

    public Projectile getProjectile() { return projectile; }

    public void setProjectile(Projectile projectile) { this.projectile = projectile; }

    public ArrayList<Entity> getInventory() { return inventory; }

    public void setInventory(ArrayList<Entity> inventory) { this.inventory = inventory; }

    public int getMaxInventorySize() { return maxInventorySize; }

    public int getUseCost() { return useCost; }

    public void setUseCost(int useCost) { this.useCost = useCost; }

    public int getDefenseValue() { return defenseValue; }

    public void setDefenseValue(int defenseValue) { this.defenseValue = defenseValue; }

    public int getAttackValue() { return attackValue; }

    public void setAttackValue(int attackValue) { this.attackValue = attackValue; }

    public int getMotion1_duration() { return motion1_duration; }

    public void setMotion1_duration(int motion1_duration) { this.motion1_duration = motion1_duration; }

    public int getMotion2_duration() { return motion2_duration; }

    public void setMotion2_duration(int motion2_duration) { this.motion2_duration = motion2_duration; }
}
