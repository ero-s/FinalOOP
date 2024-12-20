package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][][];
    boolean drawPath = true;
    ArrayList<String> fileNames = new ArrayList<>();
    ArrayList<String> collisionStatus = new ArrayList<>();

    public TileManager(GamePanel gp) {
        this.gp = gp;

//         READ TILE DATA FILE
        InputStream is = getClass().getResourceAsStream("/res/maps/data.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

//         GETTING TILE NAMES AND COLLISION INFO FROM THE FILE
        String line;
        int tileIndex = 0;

        try {
            while ((line = br.readLine()) != null) {
                fileNames.add(line);

                // Read the collision status from file
                boolean isSolid = Boolean.parseBoolean(br.readLine());

                int positionInMap = tileIndex % 2500;
                int row = positionInMap / 50;
                int col = positionInMap % 50;


                collisionStatus.add(String.valueOf(isSolid));
                tileIndex++;
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // INITIALIZE THE TILE ARRAY BASED ON THE fileNames size
        tile = new Tile[fileNames.size()];
        getTileImage();

        // GET THE maxWorldCol && Row
        is = getClass().getResourceAsStream("/res/maps/worldmap.txt");
        br = new BufferedReader(new InputStreamReader(is));

        try{
            String line2 = br.readLine();
            String maxTile[] = line2.split(" ");

            gp.maxWorldCol = maxTile.length;
            gp.maxWorldRow = maxTile.length;
            mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

            br.close();
        } catch (IOException e) {
            System.out.println("Exception!");
        }
        getTileImage();


        // worlds

        loadMap("/res/maps/SummerVille.txt", 0);
        loadMap("/res/maps/FallVille.txt", 1);
        loadMap("/res/maps/WinterVille.txt", 2);
        loadMap("/res/maps/SpringVille.txt", 3);

        // dungeons
            loadMap("/res/maps/dungeon01.txt", 4);
            loadMap("/res/maps/dungeon02.txt", 5);
            loadMap("/res/maps/dungeon03.txt", 6);
            loadMap("/res/maps/dungeon02.txt", 7);



    }

    public void setup(int index, String imageName, boolean collision) {
        UtilityTool uTool = new UtilityTool();

        try {

            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/" + imageName));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;

            if (collision) {
                // Set a collision area that is slightly smaller than the tile (e.g., padding by 4px)
                tile[index].collisionBox = new Rectangle(4, 4, gp.tileSize - 8, gp.tileSize - 8);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath, int map) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();

                while (col < gp.maxWorldCol) {
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[map][col][row] = num;
                    col++;
                }

                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e) {

        }
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];
            Tile currentTile =tile[tileNum];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

                g2.drawImage(tile[tileNum].image, screenX, screenY, null);

            }

            worldCol++;

            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;

                worldRow++;
            }

//            Draw the tile's specific collision box if it has collision
//            if (currentTile.collision && currentTile != null) {
//                g2.setColor(Color.red);
//                g2.setStroke(new java.awt.BasicStroke(1));
//
//                // Calculate the tile's collision box world position
//                int collisionBoxX = screenX + currentTile.collisionBox.x;
//                int collisionBoxY = screenY + currentTile.collisionBox.y;
//                int collisionBoxWidth = currentTile.collisionBox.width;
//                int collisionBoxHeight = currentTile.collisionBox.height;
//
//                // Draw the tile's specific collision box
//                g2.drawRect(collisionBoxX, collisionBoxY, collisionBoxWidth, collisionBoxHeight);
//            }
        }

    }

    public void drawPath(Graphics2D g2){
//        g2.setColor(new Color(255, 0, 0, 70));
//
//        for (int i = 0; i < gp.pFinder.pathList.size(); i++) {
//            int worldX = gp.pFinder.pathList.get(i).col * gp.tileSize;
//            int worldY = gp.pFinder.pathList.get(i).row * gp.tileSize;
//            int screenX = worldX - gp.player.worldX + gp.player.screenX;
//            int screenY = worldY - gp.player.worldY + gp.player.screenY;
//
//            g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
//        }
    }

    public void getTileImage() {
        for(int i  = 0; i < fileNames.size(); i++){

            String fileName;
            boolean collision;

            // Get a file name
            fileName = fileNames.get(i);

            // Get a collision status
            if(collisionStatus.get(i).equals("true")){
                collision = true;
            }
            else {
                collision = false;
            }

            setup(i, fileName, collision);
        }
    }

}

