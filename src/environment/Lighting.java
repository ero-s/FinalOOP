package environment;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class Lighting {
    GamePanel gp;
    BufferedImage darknessFilter;

    public Lighting(GamePanel gp, int circleSize) {
        // Create a buffered image
        darknessFilter = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) darknessFilter.getGraphics();

        // Get the center x and y of the light circle
        int centerX = gp.player.screenX + (gp.tileSize / 2);
        int centerY = gp.player.screenY + (gp.tileSize  / 2);

        // Create a gradation effect within the light circle
        float fraction[] = new float[12];
        Color color[] = new Color[12];

        color[0] = new Color(0, 0, 0, 0.1f);
        color[1] = new Color(0, 0, 0, 0.42f);
        color[2] = new Color(0, 0, 0, 0.52f);
        color[3] = new Color(0, 0, 0, 0.61f);
        color[4] = new Color(0, 0, 0, 0.69f);
        color[5] = new Color(0, 0, 0, 0.76f);
        color[6] = new Color(0, 0, 0, 0.82f);
        color[7] = new Color(0, 0, 0, 0.87f);
        color[8] = new Color(0, 0, 0, 0.91f);
        color[9] = new Color(0, 0, 0, 0.94f);
        color[10] = new Color(0, 0, 0, 0.96f);
        color[11] = new Color(0, 0, 0, 0.98f);

        fraction[0] = 0f;
        fraction[1] = 0.4f;
        fraction[2] = 0.45f;
        fraction[3] = 0.5f;
        fraction[4] = 0.55f;
        fraction[5] = 0.6f;
        fraction[6] = 0.65f;
        fraction[7] = 0.7f;
        fraction[8] = 0.75f;
        fraction[9] = 0.8f;
        fraction[10] = 0.85f;
        fraction[11] = 1f;

        //create gradient circle
        RadialGradientPaint gPaint = new RadialGradientPaint(centerX, centerY, (circleSize/2), fraction, color);

        // set the gradient data
        g2.setPaint(gPaint);

        g2.fillRect(0,0, gp.screenWidth, gp.screenHeight);

        g2.dispose();
    }

    public void draw(Graphics2D g2){
        g2.drawImage(darknessFilter,0,0,null);
    }
}