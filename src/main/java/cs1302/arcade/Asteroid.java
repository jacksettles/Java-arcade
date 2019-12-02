package cs1302.arcade;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 * This class represents an {@code Asteroid} object
 * to be floating around in space in the Asteroids Game.
 */
public class Asteroid extends Polygon {

    private Double[] xCords;
    private Double[] yCords;
    private boolean init = false;

    public Asteroid (Double[] x, Double[] y) {
        setPos(x, y);
        init = true;
        this.setFill(Color.DARKGRAY);
    }

    public void setPos(Double[] x, Double[] y) {
        int xIndex = 0;
        int yIndex = 1;
        for (int i = 0; i < 8; i++) {
            if (init) {
                this.getPoints().set(xIndex, x[i]);
                this.getPoints().set(yIndex, y[i]);
                xIndex += 2;
                yIndex += 2;
            } else {
                this.getPoints().add(x[i]);
                this.getPoints().add(y[i]);
            }
        }
        xCords = x;
        yCords = y;
    }
}
