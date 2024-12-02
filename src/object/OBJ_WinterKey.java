package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_WinterKey extends Entity {
    GamePanel gp;
    public static final String objName = "Winter Key";
    public OBJ_WinterKey(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_consumable;
        name = objName;
        down1 = setup("/res/objects/key_1", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nIt opens a door.";
        price = 100;
        stackable = true;
        setDialogue();
    }

    public void setDialogue(){
        dialogues[0][0] = "You use the "+name+" and open the door";
        dialogues[1][0] = "There will be another use for that";
    }

    public boolean use(Entity entity){
        boolean usedItem = false;
        int objIndex = getDetected(entity, gp.obj, "Winter Door");
        if(objIndex != 999){
            startDialogue(this, 0);
            gp.playSE(3);
            gp.obj[gp.currentMap][objIndex] = null;
            usedItem = true;
        }
        else{
            startDialogue(this, 1);
        }
        return  usedItem;
    }
}
