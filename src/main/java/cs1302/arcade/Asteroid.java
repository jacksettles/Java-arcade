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
import cs1302.arcade.AsteroidsGame;

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
    private AsteroidsGame asteroidsGame;

    /**
     * The sole constructor for an {@code Asteroid} object, this method
     * sets the asteroid in size and in motion.
     *
     *@param length the side length of this Asteroid
     *@param s a reference to the ship object for collision detection
     *@param ag a reference to the AsteroidsGame object this asteroid is made for
     */
    public Asteroid(Double length, Ship s, AsteroidsGame ag) {
        super(length, length);
        asteroidsGame = ag;
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

    /**
     * This method returns the side length of the Asteroid object.
     *
     *@return sideLength a double value
     */
    public Double getSideLength() {
        return sideLength;
    }

    /**
     * This method adds a hit to this Asteroid object for
     * when a bullet strikes it.
     */
    public void addHit() {
        hitCount++;
    }

    /**
     * This method returns the hit count on this Asteroid.
     *
     *@return hitCount an integer value of hits
     */
    public int getHitCount() {
        return hitCount;
    }

    /**
     * This method sets whether or not this Asteroid is
     * active, meaning, if this asteroid is 'alive' to be hit.
     *
     *@param b a boolean value for whether this Asteroid is active
     */
    public void setActive(boolean b) {
        isActive = b;
    }

    /**
     * This method returns whether or not this asteroid is active.
     *
     *@return isActive a boolean value.
     */
    public boolean getActive() {
        return isActive;
    }

    /**
     * This method returns the {@code Timeline} object of this Asteriod.
     * This is done so the Asteroids do not strart moving until the game is
     * instantiated.
     *
     *@return tm the Timeline of this Asteroid
     */
    public Timeline getTimeLine() {
        return tm;
    }

    /**
     * This method generates a random double for an X coordinate
     * of this Asteroid to start at. If it is betwen 250.0 and 550.0
     * then recursion is used to regenerate a new value.
     *
     *@return x a double value
     */
    public Double randX() {
        Double x = Math.random() * 640.0;
        if (x >= 250.00 && x < 550.0) {
            return randX();
        } else {
            return x;
        }
    }

    /**
     * This method generates a random double for a Y coordinate
     * of this Asteroid to start at. If it is betwen 200.0 and 300.0
     * then recursion is used to regenerate a new value.
     *
     *@return y a double value
     */
    public Double randY() {
        Double y = Math.random() * 480.0;
        if (y >= 200.0 && y <= 300.0) {
            return randY();
        } else {
            return y;
        }
    }

    /**
     * This method generates a random double for an angle
     * for this Asteroid to move at. Angle is between
     * 0 inclusive and 360.0 exclusive.
     *
     *@return a double value of an angle
     */
    public Double randAngle() {
        return Math.random() * 360.0;
    }

    /**
     * This method flips the Asteroid when it drifts off screen.
     *
     *@param b the {@code Bounds} of this Asteroid
     *@param xChange a double value for the change in the X axis
     *@param yChange a double value for the change in the Y axis
     */
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

    /**
     * This method checks to se if this Asteroid
     * has collided with the ship.
     *
     *@return hitShip a boolean for whether this asteroid has hit the ship
     */
    public boolean check() {
        boolean hitShip = false;
        if (astBounds.intersects(ship.getBoundsInParent()) && ship.getMove()) {
            hitShip = true;
        }
        return hitShip;
    }

    /**
     * This method sets this Asteroid in drift at a speed
     * based off of the integer passed in.
     * If an asteroid hits a ship then the ship loses a life,
     * flashes red and gold, and resets the position.
     *
     *@param sp an integer that determines the speed
     */
    public void drift(int sp) {
        rad = Math.toRadians(randAngle());
        EventHandler<ActionEvent> moveAst = e -> {
            Double x2 = 1.0 * Math.cos(rad); // amt to move by on x axis
            Double y2 = 1.0 * Math.sin(rad); // amt to move by on y axis
            this.setTranslateX(this.getTranslateX() + x2);
            this.setTranslateY(this.getTranslateY() - y2);
            astBounds = this.getBoundsInParent();
            if (this.check() && isActive && !ship.justCrashed()) {
                ship.setLives(ship.getLives() - 1);
                ship.flash();
                ship.resetPos();
                ship.setCrashed(true);
                if (ship.getLives() == 0) {
                    isActive = false;
                    asteroidsGame.presentFinalScore();
                    asteroidsGame.getGroup().getChildren().remove(this);
                }
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
