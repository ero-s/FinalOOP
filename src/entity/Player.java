package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import main.GamePanel;
import main.KeyHandler;
import object.OBJ_Fireball;
import object.OBJ_Key;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;

public class Player extends Entity {
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    public boolean attackCanceled = false;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 12;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 24;
        solidArea.height = 30;

        setDefaultValues();
        getImage();
        getAttackImage();
        getGuardImage();
        setItems();
        setDialogue();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        gp.currentMap = 0;
        defaultSpeed = 4;
        speed = defaultSpeed;
        direction = "down";

        // PLAYER STATUS
        level = 1;
        maxLife = 6;
        life = maxLife;
        maxMana = 4;
        mana = maxMana;
        strength = 1;
        dexterity = 1;
        exp = 0;
        nextLevelExp = 5;
        coin = 800;
        currentWeapon = new OBJ_Sword_Normal(gp);
        currentShield = new OBJ_Shield_Wood(gp);
        projectile = new OBJ_Fireball(gp);
        attack = getAttack();
        defense = getDefense();
    }

    public void setDefaultPositions() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        direction = "down";
    }

    public void restoreLifeAndMana() {
        life = maxMana;
        mana = maxMana;
        invincible = false;
        transparent = false;
    }

    public void setItems() {
        inventory.clear();
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new OBJ_Key(gp));
    }

    public int getAttack() {
        attackArea = currentWeapon.attackArea;
        motion1_duration = currentWeapon.motion1_duration;
        motion2_duration = currentWeapon.motion2_duration;
        return attack = strength * currentWeapon.attackValue;
    }

    public int getCurrentWeaponSlot() {
        int currentWeaponSlot = 0;
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i) == getCurrentWeapon()) {
                currentWeaponSlot = i;
            }
        }

        return currentWeaponSlot;
    }

    public int getCurrentShieldSlot() {
        int currentShieldSlot = 0;
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i) == getCurrentShield()) {
                currentShieldSlot = i;
            }
        }

        return currentShieldSlot;
    }

    public int getDefense() { return defense = dexterity * currentShield.defenseValue; }

    public void getImage() {
        up1 = setup("/res/player/boy_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/res/player/boy_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/res/player/boy_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/res/player/boy_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/res/player/boy_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/res/player/boy_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("/res/player/boy_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/res/player/boy_right_2", gp.tileSize, gp.tileSize);

    }

    public void getAttackImage() {
        if (currentWeapon.type == type_sword) {
            attackUp1 = setup("/res/player/boy_attack_up_1", gp.tileSize, gp.tileSize * 2);
            attackUp2 = setup("/res/player/boy_attack_up_2", gp.tileSize, gp.tileSize * 2);
            attackDown1 = setup("/res/player/boy_attack_down_1", gp.tileSize, gp.tileSize * 2);
            attackDown2 = setup("/res/player/boy_attack_down_2", gp.tileSize, gp.tileSize * 2);
            attackLeft1 = setup("/res/player/boy_attack_left_1", gp.tileSize * 2, gp.tileSize);
            attackLeft2 = setup("/res/player/boy_attack_left_2", gp.tileSize * 2, gp.tileSize);
            attackRight1 = setup("/res/player/boy_attack_right_1", gp.tileSize * 2, gp.tileSize);
            attackRight2 = setup("/res/player/boy_attack_right_2", gp.tileSize * 2, gp.tileSize);
        }

        if (currentWeapon.type == type_axe) {
            attackUp1 = setup("/res/player/boy_axe_up_1", gp.tileSize, gp.tileSize * 2);
            attackUp2 = setup("/res/player/boy_axe_up_2", gp.tileSize, gp.tileSize * 2);
            attackDown1 = setup("/res/player/boy_axe_down_1", gp.tileSize, gp.tileSize * 2);
            attackDown2 = setup("/res/player/boy_axe_down_2", gp.tileSize, gp.tileSize * 2);
            attackLeft1 = setup("/res/player/boy_axe_left_1", gp.tileSize * 2, gp.tileSize);
            attackLeft2 = setup("/res/player/boy_axe_left_2", gp.tileSize * 2, gp.tileSize);
            attackRight1 = setup("/res/player/boy_axe_right_1", gp.tileSize * 2, gp.tileSize);
            attackRight2 = setup("/res/player/boy_axe_right_2", gp.tileSize * 2, gp.tileSize);
        }

        if (currentWeapon.type == type_pickaxe) {
            attackUp1 = setup("/res/player/boy_pick_up_1", gp.tileSize, gp.tileSize * 2);
            attackUp2 = setup("/res/player/boy_pick_up_2", gp.tileSize, gp.tileSize * 2);
            attackDown1 = setup("/res/player/boy_pick_down_1", gp.tileSize, gp.tileSize * 2);
            attackDown2 = setup("/res/player/boy_pick_down_2", gp.tileSize, gp.tileSize * 2);
            attackLeft1 = setup("/res/player/boy_pick_left_1", gp.tileSize * 2, gp.tileSize);
            attackLeft2 = setup("/res/player/boy_pick_left_2", gp.tileSize * 2, gp.tileSize);
            attackRight1 = setup("/res/player/boy_pick_right_1", gp.tileSize * 2, gp.tileSize);
            attackRight2 = setup("/res/player/boy_pick_right_2", gp.tileSize * 2, gp.tileSize);
        }
    }

    public void getGuardImage(){
        guardUp = setup("/res/player/boy_guard_up", gp.tileSize, gp.tileSize);
        guardDown = setup("/res/player/boy_guard_down", gp.tileSize, gp.tileSize);
        guardLeft = setup("/res/player/boy_guard_left", gp.tileSize, gp.tileSize);
        guardRight = setup("/res/player/boy_guard_right", gp.tileSize, gp.tileSize);
    }

    public void update() {

        if (knockBack) {

            collisionOn = false;
            gp.cChecker.checkTile(this);
            gp.cChecker.checkObject(this, true);
            gp.cChecker.checkEntity(this, gp.npc);
            gp.cChecker.checkEntity(this, gp.monster);
            gp.cChecker.checkEntity(this, gp.iTile);

            if (collisionOn) {
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            } else if (!collisionOn) {spriteCounter++;
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
        } else if (attacking) {
            attacking();
        } else if(keyH.spacePressed){
            System.out.println("WorldX: "+worldX/gp.tileSize+" WorldY: "+worldY/gp.tileSize  );
            guarding = true;
            guardCounter++;
        } else if (keyH.upPressed || keyH.downPressed || keyH.leftPressed ||
                keyH.rightPressed || keyH.enterPressed) {

            if (keyH.upPressed) { direction = "up"; }
            else if (keyH.downPressed) { direction = "down"; }
            else if (keyH.leftPressed) { direction = "left"; }
            else if (keyH.rightPressed) { direction = "right"; }

            // CHECK TILE COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // CHECK OBJECT COLLISION
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(gp.currentMap, objIndex);

            // CHECK NPC COLLISION
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            // CHECK MONSTER COLLISION
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);

            // CHECK INTERACTIVE TILE COLLISION
            int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);

            // CHECK EVENT
            gp.eHandler.checkEvent();

            // IF COLLISION IS FALSE, PLAYER CAN MOVE
            if (!collisionOn && !keyH.enterPressed) {
                switch (direction) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }

            if (keyH.enterPressed && !attackCanceled) {
                gp.playSE(7);
                attacking = true;
                spriteCounter = 0;
            }

            attackCanceled = false;
            gp.keyH.enterPressed = false;
            guarding = false;
            guardCounter = 0;

            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
        else {
//            standCounter++;
//            if(standCounter == 20){
//                spriteNum = 1;
//                standcounter = 0;
//            }
            guarding = false;
            guardCounter = 0;
        }

        if (gp.keyH.shotKeyPressed && !projectile.alive && shotAvailableCounter == 30
                && projectile.haveResource(this)) {
            projectile.set(worldX, worldY, direction, true, this);

            projectile.subtractResource(this);

            // CHECK VACANCY
            for (int i = 0; i < gp.projectile[1].length; i++) {
                if (gp.projectile[gp.currentMap][i] == null) {
                    gp.projectile[gp.currentMap][i] = projectile;
                    break;
                }
            }

            shotAvailableCounter = 0;
            gp.playSE(10);
        }

        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                transparent = false;
                invincibleCounter = 0;
            }
        }

        if (shotAvailableCounter < 30) {
            shotAvailableCounter++;
        }

        if (life > maxLife) {
            life = maxLife;
        }

        if (mana > maxMana) {
            mana = maxMana;
        }

        if (life <= 0) {
            gp.gameState = gp.gameOverState;
            gp.ui.commandNum = -1;
            gp.stopMusic();
            gp.playSE(12);
        }
    }

    public void pickUpObject(int mapNum, int i) {
        if (i != 999) {

            // PICKUP ONLY ITEMS
            if (gp.obj[mapNum][i].type == type_pickupOnly) {
                gp.obj[mapNum][i].use(this);
                gp.obj[mapNum][i] = null;
            }
            else if(gp.obj[mapNum][i].type == type_obstacle){
                if(keyH.enterPressed){
                    attackCanceled = true;
                    gp.obj[mapNum][i].interact();
                }
            }
            else {
                // INVENTORY ITEMS
                String text;
                if (canObtainItem(gp.obj[mapNum][i])){
                    gp.playSE(1);
                    text = "You got a " + gp.obj[mapNum][i].name + "!";
                } else {
                    text = "You cannot carry any more";
                }
                gp.ui.addMessage(text);
                gp.obj[mapNum][i] = null;
            }
        }
    }

    public void interactNPC(int i) {

        if (i != 999) {
            if (gp.keyH.enterPressed){
                attackCanceled = true;
                gp.gameState = gp.dialogueState;
                gp.npc[gp.currentMap][i].speak();
            }
            gp.npc[gp.currentMap][i].move(direction);
        }
    }

    public void contactMonster(int i) {
        if (i != 999) {
            if (!invincible && !gp.monster[gp.currentMap][i].dying) {
                gp.playSE(6);

                int damage = gp.monster[gp.currentMap][i].attack - defense;
                if (damage < 1) { damage = 1; }

                life -= damage;
                invincible = true;
                transparent = true;
            }
        }
    }

    public void damageMonster(int i, Entity attacker, int attack, int knockBackPower) {
        if (i != 999) {
            if (!gp.monster[gp.currentMap][i].invincible) {
                gp.playSE(5);

                if (knockBackPower > 0) { setKnockBack(gp.monster[gp.currentMap][i] , attacker, knockBackPower); }

                if(gp.monster[gp.currentMap][i].offBalance){ attack *= 3; }

                int damage = attack - gp.monster[gp.currentMap][i].defense;
                if (damage < 0) { damage = 0; }

                gp.monster[gp.currentMap][i].life -= damage;
                gp.ui.addMessage(damage + " damage!");
                gp.monster[gp.currentMap][i].invincible = true;
                gp.monster[gp.currentMap][i].damageReaction();

                if (gp.monster[gp.currentMap][i].life <= 0) {
                    gp.monster[gp.currentMap][i].dying = true;
                    gp.ui.addMessage("Killed the " + gp.monster[gp.currentMap][i].name + "!");
                    gp.ui.addMessage("Exp + " + gp.monster[gp.currentMap][i].exp);
                    exp += gp.monster[gp.currentMap][i].exp;
                    checkLevelUp();
                }
            }
        }
    }

    public void damageInteractiveTile(int i) {
        if (i != 999 && gp.iTile[gp.currentMap][i].destructible
                && gp.iTile[gp.currentMap][i].isCorrectItem(this)
                && !gp.iTile[gp.currentMap][i].invincible) {
            gp.iTile[gp.currentMap][i].playSe();
            gp.iTile[gp.currentMap][i].life--;
            gp.iTile[gp.currentMap][i].invincible = true;

            generateParticle(gp.iTile[gp.currentMap][i], gp.iTile[gp.currentMap][i]);

            if (gp.iTile[gp.currentMap][i].life == 0) {
//                gp.iTile[gp.currentMap][i].checkDrop();
                gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestroyedForm();
            }
        }
    }

    public void damageProjectile(int i) {
        if (i != 999) {
            Entity projectile = gp.projectile[gp.currentMap][i];
            projectile.alive = false;
            generateParticle(projectile, projectile);
        }
    }

    public void checkLevelUp() {
        if (exp >= nextLevelExp) {
            level++;
            nextLevelExp *= 2;
            maxLife += 2;
            strength++;
            dexterity++;
            attack = getAttack();
            defense = getDefense();
            gp.playSE(8);
            gp.gameState = gp.dialogueState;

            dialogues[0][0] = "You are level " + level + " now!\n" + "You feel stronger!";
            setDialogue();

            startDialogue(this, 0);
        }
    }
    public void setDialogue(){

    }

    public void selectItem() {
        int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol, gp.ui.playerSlotRow);

        if (itemIndex < inventory.size()) {
            Entity selectedItem = inventory.get(itemIndex);

            if (selectedItem.type == type_sword || selectedItem.type == type_axe || selectedItem.type == type_pickaxe) {
                currentWeapon = selectedItem;
                attack = getAttack();
                getAttackImage();
            }

            if (selectedItem.type == type_shield) {
                currentShield = selectedItem;
                defense = getDefense();
            }

            if (selectedItem.type == type_consumable) {
                if(selectedItem.use(this)){
                    if(selectedItem.amount > 1) {
                        selectedItem.amount--;
                    } else {
                        inventory.remove(itemIndex);
                    }
                }

            }
        }
    }
    public boolean canObtainItem(Entity item){

        boolean canObtain = false;

        Entity newItem = gp.eGenerator.getObject(item.name);

        // CHECK IF STACKABLE
        if(newItem.stackable){

            int index = searchItemInInventory(newItem.name);

            if(index != 999){
                inventory.get(index).amount++;
                canObtain = true;
            } else { // New item so need to check vacancy
                if(inventory.size() != maxInventorySize){
                    inventory.add(newItem);
                    canObtain = true;
                }
            }
        } else { // NOT STACKABLE so check vacancy
            if(inventory.size() != maxInventorySize){
                inventory.add(newItem);
                canObtain = true;
            }
        }
        return canObtain;
    }
    public int searchItemInInventory(String itemName){

        int itemIndex = 999;

        for(int i = 0; i < inventory.size(); i++){
            if(inventory.get(i).name.equals(itemName)){
                itemIndex = i;
                break;
            }
        }
        return itemIndex;
    }
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch (direction) {
            case "up":
                if (!attacking) {
                    if (spriteNum == 1) { image = up1; }
                    if (spriteNum == 2) { image = up2; }
                }
                if (attacking) {
                    tempScreenY = screenY - gp.tileSize;
                    if (spriteNum == 1) { image = attackUp1; }
                    if (spriteNum == 2) { image = attackUp2; }
                }
                if(guarding) { image = guardUp; }
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
                if(guarding) { image = guardDown; }
                break;
            case "left":
                if (!attacking) {
                    if (spriteNum == 1) { image = left1; }
                    if (spriteNum == 2) { image = left2; }
                }
                if (attacking) {
                    tempScreenX = screenX - gp.tileSize;
                    if (spriteNum == 1) { image = attackLeft1; }
                    if (spriteNum == 2) { image = attackLeft2; }
                }
                if(guarding) { image = guardLeft; }
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
                if(guarding) { image = guardRight; }
                break;
        }

        if (transparent) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }

        g2.drawImage(image, tempScreenX, tempScreenY, null);

        // RESET
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}