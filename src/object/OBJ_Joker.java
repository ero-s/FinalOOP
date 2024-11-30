package object;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

import java.awt.*;

public class OBJ_Joker extends Projectile {
    GamePanel gp;
    public static final String objName = "Joker";
    public OBJ_Joker(GamePanel gp) {
        super(gp);

        this.gp = gp;

        name = objName;
        speed = 7;
        maxLife = 80;
        life = maxLife;
        attack = 4;
        useCost = 1;
        alive = false;
        getImage();
    }

    public void getImage() {
        up1 = setup("/res/projectile/joker_down_1", gp.tileSize, gp.tileSize);
        up2 = setup("/res/projectile/joker_down_1", gp.tileSize, gp.tileSize);
        down1 = setup("/res/projectile/joker_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/res/projectile/joker_down_1", gp.tileSize, gp.tileSize);
        left1 = setup("/res/projectile/joker_down_1", gp.tileSize, gp.tileSize);
        left2 = setup("/res/projectile/joker_down_1", gp.tileSize, gp.tileSize);
        right1 = setup("/res/projectile/joker_down_1", gp.tileSize, gp.tileSize);
        right2 = setup("/res/projectile/joker_down_1", gp.tileSize, gp.tileSize);
    }


    public Color getParticleColor() {
        Color color = new Color(40, 50, 30);

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
