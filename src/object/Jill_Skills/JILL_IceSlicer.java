package object.Jill_Skills;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

import java.awt.*;

public class JILL_IceSlicer extends Projectile {
    GamePanel gp;
    public static final String objName = "Ice Slicer";

    public JILL_IceSlicer(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = objName;
        speed = 10;
        maxLife = 60;
        attack = 10;
        useCost = 1;
        alive = false;

        // collision of the projectile
        solidArea = new Rectangle(64,64,64,64);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        xOffset = (gp.tileSize * 2 - gp.tileSize) / 2;
        yOffset = (gp.tileSize * 2 - gp.tileSize) / 2;
        getImage();

    }

    public void getImage() {
        up1 = setup("/res/projectile/IceSlicer/ICup", gp.tileSize*4, gp.tileSize*4);
        up2 = setup("/res/projectile/IceSlicer/ICup2", gp.tileSize*4, gp.tileSize*4);
        down1 = setup("/res/projectile/IceSlicer/ICdown", gp.tileSize*4, gp.tileSize*4);
        down2 = setup("/res/projectile/IceSlicer/ICdown2", gp.tileSize*4, gp.tileSize*4);
        left1 = setup("/res/projectile/IceSlicer/ICleft", gp.tileSize*4, gp.tileSize*4);
        left2  = setup("/res/projectile/IceSlicer/ICleft2", gp.tileSize*4, gp.tileSize*4);
        right1 = setup("/res/projectile/IceSlicer/ICright", gp.tileSize*4, gp.tileSize*4);
        right2 = setup("/res/projectile/IceSlicer/ICright2", gp.tileSize*4, gp.tileSize*4);
    }

    public boolean haveResource(Entity user){
        boolean haveResource = false;
        if(user.mana >= useCost){
            haveResource = true;
        }
        return haveResource;
    }

    public void subtractResource(Entity user) { user.mana -= useCost; }

    public Color getParticleColor() {
        Color color = new Color(40, 171, 242);
        return color;
    }


    public int getParticleSize(){
        int size = 10;
        return size;
    }

    public int getParticleMaxLife(){
        int maxLife = 20;
        return maxLife;
    }


}
