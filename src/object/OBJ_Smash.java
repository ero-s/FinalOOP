package object;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

import java.awt.*;

public class OBJ_Smash extends Projectile {
    //TODO improve graphics
    GamePanel gp;
    Entity user;

    public static final String objName = "Smash";

    public OBJ_Smash(GamePanel gp) {
        super(gp);

        this.gp = gp;

        name = objName;
        speed = 0;
        maxLife = 60;
        life = maxLife;
        attack = 5;
        knockBackPower = 5;
        useCost = 2;
        alive = false;
        //offset of projectile
        xOffset = (gp.tileSize * 2 - gp.tileSize) / 2;
        yOffset = gp.tileSize/ 2 + 64;


        solidArea = new Rectangle();
        solidArea.x = -64;
        solidArea.y = -32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 256;
        solidArea.height = 256;
        getImage();
    }

    public void setUser(Entity user){
        this.user = user;

    }


    public boolean haveResource(Entity user) {
        boolean haveResource = false;

        if (user.mana >= useCost) {
            haveResource = true;
        }

        return haveResource;
    }

    public void update() {
        if (skillDurationCounter >= 60) {
            alive = false;
            skillDurationCounter = 0;
        }

        if (user == gp.player) {
            // Adjust the solid area to match the current world position
            solidArea.x = worldX + solidAreaDefaultX;
            solidArea.y = worldY + solidAreaDefaultY;

            // Check all monsters in the current map
            for (int i = 0; i < gp.monster[gp.currentMap].length; i++) {
                Entity monster = gp.monster[gp.currentMap][i];
                if (monster != null && monster.alive) {
                    // Check if the monster is within the projectile's solidArea
                    Rectangle monsterArea = new Rectangle(
                            monster.worldX + monster.solidArea.x,
                            monster.worldY + monster.solidArea.y,
                            monster.solidArea.width,
                            monster.solidArea.height
                    );
                    if (solidArea.intersects(monsterArea)) {
                        gp.player.damageMonster(i, this, attack * (gp.player.level / 2), knockBackPower);
                    }
                }
            }

            // Reset solidArea position to avoid affecting other calculations
            solidArea.x = solidAreaDefaultX;
            solidArea.y = solidAreaDefaultY;
        }

        if (user != gp.player) {
            // Adjust the solid area for the projectile
            solidArea.x = worldX + solidAreaDefaultX;
            solidArea.y = worldY + solidAreaDefaultY;
            // Check if the projectile hits the player
            Rectangle playerArea = new Rectangle(
                    gp.player.worldX + gp.player.solidArea.x,
                    gp.player.worldY + gp.player.solidArea.y,
                    gp.player.solidArea.width,
                    gp.player.solidArea.height
            );

            if (!gp.player.invincible && solidArea.intersects(playerArea)) {
                damagePlayer(attack);
                generateParticle(user.projectile, user.projectile);
            }
            // Reset solidArea position
            solidArea.x = solidAreaDefaultX;
            solidArea.y = solidAreaDefaultY;
        }

        life--;
        if (life <= 0) {
            alive = false;
        }

        spriteCounter++;
        if (spriteCounter > 12) {
            spriteNum = (spriteNum == 1) ? 2 : 1;
            spriteCounter = 0;
        }

        skillDurationCounter++;
    }

    public void getImage(){
        up1 = setup("/res/objects/smash", gp.tileSize*4, gp.tileSize*4);
        up2 = setup("/res/objects/smash", gp.tileSize*4, gp.tileSize*4);
        right1 = setup("/res/objects/smash", gp.tileSize*4, gp.tileSize*4);
        right2 = setup("/res/objects/smash", gp.tileSize*4, gp.tileSize*4);
        down1 = setup("/res/objects/smash", gp.tileSize*4, gp.tileSize*4);
        down2 = setup("/res/objects/smash", gp.tileSize*4, gp.tileSize*4);
        left1 = setup("/res/objects/smash", gp.tileSize*4, gp.tileSize*4);
        left2 = setup("/res/objects/smash", gp.tileSize*4, gp.tileSize*4);
    }

    public void subtractResource(Entity user) {
        user.mana -= useCost;
    }

    public Color getParticleColor() {
        Color color = new Color(103, 86, 83);

        return color;
    }

    public int getParticleSize() {
        int size = 12;

        return size;
    }

    public int getParticleSpeed() {
        int speed = 1;

        return speed;
    }

    public int getParticleMaxLife() {
        int maxLife = 20;

        return maxLife;
    }
}
