package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_Blue extends Entity {

    GamePanel gp;
    public static final String objName = "Blue Shield";
    public OBJ_Shield_Blue(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = type_shield;
        name = objName;
        down1 = setup("/res/objects/shield_blue", gp.tileSize, gp.tileSize);
        defenseValue = 1;
        description = "[" + name + "]\nA shiny blue shield.";
        price = 250;

        setDialogues();
    }

    public void setDialogues(){

        dialogues[0][0] = "You pick up a beautiful blue gem.";
        dialogues[0][1] = "You find the Blue Heart, the legendary treasure!";
    }
    public boolean use(Entity entity){

        gp.gameState = gp.cutsceneState;
        gp.csManager.sceneNum = gp.csManager.ending;
        return true;
    }
    
}
