package object;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

import java.awt.*;

public class OBJ_Smash extends Projectile {
    GamePanel gp;
    Entity user;
    public static final String objName = "Smash";

    public OBJ_Smash(GamePanel gp) {
        super(gp);

        this.gp = gp;

        name = objName;
        speed = 0;
        maxLife = 150;
        life = maxLife;
        attack = 5;
        knockBackPower = 5;
        useCost = 2;
        alive = false;
        xOffset = gp.tileSize/ 2;
        yOffset = -gp.tileSize * 3/2;
//        yOffset = gp.tileSize* 3/2;

        solidArea = new Rectangle();
        solidArea.x = -64;
        solidArea.y = -64;
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

    public void update(){
        if(skillDurationCounter >= 150){
            alive = false;
            skillDurationCounter = 0;
        }
        if (user == gp.player) {
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);

            if (monsterIndex != 999) {
                gp.player.damageMonster(monsterIndex, this, attack*(gp.player.level/2), knockBackPower);
                generateParticle(user.projectile, gp.monster[gp.currentMap][monsterIndex]);
            }
        }

        if (user != gp.player) {
            boolean contactPlayer = gp.cChecker.checkPlayer(this);

            if (!gp.player.invincible && contactPlayer) {
                damagePlayer(attack);
                generateParticle(user.projectile, user.projectile);
            }
        }

        switch (direction) {
            case "up": worldY -= speed; break;
            case "down": worldY += speed; break;
            case "left": worldX -= speed; break;
            case "right": worldX += speed; break;
        }

        life--;
        if (life <= 0) {
            alive = false;
        }

        spriteCounter++;
        if (spriteCounter > 12) {
            if (spriteNum == 1) { spriteNum = 2; }
            else if (spriteNum == 2) { spriteNum = 1; }
            spriteCounter = 0;
        }
        skillDurationCounter++;
    }

    public void getImage(){
        up1 = setup("/res/projectile/pickleRick/up1", gp.tileSize*4, gp.tileSize*4);
        up2 = setup("/res/projectile/pickleRick/up2", gp.tileSize*4, gp.tileSize*4);
        right1 = setup("/res/projectile/pickleRick/right1", gp.tileSize*4, gp.tileSize*4);
        right2 = setup("/res/projectile/pickleRick/right2", gp.tileSize*4, gp.tileSize*4);
        down1 = setup("/res/projectile/pickleRick/down1", gp.tileSize*4, gp.tileSize*4);
        down2 = setup("/res/projectile/pickleRick/down2", gp.tileSize*4, gp.tileSize*4);
        left1 = setup("/res/projectile/pickleRick/left1", gp.tileSize*4, gp.tileSize*4);
        left2 = setup("/res/projectile/pickleRick/left2", gp.tileSize*4, gp.tileSize*4);
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
