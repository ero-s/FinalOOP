package main;

import entity.Entity;

public class EventHandler {
    GamePanel gp;
    EventRect eventRect[][][];
    Entity eventMaster;

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;
    int tempMap, tempCol, tempRow;

    public EventHandler(GamePanel gp) {
        this.gp = gp;
        eventMaster = new Entity(gp);

        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        int map = 0;
        int col = 0;
        int row = 0;

        while (map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow) {
            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].height = 2;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;

            col++;

            if (col == gp.maxWorldCol) {
                col = 0;
                row++;

                if (row == gp.maxWorldCol) {
                    row = 0;
                    map++;
                }
            }
        }
        setDialogue();
    }

    public void setDialogue(){
        eventMaster.dialogues[0][0] = "You fall into a pit!";
        eventMaster.dialogues[1][0] = "You drink the water. \n Your life and mana have been recovered";

    }

    public void checkEvent() {
        // CHECK IF PLAYER CHAR IS MORE THAN 1 TILE AWAY FROM THE LAST EVENT
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);

        if (distance > gp.tileSize) {
            canTouchEvent = true;
        }

        if (canTouchEvent) {
            // PIT DAMAGE
            if (hit(0, 27, 16, "right")) {
                damagePit(gp.dialogueState);
            }

            //Opening Dialogue
            else if(hit(0, 41, 38, "any")
                    || hit(0, 42, 38, "any")
                    || hit(0, 43, 38, "any")
                    || hit(0, 44, 38, "any")
                    || hit(0, 45, 38, "any")){
                if(gp.player.currentDialogueSet == 1){
                    gp.player.startDialogue(gp.player, gp.player.currentDialogueSet);
                    gp.player.currentDialogueSet++;
                }
            }

            //Confronting PickleRick
            else if(hit(4, 21, 20, "any")
                    || hit(4, 22, 20, "any")
                    || hit(4, 23, 20, "any")
                    || hit(4, 24, 20, "any")
                    || hit(4, 25, 20, "any")){
                if(gp.player.currentDialogueSet == 2){

                    gp.player.startDialogue(gp.player, gp.player.currentDialogueSet);
                    gp.player.currentDialogueSet++;
                }
            }

            //PickleRick BackStory
            else if(hit(4, 18, 4, "any")
                    || hit(4, 19, 4, "any")
                    || hit(4, 20, 4, "any")
                    || hit(4, 21, 4, "any")
                    || hit(4, 22, 4, "any")
                    || hit(4, 23, 4, "any")
                    || hit(4, 24, 4, "any")
                    || hit(4, 25, 4, "any")
                    || hit(4, 26, 4, "any")
                    || hit(4, 27, 4, "any")
                    || hit(4, 28, 4, "any")
                    || hit(4, 29, 4, "any")
                    || hit(4, 30, 4, "any")){
                if(gp.player.currentDialogueSet == 3){

                    gp.player.startDialogue(gp.player, gp.player.currentDialogueSet);
                    gp.player.currentDialogueSet++;
                }
            }

            // TELEPORT TO FALL CIRCUS
            if (hit(0, 14, 7, "any") || hit(0, 13, 7, "any")
                    || hit(0, 14, 6, "any") || hit(0, 13, 6, "any")) {
                teleport(1,45,15);
            }

            // DRINK RECOVERING
            else if (hit(0, 23, 12, "up")) {
                healingPool(gp.dialogueState);
            }

            // TELEPORT to FALL
            else if (hit(0, 10, 39, "any")) {
                teleport(1, 12, 13);
            }

            // BACK TELEPORT
            else if (hit(1, 12, 13, "any")) {
                teleport(0, 10, 39);
            }

            // NPC MERCHANT
            else if (hit(1, 12, 9, "up") ) {
                speak(gp.npc[1][0]);
            }

            // SUMMER DUNGEON
            else if (hit(0, 41, 15, "any") || hit(0, 40, 15, "any")){
                teleport(4, 24, 44);
            }

            // EXIT SUMMER DUNGEON
            else if(hit(4,24,44,"any")){
                teleport(0, 41, 16);
            }

            // FALL DUNGEON
            else if(hit(1,25,42,"any")){
                teleport(5, 24, 28);
            }

            // EXIT FALL DUNGEON
            else if(hit(5,24,28,"any")){
                teleport(1, 25, 42);
            }

            // FALL TO WINTER
            else if(hit(1,1,44,"any")){
                teleport(2, 2, 39);
            }

            // WINTER TO SPRING
            else if(hit(2,42,45,"any")){
                teleport(3, 18, 46);
            }

            // WINTER DUNGEON
            else if(hit(2,43,6,"any")){
                teleport(6, 3, 4);
            }

            // EXIT WINTER DUNGEON
            else if(hit(6,3,4,"any")){
                teleport(2, 43, 6);
            }
        }
    }

    public boolean hit(int map, int col, int row, String reqDirection){
        boolean hit = false;
        if(map == gp.currentMap){
            gp.player.mapCollision.x = gp.player.worldX + gp.player.mapCollision.x;
            gp.player.mapCollision.y = gp.player.worldY + gp.player.mapCollision.y;
            eventRect[map][col][row].x = col*gp.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row*gp.tileSize + eventRect[map][col][row].y;

            if(gp.player.mapCollision.intersects(eventRect[map][col][row]) && !eventRect[map][col][row].eventDone){
                if(gp.player.direction.contentEquals(reqDirection)|| reqDirection.contentEquals("any")){
                    hit = true;

                    previousEventX = gp.player.worldX;
                    previousEventY = gp.player.worldY;
                }
            }
            gp.player.mapCollision.x = gp.player.mapCollisionDefaultX;
            gp.player.mapCollision.y = gp.player.mapCollisionDefaultY;

            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
        }
        return hit;
    }

    public void damagePit(int gameState) {
        gp.gameState = gameState;
        gp.playSE(6);
        eventMaster.startDialogue(eventMaster, 0);
        gp.player.life -= 1;
        canTouchEvent = false;
    }

    public void healingPool(int gameState) {
        if (gp.keyH.enterPressed) {
            gp.gameState = gameState;
            gp.player.attackCanceled = true;
            gp.playSE(2);
            eventMaster.startDialogue(eventMaster, 1);
            gp.player.life = gp.player.maxLife;
            gp.player.mana = gp.player.maxMana;
            gp.aSetter.setMonster();
        }
    }

    public void teleport(int map, int col, int row) {
        gp.gameState = gp.transitionState;
        tempMap = map;
        tempCol = col;
        tempRow = row;
        canTouchEvent = false;
        gp.playSE(13);
    }

    public void speak(Entity entity) {
        if (gp.keyH.enterPressed) {
            gp.gameState = gp.dialogueState;
            gp.player.attackCanceled = true;
            entity.speak();
        }
    }
}
