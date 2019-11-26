package cs1302.arcade;

import cs1302.arcade.Bullet;
import javafx.scene.transform.Rotate;
import javafx.scene.shape.Polygon;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Ship extends Polygon {

    private Double angle;
    private Double[] xCords;
    private Double[] yCords;
    private boolean init = false;
    private Point2D center;
    Scene shipScene;
    Scene swapScene;
    Stage stage;

    public Ship(Double[] x, Double[] y) {
        setPos(x, y);
        angle = 90.0; // might move this line to setPos method
        init = true;
    }

    public void flip() {
        if (this.getTranslateX() >= 330.0) {
            this.setTranslateX(-330.0);
        } else if (this.getTranslateX() <= -330.0) {
            this.setTranslateX(330.0);
        }
        if (this.getTranslateY() >= 250.0) {
            this.setTranslateY(-250.0);
        } else if (this.getTranslateY() <= -250.0) {
            this.setTranslateY(250.0);
        }
    }

    public Double getAngle() {
        Double ret = angle;
        return ret;
    }

    public void addAngle(Double ang) {
        if (ang >= 360.0 || ang < -360.0) {
            throw new IllegalArgumentException("ang parmeter must be between"
                                               + " -360.0 inclusive and 360.0 degrees exclusive");
        }
        angle += ang;
        if (Math.abs(angle - 360.0) <= 0.0001) {
            angle = 0.0;
        }
        if (angle < 0.0) {
            angle += 360.0;
        }
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
    }

    public Double[] getX() {
        return xCords;
    }

    public Double[] getY() {
        return yCords;
    }

    public void setSwitch(Stage stage, Scene scene) {
        this.stage = stage;
        swapScene = scene;
    } //setS

    public Bullet shoot() {
        Bullet b;
        double rad = Math.toRadians(this.getAngle());
        double centerX = this.getLayoutBounds().getWidth() * Math.cos(rad);
        double centerY = this.getLayoutBounds().getHeight() * -Math.sin(rad);
        centerX += this.getTranslateX() + 320.0;
        centerY += this.getTranslateY() + 240.0;
        b = new Bullet(centerX, centerY, 2.0);
        return b;
    }

} // Asteroids
