package entity;

import main.GamePanel;
import object.OBJ_Smash;

public class Projectile extends Entity {
    Entity user;

    public Projectile(GamePanel gp) {

        super(gp);
    }

    public void set(int worldX, int worldY, String direction, boolean alive, Entity user) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.alive = alive;
        this.user = user;
        this.life = maxLife;
        skill1 = new OBJ_Smash(gp);
    }

    public void update() {
        if (user == gp.player) {
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);

            if (monsterIndex != 999) {
                gp.player.damageMonster(monsterIndex, this, attack*(gp.player.level/2), knockBackPower);
                generateParticle(user.projectile, gp.monster[gp.currentMap][monsterIndex]);
                alive = false;
            }
        }

        if (user != gp.player) {
            boolean contactPlayer = gp.cChecker.checkPlayer(this);

            if (!gp.player.invincible && contactPlayer) {
                damagePlayer(attack);
                generateParticle(user.projectile, user.projectile);
                alive = false;
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

            if(gp.keyH.skill1Pressed){ //g2 public
                gp.g2.drawRect(this.solidArea.x, this.solidArea.y, this.solidArea.width, this.solidArea.height);

                int monsterIndex = gp.cChecker.checkEntity(skill1, gp.monster);

                if (monsterIndex != 999) {
                    gp.player.damageMonster(monsterIndex, this, attack*(gp.player.level/2), knockBackPower);
                    generateParticle(user.projectile, gp.monster[gp.currentMap][monsterIndex]);
                    alive = false;
                }
            }
        }

        spriteCounter++;
        if (spriteCounter > 12) {
            if (spriteNum == 1) { spriteNum = 2; }
            else if (spriteNum == 2) { spriteNum = 1; }
            spriteCounter = 0;
        }
    }

    public boolean haveResource(Entity user) { return false; }

    public void subtractResource(Entity user) { }
}
