package cs1302.arcade;

import javafx.scene.shape.Polygon;

public class Ship extends Polygon {

    private Double[] xCords;
    private Double[] yCords;
    private boolean init = false;

    public Ship(Double[] x, Double[] y) {
        setPos(x, y);
        init = true;
    }

    public void setPos(Double[] x, Double[] y) {
        int xIndex = 0;
        int yIndex = 1;
        for (int i = 0; i < 3; i++) {
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

    public Double[] getX() {
        return xCords;
    }

    public Double[] getY() {
        return yCords;
    }
} // Asteroids
