package data;

import javax.xml.crypto.Data;
import java.io.Serializable;
import java.util.ArrayList;

public class DataStorage implements Serializable {
    boolean isHasSave = false;

    public int coin;
    // encapsulated players stats
    private int life;
    private int maxLife;
    private int maxMana;
    private int mana;
    private int level;
    private int strength;
    private int dexterity;
    private int attack;
    private int defense;
    private int exp;
    private int nextLevelExp;
    private int currentMap;
    private int currX;
    private int currY;
    private int motion1_duration;
    private int motion2_duration;

    // save identifier
    private boolean hasSave;

    // item attributes
    private ArrayList<String> itemNames;
    private ArrayList<Integer> itemAmounts;
    private int currentWeaponSlot;
    private int currentShieldSlot;

    // object on map
    String[][] mapObjectNames;
    int[][] mapObjectWorldX;
    int[][] mapObjectWorldY;
    String[][] mapObjectLootNames;
    boolean[][] mapObjectOpened;

    // constructor
    public DataStorage() {
        itemAmounts = new ArrayList<>();
        itemNames = new ArrayList<>();
        hasSave = false;
    }

    // getter and setters
    public int getMotion2_duration() {
        return motion2_duration;
    }

    public void setMotion2_duration(int motion2_duration) {
        this.motion2_duration = motion2_duration;
    }

    public int getMotion1_duration() {
        return motion1_duration;
    }

    public void setMotion1_duration(int motion1_duration) {
        this.motion1_duration = motion1_duration;
    }

    public int getMaxLife() {
        return maxLife;
    }

    public void setMaxLife(int maxLife) {
        this.maxLife = maxLife;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getDexterity() {
        return dexterity;
    }

    public int getCurrentWeaponSlot() {
        return currentWeaponSlot;
    }

    public void setCurrentWeaponSlot(int currentWeaponSlot) {
        this.currentWeaponSlot = currentWeaponSlot;
    }

    public int getCurrentShieldSlot() {
        return currentShieldSlot;
    }

    public void setCurrentShieldSlot(int currentShieldSlot) {
        this.currentShieldSlot = currentShieldSlot;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getNextLevelExp() {
        return nextLevelExp;
    }

    public void setNextLevelExp(int nextLevelExp) {
        this.nextLevelExp = nextLevelExp;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public void setCurrX(int currX){this.currX = currX;}

    public void setCurrY(int currY){this.currY = currY;}

    public void setCurrentMap(int currentMap){this.currentMap = currentMap;}

    public int getCurrX(){return currX;}

    public int getCurrY(){return currY;}

    public int getCurrentMap(){return currentMap;}

    public boolean getHasSave() {
        return hasSave;
    }

    public void setHasSave(boolean hasSave) {
        this.hasSave = hasSave;
    }

    public ArrayList<String> getItemNames() {
        return itemNames;
    }

    public void setItemNames(ArrayList<String> itemNames) {
        this.itemNames = itemNames;
    }

    public ArrayList<Integer> getItemAmounts() {
        return itemAmounts;
    }

    public void setItemAmounts(ArrayList<Integer> itemAmounts) {
        this.itemAmounts = itemAmounts;
    }

}
