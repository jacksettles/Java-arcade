package cs1302.arcade;

import javafx.scene.transform.Rotate;
import javafx.scene.shape.Polygon;
import javafx.geometry.Point2D;

public class Ship extends Polygon {

    private Double[] xCords;
    private Double[] yCords;
    private boolean init = false;
    private Point2D center;

    public Ship(Double[] x, Double[] y) {
        setPos(x, y);
        init = true;
    }

    public Point2D getCenter() {
        Point2D copy = center;
        return copy;
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
        makeCenter();
    }

    private void makeCenter() {
        double centerX = 0.0;
        double centerY = 0.0;
        for (Double val : xCords) {
            centerX += val;
        }
        for (Double val : yCords) {
            centerY += val;
        }
        centerX /= 3;
        centerY /= 3;
        center = null;
        center = new Point2D(centerX, centerY);
        //System.out.println(center.toString());
    }

    public Double[] getX() {
        return xCords;
    }

    public Double[] getY() {
        return yCords;
    }
} // Asteroids
