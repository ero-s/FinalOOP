package tile_interactive;

import entity.Entity;
import main.GamePanel;

import java.awt.*;

public class IT_DestructibleWall extends InteractiveTile {
    GamePanel gp;

    public IT_DestructibleWall(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.gp = gp;
        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;

        down1 = setup("/res/tiles_interactive/destructiblewall", gp.tileSize, gp.tileSize);
        destructible = true;
        life = 3;
    }

    public boolean isCorrectItem(Entity entity) {
        boolean isCorrectItem = false;

        if (entity.currentWeapon.type == type_pickaxe) {
            isCorrectItem = true;
        }

        return isCorrectItem;
    }

    public void playSe() {
        gp.playSE(11);
    }

    public InteractiveTile getDestroyedForm() {
        InteractiveTile tile = null;

        return tile;
    }

    public Color getParticleColor() { return new Color(65,65,65); }

    public int getParticleSize() { return 6; }

    public int getParticleSpeed() { return 1; }

    public int getParticleMaxLife() { return 20; }
}
