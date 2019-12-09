package cs1302.arcade;

import javafx.scene.paint.Color;
import cs1302.arcade.Bullet;
import javafx.scene.transform.Rotate;
import javafx.scene.shape.Polygon;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.util.Duration;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.animation.FillTransition;
import cs1302.arcade.AsteroidsGame;


/**
 * This class represents a {@code Ship}
 * object in AsteroidsGame! It extends Polygon.
 */
public class Ship extends Polygon {

    private Double angle;
    private Double[] xCords;
    private Double[] yCords;
    private boolean init = false;
    private Point2D center;
    private Bounds shipBounds;
    private boolean canMove = true;
    private int lives;
    private boolean justCrashed = false;
    Scene shipScene;
    Scene swapScene;
    Stage stage;

    /**
     * Only constructor for the Ship class;
     * it takes two Double arrays for the x and y coordinates
     * of the triangular ship.
     *
     *@param x the Double array of x cords
     *@param y the Double array of y cords
     */
    public Ship(Double[] x, Double[] y) {
        setPos(x, y);
        angle = 90.0;
        init = true;
        lives = 5;
        shipBounds = this.getBoundsInParent();
    }

    /**
     * This method is set to go off whenever a ship encounters
     * an asteroid. It flashes red and gold and disables the ships
     * mobility for two seconds while its position is reset.
     */
    public void flash() {
        FillTransition blink = new FillTransition(Duration.millis(200.0), this,
                                                  Color.GOLD, Color.RED);
        blink.setCycleCount(10);
        blink.setAutoReverse(true);
        EventHandler<ActionEvent> enableMobility = e -> {
            canMove = true;
        };
        if (lives > 0) {
            blink.setOnFinished(enableMobility);
        }
        blink.play();
        canMove = false;
    }

    /**
     * This method returns whether this ship is mobile.
     *
     *@return canMove a boolean variable
     */
    public boolean getMove() {
        return canMove;
    }

    /**
     * This method sets whether this ship is mobile.
     *
     *@param b a boolean
     */
    public void setMove(boolean b) {
        canMove = b;
    }

    /**
     * This method sets the number of lives the ship has.
     *
     *@param newLives an integer value of lives
     */
    public void setLives(int newLives) {
        lives = newLives;
    }

    /**
     * This method returns the number of lives this ship has.
     *
     *@return lives an integer value of lives
     */
    public int getLives() {
        return lives;
    }

    /**
     * This method flips the ship if it flies off screen.
     * For instance, if the ship flies off the top of the screen,
     * it reappears at the bottom.
     */
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

    /**
     * This ship returns the angle the ship is pointing at.
     *
     *@return ret a Double value of this ships angle
     */
    public Double getAngle() {
        Double ret = angle;
        return ret;
    }

    /**
     * This method adds the value passed in to this ships current angle.
     *
     *@param ang a Double to be added to the ships current angle value.
     */
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

    /**
     * This method returns the center position of this ship.
     *
     *@return copy a copy of this ships center as a Point2D object.
     */
    public Point2D getCenter() {
        Point2D copy = center;
        return copy;
    }

    /**
     * This method sets the starting position of the ship object.
     *
     *@param x the Double array of x cords
     *@param y the Double array of y cords
     */
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

    /**
     * This method calculates the center value of this ship.
     */
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

    /**
     * This method returns the array of x coordinates of this ship's points.
     *
     *@return xCords the double array of xCords
     */
    public Double[] getX() {
        return xCords;
    }


    /**
     * This method returns the array of y coordinates of this ship's points.
     *
     *@return yCords the double array of yCords
     */
    public Double[] getY() {
        return yCords;
    }

    /**
     * This method resets the position of the ship to the center.
     */
    public void resetPos() {
        this.setTranslateX(0.0);
        this.setTranslateY(0.0);
    }

    /**
     * This method sets the justCrashed value of the ship.
     * This is done so that the ship is given a few seconds to move around
     * after it has been reset before another asteroid can technically
     * "hit it" in case an asteroid is in the position it is reset to.
     *
     *@param crashed a boolean
     */
    public void setCrashed(boolean crashed) {
        justCrashed = crashed;
        this.disableCrashing();
    }

    /**
     * This method returns whether this ship just crashed or not.
     *
     *@return justCrashed a boolean whether this ship recently crashed.
     */
    public boolean justCrashed() {
        return justCrashed;
    }

    /**
     * This method disables crashing into asteroids for about
     * 5 seconds, allowing the ship to move around after it had just been hit.
     */
    public void disableCrashing() {
        EventHandler<ActionEvent> disable = e -> {
            justCrashed = true;
        };
        Duration dur = new Duration(7000.0);
        KeyFrame kf = new KeyFrame(dur, disable);
        Timeline tm = new Timeline();
        tm.setCycleCount(1);
        EventHandler<ActionEvent> enable = e -> {
            justCrashed = false;
        };
        tm.setOnFinished(enable);
        tm.getKeyFrames().add(kf);
        tm.play();
    }

    /**
     * This method returns a bullet object for the ship to shoot.
     *
     *@param ast an asteroid array for collision detection
     *@param group the group from AsteroidsGame to add the bullets to
     *@param ag the AsteroidsGame object that is being played
     *@return b a Bullet object
     */
    public Bullet shoot(Asteroid[] ast, Group group, AsteroidsGame ag) {
        Bullet b;
        double rad = Math.toRadians(this.getAngle());
        double centerX = this.getLayoutBounds().getWidth() * Math.cos(rad);
        double centerY = this.getLayoutBounds().getHeight() * -Math.sin(rad);
        centerX += this.getTranslateX() + 320.0;
        centerY += this.getTranslateY() + 240.0;
        b = new Bullet(centerX, centerY, 2.0, ast, group, ag);
        b.setFill(Color.RED);
        return b;
    }

} // Ship
