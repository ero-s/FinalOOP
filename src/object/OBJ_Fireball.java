package object;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

import java.awt.*;

public class OBJ_Fireball extends Projectile {
    GamePanel gp;
    public static final String objName = "Fireball";
    public OBJ_Fireball(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = objName;
        speed = 10;
        maxLife = 60;
        attack = 10;
        useCost = 1;
        alive = false;
        //collision area of projectile
        solidArea = new Rectangle(0,0,48,48);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        //offset of projectile
//        xOffset = (gp.tileSize * 2 - gp.tileSize) / 2;
//        yOffset = (gp.tileSize * 2 - gp.tileSize) / 2;
        getImage();
    }

    public void getImage(){
        up1 = setup("/res/objects/shield_wood", gp.tileSize, gp.tileSize);
        up2 = setup("/res/objects/shield_wood", gp.tileSize, gp.tileSize);
        right1 = setup("/res/objects/shield_wood", gp.tileSize, gp.tileSize);
        right2 = setup("/res/objects/shield_wood", gp.tileSize, gp.tileSize);
        down1 = setup("/res/objects/shield_wood", gp.tileSize, gp.tileSize);
        down2 = setup("/res/objects/shield_wood", gp.tileSize, gp.tileSize);
        left1 = setup("/res/objects/shield_wood", gp.tileSize, gp.tileSize);
        left2 = setup("/res/objects/shield_wood", gp.tileSize, gp.tileSize);
    }

    public boolean haveResource(Entity user){
        boolean haveResource = false;
        if(user.mana >= useCost){
            haveResource = true;
        }
        return haveResource;
    }

    public void subtractResource(Entity user){
        user.mana -= useCost;
    }

    public Color getParticleColor(){
        Color color = new Color(255, 221, 0);
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
