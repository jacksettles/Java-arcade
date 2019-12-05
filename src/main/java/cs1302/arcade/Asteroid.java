package cs1302.arcade;

import javafx.geometry.Bounds;
import javafx.util.Duration;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.scene.image.Image;
import javafx.event.EventHandler;
import cs1302.arcade.Ship;

/**
 * This class represents an {@code Asteroid} object
 * to be floating around in space in the Asteroids Game.
 */
public class Asteroid extends Rectangle {

    private boolean init = false;
    private Image astIm = new Image("http://clipart-library.com/image_gallery/275522.png");
    private ImagePattern ip = new ImagePattern(astIm);
    private Double rad;
    private Double sideLength;
    private Bounds astBounds;
    private Timeline tm;
    private Ship ship;
    private int hitCount = 0;
    private boolean isActive = true;
    private int speed;

    public Asteroid(Double length, Ship s) {
        super(length, length);
        sideLength = length;
        if (sideLength == 60.0) {
            speed = 10;
        } else if (sideLength == 30.0) {
            speed = 50;
        } else {
            speed = 90;
        }
        Double randX = randX();
        Double randY = randY();
        this.setX(randX);
        this.setY(randY);
        ship = s;
        astBounds = this.getBoundsInParent();
        init = true;
        this.setFill(ip);
        this.drift(speed);
    }

    public Double getSideLength() {
        return sideLength;
    }

    public void addHit() {
        hitCount++;
    }

    public int getHitCount() {
        return hitCount;
    }

    public void setActive(boolean b) {
        isActive = b;
    }

    public boolean getActive() {
        return isActive;
    }

    public Timeline getTimeLine() {
        return tm;
    }

    public Double randX() {
        Double x = Math.random() * 640.0;
        if (x >= 250.00 && x < 550.0) {
            return randX();
        } else {
            return x;
        }
    }

    public Double randY() {
        Double y = Math.random() * 480.0;
        if (y >= 200.0 && y <= 300.0) {
            return randY();
        } else {
            return y;
        }
    }

    public Double randAngle() {
        return Math.random() * 360.0;
    }

    public void flip(Bounds b, Double xChange, Double yChange) {
        double minX = b.getMinX();
        double minY = b.getMinY();
        double maxX = b.getMaxX();
        double maxY = b.getMaxY();
        if (minX >= 640.0 && (xChange > 0.0)) {
            this.setTranslateX(-40.0 - this.getX());
        } else if (maxX <= 0.0 && (xChange < 0.0)) {
            this.setTranslateX(680.0 - this.getX());
        }
        if (minY >= 480.0 && (yChange < 0.0)) {
            this.setTranslateY(-40.0 - this.getY());
        } else if (maxY <= 0.0 && (yChange > 0.0)) {
            this.setTranslateY(520.0 - this.getY());
        }
    }

    public boolean check() {
        boolean hitShip = false;
        if (astBounds.intersects(ship.getBoundsInParent())) {
            hitShip = true;
        }
        return hitShip;
    }

    public void drift(int sp) {
        rad = Math.toRadians(randAngle());
        EventHandler<ActionEvent> moveAst = e -> {
            Double x2 = 1.0 * Math.cos(rad); // amt to move by on x axis
            Double y2 = 1.0 * Math.sin(rad); // amt to move by on y axis
            this.setTranslateX(this.getTranslateX() + x2);
            this.setTranslateY(this.getTranslateY() - y2);
            astBounds = this.getBoundsInParent();
            if (this.check() && isActive) {
                ship.flash();
                ship.resetPos();
            }
            this.flip(astBounds, x2, y2);
        };
        Double time = 100.0 - sp;
        Duration dur = new Duration(time);
        KeyFrame kf = new KeyFrame(dur, moveAst);
        tm = new Timeline();
        tm.setCycleCount(Timeline.INDEFINITE);
        tm.getKeyFrames().add(kf);
    }
}
