package data;

import main.GamePanel;

import java.io.*;
import java.util.ArrayList;

public class SaveLoad {
    GamePanel gp;
    ArrayList<String>itemNames = new ArrayList<String>();
    ArrayList<Integer>itemAmounts = new ArrayList<Integer>();

    public SaveLoad(GamePanel gp) {
        this.gp = gp;
    }

    public void save() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("save.dat")));
            DataStorage ds = new DataStorage();

            //PLAYER STATS AND LOCATION
            ds.setLevel(gp.player.level);
            ds.setMaxLife(gp.player.maxLife);
            ds.setLife(gp.player.life);
            ds.setMaxMana(gp.player.maxMana);
            ds.setMana(gp.player.mana);
            ds.setStrength(gp.player.strength);
            ds.setDexterity(gp.player.dexterity);
            ds.setExp(gp.player.exp);
            ds.setNextLevelExp(gp.player.nextLevelExp);
            ds.setCurrX(gp.player.worldX);
            ds.setCurrY(gp.player.worldY);
            ds.setCurrentMap(gp.currentMap);
            ds.setHasSave(gp.hasSave);
            ds.coin = gp.player.coin;

            //PLAYER INVENTORY
            for(int i = 0; i < gp.player.inventory.size(); i++){
                itemNames.add(gp.player.inventory.get(i).name);
            }

            // Write the DataStorage object
            oos.writeObject(ds);
        } catch (Exception e) {
            System.out.println("Save Exception!");
        }
    }

//    public boolean getHasSave(){
//        return hasSave;
//    }

    public void load() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("save.dat")));

            // Read the DataStorage object
            DataStorage ds = (DataStorage) ois.readObject();

            // PLAYER STATS AND LOCATION
            gp.player.level = ds.getLevel();
            gp.player.maxLife = ds.getMaxLife();
            gp.player.life = ds.getLife();
            gp.player.maxMana = ds.getMaxMana();
            gp.player.mana = ds.getMana();
            gp.player.strength = ds.getStrength();
            gp.player.dexterity = ds.getDexterity();
            gp.player.exp = ds.getExp();
            gp.player.nextLevelExp = ds.getNextLevelExp();
            gp.player.coin = ds.coin;
            gp.player.worldX = ds.getCurrX();
            gp.player.worldY = ds.getCurrY();
            gp.currentMap = ds.getCurrentMap();
            gp.hasSave = ds.getHasSave();


        } catch (Exception e) {
            System.out.println("Load Exception!");
        }
    }
}