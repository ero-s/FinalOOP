package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_WinterDoor extends Entity {
    GamePanel gp;
    public static final String objName = "Winter Door";
    public OBJ_WinterDoor(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_obstacle;
        name = objName;
        down1 = setup("/res/objects/door", gp.tileSize, gp.tileSize);

        collision = true;
        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        setDialogue();
    }

    public void setDialogue() {
        dialogues[0][0] = "You need a key to open this";

    }

    public void interact(){
        startDialogue(this, 0);
    }
}
