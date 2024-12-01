package main;

import entity.Entity;
import entity.NPC_Portal;
import object.*;
import object.GeneralSkills.OBJ_Smash;
import object.Skills_Jack.OBJ_Blizzard;
import object.Skills_Jack.OBJ_Icycle;
import object.Skills_PK.PR_SludgeBomb;

public class EntityGenerator {
    GamePanel gp;

    public EntityGenerator(GamePanel gp){
        this.gp = gp;
    }

    public Entity getObject(String itemName) {
        Entity obj = null;

        switch (itemName) {
            // objects in the inventory
            case CON_Cabbage.objName : obj = new CON_Cabbage(gp); break;
            case CON_Carrot.objName : obj = new CON_Carrot(gp); break;
            case OBJ_Axe.objName : obj = new OBJ_Axe(gp); break;
            case OBJ_Boots.objName : obj = new OBJ_Boots(gp); break;
            case OBJ_Chest.objName : obj = new OBJ_Chest(gp); break;
            case OBJ_Coin_Bronze.objName : obj = new OBJ_Coin_Bronze(gp); break;
            case OBJ_Door.objName : obj = new OBJ_Door(gp); break;
            case OBJ_Door_Iron.objName : obj = new OBJ_Door_Iron(gp); break;
            case OBJ_Fireball.objName : obj = new OBJ_Fireball(gp); break;
            case OBJ_Heart.objName : obj = new OBJ_Heart(gp); break;
            case OBJ_Key.objName : obj = new OBJ_Key(gp); break;
            case OBJ_ManaCrystal.objName : obj = new OBJ_ManaCrystal(gp); break;
            case OBJ_Pickaxe.objName : obj = new OBJ_Pickaxe(gp); break;
            case OBJ_Potion_Red.objName : obj = new OBJ_Potion_Red(gp); break;
            case PR_SludgeBomb.objName : obj = new PR_SludgeBomb(gp); break;
            case OBJ_Rock.objName : obj = new OBJ_Rock(gp); break;
            case OBJ_Shield_Blue.objName : obj = new OBJ_Shield_Blue(gp); break;
            case OBJ_Shield_Wood.objName : obj = new OBJ_Shield_Wood(gp); break;
            case OBJ_Sword_Normal.objName : obj = new OBJ_Sword_Normal(gp); break;
            case OBJ_Lantern.objName: obj = new OBJ_Lantern(gp); break;
            case OBJ_Tent.objName: obj = new OBJ_Tent(gp); break;
            case OBJ_Smash.objName: obj = new OBJ_Smash(gp); break;
            case OBJ_BlueHeart.objName: obj = new OBJ_BlueHeart(gp); break;
            case OBJ_Blizzard.objName: obj = new OBJ_Blizzard(gp);break;
            case OBJ_Icycle.objName: obj = new OBJ_Icycle(gp);break;
        }
        return obj;
    }

}
