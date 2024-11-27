package entity;

import main.GamePanel;

public class PlayerDummy extends Entity {
    public static final String npcName = "Dummy";

    public PlayerDummy(GamePanel gp) {
        super(gp);
        name = npcName;
        getImage();
    }

    public void getImage() {
        up1 = setup("/res/player/hakobe/Walk back-1", gp.tileSize*2, gp.tileSize*2);
        up2 = setup("/res/player/hakobe/Walk back-2", gp.tileSize*2, gp.tileSize*2);
        down1 = setup("/res/player/hakobe/Walk front-1", gp.tileSize*2, gp.tileSize*2);
        down2 = setup("/res/player/hakobe/Walk front-2", gp.tileSize*2, gp.tileSize*2);
        left1 = setup("/res/player/hakobe/Walk Left-1", gp.tileSize*2, gp.tileSize*2);
        left2 = setup("/res/player/hakobe/Walk Left-2", gp.tileSize*2, gp.tileSize*2);
        right1 = setup("/res/player/hakobe/Walk Right-1", gp.tileSize*2, gp.tileSize*2);
        right2 = setup("/res/player/hakobe/Walk Right-2", gp.tileSize*2, gp.tileSize*2);
    }
}