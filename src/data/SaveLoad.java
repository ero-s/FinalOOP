package data;

import main.GamePanel;

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
        readSave();
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

            // objects on map
            ds.mapObjectNames = new String[gp.maxMap][gp.obj[1].length];
            ds.mapObjectWorldX = new int[gp.maxMap][gp.obj[1].length];
            ds.mapObjectWorldY = new int[gp.maxMap][gp.obj[1].length];
            ds.mapObjectOpened = new boolean[gp.maxMap][gp.obj[1].length];

            for (int mapNum = 0; mapNum < gp.maxMap; mapNum++) {

                for (int i = 0; i < gp.obj[1].length; i++) {

                    if (gp.obj[mapNum][i] == null) {
                        ds.mapObjectNames[mapNum][i] = "NA";
                    } else {
                        ds.mapObjectNames[mapNum][i] = gp.obj[mapNum][i].name;
                        ds.mapObjectWorldX[mapNum][i] = gp.obj[mapNum][i].worldX;
                        ds.mapObjectWorldY[mapNum][i] = gp.obj[mapNum][i].worldY;

                        // NOT DONE (LOOKING FOR LOOT)
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

        if(saveIsLoaded() == false) {
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

            // objects on map

            for (int mapNum = 0; mapNum < gp.maxMap; mapNum++) {

                for (int i = 0; i < gp.obj[1].length; i++) {

                    if (ds.mapObjectNames[mapNum][i].equals("NA")) {
                        gp.obj[mapNum][i] = null;
                    } else {

                        gp.obj[mapNum][i] = gp.eGenerator.getObject(ds.mapObjectNames[mapNum][i]);
                        gp.obj[mapNum][i].worldX = ds.mapObjectWorldX[mapNum][i];
                        gp.obj[mapNum][i].worldY = ds.mapObjectWorldY[mapNum][i];

                        if (ds.mapObjectLootNames[mapNum][i] != null) {

                            gp.obj[mapNum][i].loot = gp.eGenerator.getObject(ds.mapObjectLootNames[mapNum][i]);

                        }

                        gp.obj[mapNum][i].opened = ds.mapObjectOpened[mapNum][i];

                        if (gp.obj[mapNum][i].opened == true) {
                            gp.obj[mapNum][i].down1 = gp.obj[mapNum][i].image2;
                        }
                    }
                }
            }

            System.out.println("-Game loaded successfully.");

            ois.close();

        } catch (Exception e) {
            System.out.println("Load Exception!");
        }
    }
}
