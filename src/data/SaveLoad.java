package data;

import entity.Entity;
import main.GamePanel;
import object.*;

import java.io.*;

public class SaveLoad {
    // variable declaration
    GamePanel gp;
    private ObjectOutputStream oos;
    private DataStorage ds;
    private ObjectInputStream ois;
    private boolean hasSave;

    // constructor
    public SaveLoad(GamePanel gp) {
        this.gp = gp;
        hasSave = false;
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
            case OBJ_Projectile.objName : obj = new OBJ_Projectile(gp); break;
            case OBJ_Rock.objName : obj = new OBJ_Rock(gp); break;
            case OBJ_Shield_Blue.objName : obj = new OBJ_Shield_Blue(gp); break;
            case OBJ_Shield_Wood.objName : obj = new OBJ_Shield_Wood(gp); break;
            case OBJ_Sword_Normal.objName : obj = new OBJ_Sword_Normal(gp); break;
            case OBJ_Lantern.objName: obj = new OBJ_Lantern(gp); break;
            case OBJ_Tent.objName: obj = new OBJ_Tent(gp); break;
        }
        return obj;
    }

    private boolean saveIsLoaded() {
        File saveFile = new File("src/data/save.dat");

        if (!saveFile.exists()) {
            System.out.println("No save file found.");
            hasSave = false; // No save file means no existing save
            return false;
        }

        // Check if the file is empty
        if (saveFile.length() == 0) {
            System.out.println("The save file is empty.");
            hasSave = false; // No valid save data
            return false;
        }
        return true;

    }
    private void readSave() {
        File saveFile = new File("src/data/save.dat");

        if (!saveIsLoaded()) {
            return;
        }

        try {
            ois = new ObjectInputStream(new FileInputStream(new File("src/data/save.dat")));

            // read data storage
            ds = (DataStorage)ois.readObject();
            ois.close();

            gp.setHasSave(ds.getHasSave());
            setHasSave(gp.getHasSave());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void setHasSave(boolean hasSave) {this.hasSave = hasSave;}

    public boolean getHasSave() {
        return hasSave;
    }
    // save method
    public void save() {
        try {
            oos = new ObjectOutputStream(new FileOutputStream(new File("src/data/save.dat")));

            ds = new DataStorage();

            // player stats
            ds.setLevel(gp.player.level);
            ds.setMaxLife(gp.player.maxLife);
            ds.setLife(gp.player.life);
            ds.setMaxMana(gp.player.maxMana);
            ds.setMana(gp.player.mana);
            ds.setStrength(gp.player.strength);
            ds.setDexterity(gp.player.dexterity);
            ds.setAttack(gp.player.attack);
            ds.setDefense(gp.player.defense);
            ds.setExp(gp.player.exp);
            ds.setCurrentMap(gp.currentMap);
            ds.setCurrX(gp.player.worldX);
            ds.setCurrY(gp.player.worldY);
            ds.setNextLevelExp(gp.player.nextLevelExp);
            ds.setMotion1_duration(gp.player.getMotion1_duration());
            ds.setMotion2_duration(gp.player.getMotion2_duration());
            ds.setHasSave(true);

            // player inventory

            for (int i = 0; i < gp.player.inventory.size(); i++) {
                System.out.println("item loaded!");
                ds.getItemNames().add(gp.player.inventory.get(i).name);
                ds.getItemAmounts().add(gp.player.inventory.get(i).amount);
            }

            // player equipment
            ds.setCurrentWeaponSlot(gp.player.getCurrentWeaponSlot());
            ds.setCurrentShieldSlot(gp.player.getCurrentShieldSlot());

            //OBJECTS ON MAP
            ds.mapObjectNames = new String[gp.maxMap][gp.obj[gp.currentMap].length];
            ds.mapObjectWorldX = new int[gp.maxMap][gp.obj[gp.currentMap].length];
            ds.mapObjectWorldY = new int[gp.maxMap][gp.obj[gp.currentMap].length];
            ds.mapObjectLootNames = new String[gp.maxMap][gp.obj[gp.currentMap].length];
            ds.mapObjectOpened = new boolean[gp.maxMap][gp.obj[gp.currentMap].length];

            for (int mapNum = 0; mapNum < gp.maxMap; mapNum++) {
                for (int i = 0; i < gp.obj[1].length; i++) {
                    if (gp.obj[mapNum][i] == null) {
                        ds.mapObjectNames[mapNum][i] = "NA";
                    } else {
                        ds.mapObjectNames[mapNum][i] = gp.obj[mapNum][i].name;
                        ds.mapObjectWorldX[mapNum][i] = gp.obj[mapNum][i].worldX;
                        ds.mapObjectWorldY[mapNum][i] = gp.obj[mapNum][i].worldY;
                        if (gp.obj[mapNum][i].loot != null) {
                            ds.mapObjectLootNames[mapNum][i] = gp.obj[mapNum][i].loot.name;
                        }
                        ds.mapObjectOpened[mapNum][i] = gp.obj[mapNum][i].opened;
                    }
                }
            }

            oos.writeObject(ds);
            oos.close();

            System.out.println("Game saved succesfully!");
        } catch (IOException e) {
            System.out.println("Save Exception! "+ e);
        }
    }

    public void load() {
        File saveFile = new File("src/data/save.dat");

        if(!saveIsLoaded()) {
            return;
        }

        try {
            ois = new ObjectInputStream(new FileInputStream(new File("src/data/save.dat")));

            // Read Data Storage object
            ds = (DataStorage)ois.readObject();
            ois.close();

            // Retrieve Data
            gp.player.setMana(ds.getMana());
            gp.player.setMaxMana(ds.getMaxMana());
            gp.player.setLife(ds.getLife());
            gp.player.setMaxLife(ds.getMaxLife());
            gp.player.setLevel(ds.getLevel());
            gp.player.setAttack(ds.getAttack());
            //gp.player.setCoin(ds.getCoin());
            gp.player.setDefense(ds.getDefense());
            gp.player.setNextLevelExp(ds.getNextLevelExp());
            gp.player.setExp(ds.getExp());
            gp.player.setStrength(ds.getStrength());
            gp.player.setDexterity(ds.getDexterity());
            gp.player.setMotion1_duration(ds.getMotion1_duration());
            gp.player.setMotion2_duration(ds.getMotion2_duration());
            gp.player.worldX = ds.getCurrX();
            gp.player.worldY = ds.getCurrY();
            gp.currentMap = ds.getCurrentMap();
            setHasSave(gp.getHasSave());

            // PLAYER INVENTORY
            gp.player.inventory.clear(); // clears out the default items

            for (int i = 0; i < ds.getItemNames().size(); i++) {
                gp.player.inventory.add(gp.eGenerator.getObject(ds.getItemNames().get(i)));
                gp.player.inventory.get(i).amount = ds.getItemAmounts().get(i);
            }

            // PLAYER EQUIPMENT
            gp.player.setCurrentWeapon(gp.player.inventory.get(ds.getCurrentWeaponSlot()));
            gp.player.setCurrentShield(gp.player.inventory.get(ds.getCurrentShieldSlot()));
            // theres a getter for attack, dmg, attack image (waiting)

            // OBJECTS ON MAP
            for (int mapNum = 0; mapNum < gp.maxMap; mapNum++) {
                for (int i = 0; i < gp.obj[gp.currentMap].length; i++) {
                    if (ds.mapObjectNames[mapNum][i].equals("NA")) {
                        gp.obj[mapNum][i] = null;
                    } else {
                        gp.obj[mapNum][i] =getObject(ds.mapObjectNames[mapNum][i]);
                        gp.obj[mapNum][i].worldX = ds.mapObjectWorldX[mapNum][i];
                        gp.obj[mapNum][i].worldY = ds.mapObjectWorldY[mapNum][i];
                        if (ds.mapObjectLootNames[mapNum][i] != null) {
                            gp.obj[mapNum][i].loot = gp.eGenerator.getObject(ds.mapObjectLootNames[mapNum][i]);
                        }
                        gp.obj[mapNum][i].opened = ds.mapObjectOpened[mapNum][i];
                        if (gp.obj[mapNum][i].opened) {
                            gp.obj[mapNum][i].down1 = gp.obj[mapNum][i].image2;
                        }
                    }
                }
            }


            System.out.println("Game loaded successfully.");

            ois.close();

        } catch (Exception e) {
            System.out.println("Load Exception!");
        }
    }
}
