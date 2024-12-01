package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.Jill_Skills.JILL_IceSlicer;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;
import object.OBJ_Rock;

public class  MON_Jill extends Entity {

    GamePanel gp;
    private int skill1Counter;
    private int skill2Counter;
    public static final String monName = "Jill";

    public MON_Jill(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = type_monster;
        boss = true;
        name = monName;
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 100;
        life = maxLife;
        attack = 10;
        defense = 0;
        exp = 2;
        //projectile = new OBJ_Rock(gp);
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

    // void getAttackImage() -> done
    // void update() -> done
    // shoot projectile method
    // acid splash == frost wave
    //

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

    public void getImage() {
        int i = 5;
        up1 = setup("/res/monster/jill/up1", gp.tileSize*i, gp.tileSize * i);
        up2 = setup("/res/monster/jill/up2", gp.tileSize*i, gp.tileSize * i);
        down1 = setup("/res/monster/jill/down1", gp.tileSize*i, gp.tileSize * i);
        down2 = setup("/res/monster/jill/down2", gp.tileSize*i, gp.tileSize * i);
        left1 = setup("/res/monster/jill/left1", gp.tileSize*i, gp.tileSize * i);
        left2 = setup("/res/monster/jill/left2", gp.tileSize*i, gp.tileSize * i);
        right1 = setup("/res/monster/jill/right1", gp.tileSize*i, gp.tileSize * i);
        right2 = setup("/res/monster/jill/right2", gp.tileSize*i, gp.tileSize * i);
    }

    // could be changed
    public void getAttackImage() {
        int i = 5;
        up1 = setup("/res/monster/jill/up1", gp.tileSize*i, gp.tileSize * i);
        up2 = setup("/res/monster/jill/up2", gp.tileSize*i, gp.tileSize * i);
        down1 = setup("/res/monster/jill/down1", gp.tileSize*i, gp.tileSize * i);
        down2 = setup("/res/monster/jill/down2", gp.tileSize*i, gp.tileSize * i);
        left1 = setup("/res/monster/jill/left1", gp.tileSize*i, gp.tileSize * i);
        left2 = setup("/res/monster/jill/left2", gp.tileSize*i, gp.tileSize * i);
        right1 = setup("/res/monster/jill/right1", gp.tileSize*i, gp.tileSize * i);
        right2 = setup("/res/monster/jill/right2", gp.tileSize*i, gp.tileSize * i);
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

        // scene2 in fall circus
        dialogues[0][0] = "Who dares to enter our sanctuary.";
        dialogues[0][1] = "The villagers are not our concern. We protect what is ours.";

        // scene3 in fall circus
        dialogues[1][0] = "Jack, we must give up our sanctuary. I can't see you get hurt again.";
        dialogues[1][1] = "The spring has more than enough for all of us if we work together.";
    }

//    public void iceArrow() {
//        if((shotAvailableCounter >= 30) && projectile.haveResource(this)) {
//            projectile.set(worldX, worldY, direction, true, this);
//        }
//
//        for (int i = 0; i < gp.projectile[1].length; i++) {
//            if(gp.projectile[gp.currentMap][i] == null) {
//                gp.projectile[gp.currentMap][i] = projectile;
//            }
//            break;
//        }
//        shotAvailableCounter = 0;
//    }

    public void iceSlicer() {
        if((shotAvailableCounter >= 40) && projectile.haveResource(this)) {
            projectile.set(worldX, worldY, direction, true, this);
        }

        for (int i = 0; i < gp.projectile[1].length; i++) {
            if(gp.projectile[gp.currentMap][i] == null) {
                gp.projectile[gp.currentMap][i] = projectile;
            }
            break;
        }
        shotAvailableCounter = 0;
    }

    public void setAction() {

        if (getTileDistance(gp.player) < 10) {
            moveTowardPlayer(20);

            iceSlicer();
            
//            if (skill1Counter == 3600) {
//                iceArrow();
//            }

//            if (projectileCounter == 4500) {
//                iceSlicer();
//            }

        } else {
            if (new Random().nextInt(0, 100)+1 < 40) { // 30% chance to shoot
//                if(!skill1.alive){
//                    iceArrow();
//                }

                if(!projectile.alive) {
                    iceSlicer();
                }
                //iceSlicer();

            }
        }

        if (!attacking) {
            checkAttackOrNot(60, gp.tileSize * 7, gp.tileSize * 5);
        }
        skill2Counter++;


        if (onPath) {

             //Search the direction to go
            searchPath(this,getGoalCol(gp.player), getGoalRow(gp.player));

             //Check if it shoots a projectile
            checkShootOrNot(60, 30);
            iceSlicer();

        } else {
            // Check if it starts chasing
            checkStartChasingOrNot(gp.player, 15, 100);

            // Get a random direction
            getRandomDirection(120);
            iceSlicer();
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
