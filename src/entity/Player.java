package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.KeyHandler;
import object.*;
import object.GeneralSkills.OBJ_Smash;
import object.Skills_PK.PR_SludgeBomb;

public class    Player extends Entity {
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    private int manaRegenCounter = 0;
    public boolean attackCanceled = false;
    public int currentDialogueSet;
    public boolean lightUpdated = false;
    int standCounter;
    int manaRegen;
    int hpRegen;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;
        direction = "down";

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 32;
        solidArea.y = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 16;

        mapCollision = new Rectangle();
        mapCollision.x = 0;
        mapCollision.y = 0;
        mapCollisionDefaultX = mapCollision.x;
        mapCollisionDefaultY = mapCollision.y;
        mapCollision.width = 32;
        mapCollision.height = 32;

        setDefaultValues();

    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 45;
        worldY = gp.tileSize * 40;
        gp.currentMap = 0;
        defaultSpeed = 8;
        speed = defaultSpeed;
        direction = "down";

        // PLAYER STATUS
        level = 1;
        maxLife = 20;
        maxMana = 5;
        life = maxLife;
        mana = maxMana;
        strength = 5;
        dexterity = 1;
        exp = 0;
        nextLevelExp = 5;
        coin = 800;
        currentDialogueSet = 1;
        currentWeapon = new OBJ_Sword_Normal(gp);
        currentShield = new OBJ_Shield_Wood(gp);
        currentLight = null;
        projectile = new PR_SludgeBomb(gp);
        skill1 = new OBJ_Smash(gp);
        skill1.setUser(this);
        attack = getAttack();
        defense = getDefense();

        getImage();
        getAttackImage();
        getGuardImage();
        setItems();
        setDialogue();
    }

    public void setDefaultPositions() {
        gp.currentMap = 0;
        if(gp.currentMap == 0 || gp.currentMap == 4){
            gp.currentMap = 0;
            worldX = gp.tileSize * 46;
            worldY = gp.tileSize * 40;
        }

        if(gp.currentMap == 1 || gp.currentMap == 5){
            gp.currentMap = 1;
            worldX = gp.tileSize *  45;
            worldY = gp.tileSize * 15;
        }

        direction = "down";
    }

    public void restoreStatus() {
        life = maxMana;
        mana = maxMana;
        invincible = false;
        transparent = false;
        attacking = false;
        guarding = false;
        lightUpdated = true;
        knockBack = false;
    }

    public void setItems() {
        inventory.clear();
        inventory.add(currentWeapon);
        inventory.add(currentShield);
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
        up1 = setup("/res/player/hakobe/Walk back-1", gp.tileSize*2, gp.tileSize*2);
        up2 = setup("/res/player/hakobe/Walk back-2", gp.tileSize*2, gp.tileSize*2);
        down1 = setup("/res/player/hakobe/Walk front-1", gp.tileSize*2, gp.tileSize*2);
        down2 = setup("/res/player/hakobe/Walk front-2", gp.tileSize*2, gp.tileSize*2);
        left1 = setup("/res/player/hakobe/Walk Left-1", gp.tileSize*2, gp.tileSize*2);
        left2 = setup("/res/player/hakobe/Walk Left-2", gp.tileSize*2, gp.tileSize*2);
        right1 = setup("/res/player/hakobe/Walk Right-1", gp.tileSize*2, gp.tileSize*2);
        right2 = setup("/res/player/hakobe/Walk Right-2", gp.tileSize*2, gp.tileSize*2);

    }

    public void getSleepingImage(BufferedImage image) {
        up1 = image;
        up2 = image;
        down1 = image;
        down2 = image;
        left1 = image;
        left2 = image;
        right1 = image;
        right2 = image;

    }

    public void getAttackImage() {
        if (currentWeapon.type == type_sword) {
            attackUp1 = setup("/res/player/hakobe/up1", gp.tileSize*2, gp.tileSize * 4);
            attackUp2 = setup("/res/player/hakobe/up2", gp.tileSize*2, gp.tileSize * 4);
            attackDown1 = setup("/res/player/hakobe/down1", gp.tileSize*2, gp.tileSize * 4);
            attackDown2 = setup("/res/player/hakobe/down2", gp.tileSize*2, gp.tileSize * 4);
            attackLeft1 = setup("/res/player/hakobe/left1", gp.tileSize * 4, gp.tileSize*2);
            attackLeft2 = setup("/res/player/hakobe/left2", gp.tileSize * 4, gp.tileSize*2);
            attackRight1 = setup("/res/player/hakobe/right1", gp.tileSize * 4, gp.tileSize*2);
            attackRight2 = setup("/res/player/hakobe/right2", gp.tileSize * 4, gp.tileSize*2);
        }

        if (currentWeapon.type == type_axe) {
            attackUp1 = setup("/res/player/hakobe/up1", gp.tileSize*2, gp.tileSize * 4);
            attackUp2 = setup("/res/player/hakobe/up2", gp.tileSize*2, gp.tileSize * 4);
            attackDown1 = setup("/res/player/hakobe/down1", gp.tileSize*2, gp.tileSize * 4);
            attackDown2 = setup("/res/player/hakobe/down2", gp.tileSize*2, gp.tileSize * 4);
            attackLeft1 = setup("/res/player/hakobe/left1", gp.tileSize * 4, gp.tileSize*2);
            attackLeft2 = setup("/res/player/hakobe/left2", gp.tileSize * 4, gp.tileSize*2);
            attackRight1 = setup("/res/player/hakobe/right1", gp.tileSize * 4, gp.tileSize*2);
            attackRight2 = setup("/res/player/hakobe/right2", gp.tileSize * 4, gp.tileSize*2);
        }

        if (currentWeapon.type == type_pickaxe) {
            attackUp1 = setup("/res/player/hakobe/up1", gp.tileSize*2, gp.tileSize * 4);
            attackUp2 = setup("/res/player/hakobe/up2", gp.tileSize*2, gp.tileSize * 4);
            attackDown1 = setup("/res/player/hakobe/down1", gp.tileSize*2, gp.tileSize * 4);
            attackDown2 = setup("/res/player/hakobe/down2", gp.tileSize*2, gp.tileSize * 4);
            attackLeft1 = setup("/res/player/hakobe/left1", gp.tileSize * 4, gp.tileSize*2);
            attackLeft2 = setup("/res/player/hakobe/left2", gp.tileSize * 4, gp.tileSize*2);
            attackRight1 = setup("/res/player/hakobe/right1", gp.tileSize * 4, gp.tileSize*2);
            attackRight2 = setup("/res/player/hakobe/right2", gp.tileSize * 4, gp.tileSize*2);
        }
    }

    public void getGuardImage(){
        guardUp = setup("/res/player/hakobe/up1", gp.tileSize*2, gp.tileSize*4);
        guardDown = setup("/res/player/hakobe/down2", gp.tileSize*2, gp.tileSize*4);
        guardLeft = setup("/res/player/hakobe/left2", gp.tileSize*4, gp.tileSize*2);
        guardRight = setup("/res/player/hakobe/right2", gp.tileSize*4, gp.tileSize*2);
    }

    public void update() {
        if(manaRegenCounter >= 600){
            mana++;
            manaRegenCounter = 0;
        }
        manaRegenCounter++;

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
            standCounter++;

            if(standCounter == 20){
                spriteNum = 1;
                standCounter = 0;
            }

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

        if (gp.keyH.skill1Pressed && !skill1.alive && shotAvailableCounter == 30 && skill1.haveResource(this)) {
            skill1.set(worldX, worldY, direction, true, this);
            skill1.subtractResource(this);

            // CHECK VACANCY
            for (int i = 0; i < gp.projectile[1].length; i++) {
                if (gp.projectile[gp.currentMap][i] == null) {
                    gp.projectile[gp.currentMap][i] = skill1;
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

        if(hpRegen == 300){
            life++;
            hpRegen = 0;
        }

        if(manaRegen == 180){
            mana++;
            manaRegen = 0;
        }

        hpRegen++;
        manaRegen++;

        if (getLife() > getMaxLife()) {
            setLife(getMaxLife());
        }

        if (getMana() > getMaxMana()) {
            setMana(getMaxMana());
        }

        if(!keyH.godModeOn) {
            if (life <= 0) {
                gp.gameState = gp.gameOverState;
                gp.ui.commandNum = 0;
                gp.stopMusic();
                gp.playSE(12);
            }
        }

        System.out.println(worldX/gp.tileSize + ", " + worldY/gp.tileSize);
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
//        dialogues[1][0] = "At home, the brothers overhear their parents \narguing before their separation breaks ";
//        dialogues[1][1] = "them apart — Hakobe moves to the city, while\n Andres stays on the farm.";
//        dialogues[1][2] = " As their bond fades, Hakobe hears rumors \nof disappearances in Bukidgrown. ";
//        dialogues[1][3] = "After learning of a massacre in his \nhometown, he returns, sneaks past police,";
//        dialogues[1][4] = " and discovers a dungeon gate where he\n and Andres once played. Then Hakobe entered.";
//        dialogues[1][5] = "Ahead stands a castle, brimming with greed,\na selfish king awaits";

        dialogues[2][0] = "Pickle Rick! Your end has come! \nI bring the countless cries of your people";
        dialogues[2][1] = "Your tyranny end today!.";

        dialogues[3][0] = "Pickle Rick grew up in poverty which made him\n develop a strong obsession with money and power,";
        dialogues[3][1] = "he witnessed the cruelty of the world, where\n those who are without wealth \nor power were mistreated.";
        dialogues[3][2] = "This then sowed the seeds of in the future \nhe wanted to be the one who has power\n over everything and makes people do his bidding.";
        dialogues[3][3] = "He then fell in love, but the woman he \nloved was taken away and became a slave;";
        dialogues[3][4] = "he became more powerless because \nhe was incapable of buying her.";
        dialogues[3][5] = "He worked tirelessly desperately trying \nto earn money, but she was murdered one day.";
        dialogues[3][6] = "This led Pickle Rick to bear more anger in\n the world which made him \nstart enslaving people";
        dialogues[3][7] = "and letting them work tirelessly like he did.";
        dialogues[3][8] = "He was mad at the world that\n he made others suffer like he did.";
        dialogues[3][9] = "North of where summer thrives, a town of scares lies.";
        dialogues[3][10] = "A being of immense power lays dormant, ruling with fear";
        dialogues[3][11] = "Jack O' Lantern waits for your arrival";

        dialogues[4][0] = "Hakobe and Pugtato reach the Fall Circus, on a mission to find the ringmaster";
        dialogues[4][0] = "Hakobe and Pugtato reach the Fall Circus, on a mission to find the ringmaster";



        dialogues[6][0] = "Jac was once known as a gifted performer,\n a child prodigy. Jac was once known";
        dialogues[6][1] = "as a gifted performer, a child prodigy, \nand his family's pride and joy. As he grew";
        dialogues[6][2] = "older, Jac’s parents showered him with\n expectations but rarely offered affection";
        dialogues[6][3] = "or support. Instead of nurturing his talents,\n they demanded perfection, driving";
        dialogues[6][4] = "him to practice relentlessly. \nTheir indifference cut deeply, fostering a growing ";
        dialogues[6][5] = "resentment within him.\n Jac felt like a mere prop in their show, overshadowed by";
        dialogues[6][6] = "their brilliance and neglect. \nYears later he became the best ever performer there ";
        dialogues[6][7] = "ever was but was blinded by his ideals\n which led to him enslaving people and ";
        dialogues[6][8] = "making them suffer. This sadistic behavior\n was a boost to his ego, and this ";
        dialogues[6][9] = "eventually started this cruel rule on the circus. ";


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
            if(selectedItem.type == type_light){
                if(currentLight == selectedItem){
                    currentLight = null;
                }
                else{
                    currentLight = selectedItem;
                }
                lightUpdated = true;
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
    public boolean canObtainItem(Entity item) {
        boolean canObtain = false;
        Entity newItem = gp.eGenerator.getObject(item.name);

        // CHECK IF STACKABLE
        if (newItem.stackable) {
            int index = searchItemInInventory(newItem.name);
            if (index != 999) {
                inventory.get(index).amount++;
                canObtain = true;
            } else { // New item so need to check vacancy
                if (inventory.size() != maxInventorySize) {
                    inventory.add(newItem);
                    canObtain = true;
                }
            }
        } else { // NOT STACKABLE so check vacancy
            if (inventory.size() != maxInventorySize) {
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
        g2.setColor(Color.red);
        int collisionBoxX = screenX + this.solidArea.x;
        int collisionBoxY = screenY + this.solidArea.y;
        g2.drawRect(collisionBoxX, collisionBoxY, this.solidArea.width, this.solidArea.height);

        // RESET
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}