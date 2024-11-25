package data;

import entity.Entity;
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

    // helper function
    private Entity getObject(String itemName) {
        Entity obj = null;

        switch (itemName) {
            // objects in the inventory
            //case "Normal Sword": obj = new OBJ_Sword_Normal(gp); break;
            //case "Wood Shield": obj = new OBJ_Shield_Wood(gp); break;
            //case "Woodcutter's Axe": obj = new OBJ_Axe(gp); break;
            //case "Cabbage": obj = new CON_Cabbage(gp); break;
            //case "Axe": obj = new OBJ_Axe(gp); break;
            //case "Key": obj = new OBJ_Key(gp); break;
            // add more cases if necessary
        }
        return obj;
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

            setHasSave(gp.getHasSave());

            // PLAYER INVENTORY
            gp.player.inventory.clear(); // clears out the default items

            for (int i = 0; i < ds.getItemNames().size(); i++) {
                gp.player.inventory.add(getObject(ds.getItemNames().get(i)));
                gp.player.inventory.get(i).amount = ds.getItemAmounts().get(i);
            }

            // PLAYER EQUIPMENT
            gp.player.setCurrentWeapon(gp.player.inventory.get(ds.getCurrentWeaponSlot()));
            gp.player.setCurrentShield(gp.player.inventory.get(ds.getCurrentShieldSlot()));
            // theres a getter for attack, dmg, attack image (waiting)

            System.out.println("Game loaded successfully.");

            ois.close();

        } catch (Exception e) {
            System.out.println("Load Exception!");
        }
    }
}
