package object;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

import java.awt.*;

public class OBJ_Smash extends Projectile {

    GamePanel gp;
    public static final String objName = "Smash";

    public OBJ_Smash(GamePanel gp) {
        super(gp);

        this.gp = gp;

        name = objName;
        speed = 0;
        maxLife = 1;
        life = maxLife;
        attack = 5;
        knockBackPower = 5;
        useCost = 2;
        alive = false;

        solidArea = new Rectangle();
        solidArea.x = 0;
        solidArea.y = 0;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 128;
        solidArea.height = 128;
    }


    public boolean haveResource(Entity user) {
        boolean haveResource = false;

        if (user.mana >= useCost) {
            haveResource = true;
        }

        return haveResource;
    }

    public void subtractResource(Entity user) {
        user.mana -= useCost;
    }

    public Color getParticleColor() {
        Color color = new Color(103, 68, 60);

        return color;
    }

    public int getParticleSize() {
        int size = 6;

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
