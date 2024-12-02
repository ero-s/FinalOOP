package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_TrophyJoker extends Entity {
    GamePanel gp;
    public static final String objName = "Joker";
    public OBJ_TrophyJoker(GamePanel gp) {
        super(gp);

        this.gp = gp;

        name = objName;
        down1 = setup("/res/projectile/joker_down_1", gp.tileSize, gp.tileSize);
        description = "[Joker]\n\" My genius will be\nunderstood - eventually.\"";
        price = 0;
        setDialogue();
    }

    // backstory dialogues?
    public void setDialogue(){
        //dialogues[0][0] = "You drink the " + name + "!\n" + "Your life has been recovered by " + value + ".";
    }
}
