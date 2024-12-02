package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_FallKey extends Entity {
    GamePanel gp;
    public static final String objName = "Fall Key";
    public OBJ_FallKey(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_consumable;
        name = objName;
        down1 = setup("/res/objects/key_0", gp.tileSize, gp.tileSize);
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
        int objIndex = getDetected(entity, gp.obj, "Fall Door");
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
