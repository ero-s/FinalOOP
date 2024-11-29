package object.Skills_PK;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

import java.awt.*;

public class PR_SludgeBomb extends Projectile {
    GamePanel gp;
    public static final String objName = "Sludge Bomb";
    public PR_SludgeBomb(GamePanel gp) {
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
        up1 = setup("/res/projectile/pickleRick/up1", gp.tileSize*4, gp.tileSize*4);
        up2 = setup("/res/projectile/pickleRick/up2", gp.tileSize*4, gp.tileSize*4);
        right1 = setup("/res/projectile/pickleRick/right1", gp.tileSize*4, gp.tileSize*4);
        right2 = setup("/res/projectile/pickleRick/right2", gp.tileSize*4, gp.tileSize*4);
        down1 = setup("/res/projectile/pickleRick/down1", gp.tileSize*4, gp.tileSize*4);
        down2 = setup("/res/projectile/pickleRick/down2", gp.tileSize*4, gp.tileSize*4);
        left1 = setup("/res/projectile/pickleRick/left1", gp.tileSize*4, gp.tileSize*4);
        left2 = setup("/res/projectile/pickleRick/left2", gp.tileSize*4, gp.tileSize*4);
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
        Color color = new Color(87, 230, 43);
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
