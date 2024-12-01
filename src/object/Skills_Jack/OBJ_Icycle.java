package object.Skills_Jack;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

import java.awt.*;

public class OBJ_Icycle extends Projectile {
    GamePanel gp;
    public static final String objName = "Icycle";
    public OBJ_Icycle(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = objName;
        speed = 10;
        maxLife = 60;
        attack = 10;
        useCost = 1;
        alive = false;
        //collision area of projectile
        solidArea = new Rectangle(64,64,64,64);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        //offset of projectile
        xOffset = (gp.tileSize * 2 - gp.tileSize) / 2;
        yOffset = (gp.tileSize * 2 - gp.tileSize) / 2;
        getImage();
    }
    public void getImage(){
        up1 = setup("/res/projectile/Jack/icycle/up1", gp.tileSize*4, gp.tileSize*4);
        up2 = setup("/res/projectile/Jack/icycle/up2", gp.tileSize*4, gp.tileSize*4);
        right1 = setup("/res/projectile/Jack/icycle/right1", gp.tileSize*4, gp.tileSize*4);
        right2 = setup("/res/projectile/Jack/icycle/right2", gp.tileSize*4, gp.tileSize*4);
        down1 = setup("/res/projectile/Jack/icycle/down1", gp.tileSize*4, gp.tileSize*4);
        down2 = setup("/res/projectile/Jack/icycle/down2", gp.tileSize*4, gp.tileSize*4);
        left1 = setup("/res/projectile/Jack/icycle/left1", gp.tileSize*4, gp.tileSize*4);
        left2 = setup("/res/projectile/Jack/icycle/left2", gp.tileSize*4, gp.tileSize*4);
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
        Color color = new Color(60, 155, 219);
        return color;
    }

    public int getParticleSize(){
        int size = 10;
        return size;
    }

    public int getParticleSpeed(){
        int speed = 1;
        return speed;
    }

    public int getParticleMaxLife(){
        int maxLife = 20;
        return maxLife;
    }
}
