package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Key extends Entity {
    GamePanel gp;
    public OBJ_Key(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_consumable;
        name = "Key";
        down1 = setup("/res/objects/key", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nIt opens a door.";
        price = 100;
    }

    public boolean use(Entity entity){
        boolean usedItem = false;
        gp.gameState = gp. dialogueState;
        int objIndex = getDetected(entity, gp.obj, "Door");
        if(objIndex != 999){
            gp.ui.currentDialogue = "You use the "+name+" and open the door";
            gp.playSE(3);
            gp.obj[gp.currentMap][objIndex] = null;
            usedItem = true;
        }
        else{
            gp.ui.currentDialogue = "There will be another use for that";
        }
        return  usedItem;
    }
}
