package cs1302.arcade;

import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import javafx.scene.shape.Circle;
import cs1302.arcade.Ship;
import javafx.util.Duration;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import cs1302.arcade.Asteroid;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.Group;
import cs1302.arcade.AsteroidsGame;

/**
 * This class represents a {@code Bullet} object,
 * an extension of Circle.
 */
public class Bullet extends Circle {

    private Bounds bulletBounds;
    private Asteroid[] targets;
    private boolean alreadyHit = false;
    private String explodeLink = "explosion-fire-isolated-on-black-background-detonation-bomb-as-game-illustration-id637859556?k=6";
//    private String explodeLink = "https://media.istockphoto.com/illustrations/explosion"
//        + "-fire-isolated-on-black-background-detonation-bomb-as-game-illustration-"
//        + "id637859556?k=6&m=637859556&s=612x612&w=0&h=XYjy3d7YSrMpRgZFUte7DvL2yJkM"
//        + "ssE3CGcgIGZB1lA=";
    private Image explosion = new Image(explodeLink);
    private ImagePattern explode = new ImagePattern(explosion);
    private Group group;
    private AsteroidsGame asteroidsGame;

    /**
     * The sole constructor for a bullet object.
     *
     *@param centerX the x coordinate of this bullet's center
     *@param centerY the y coordinate of this bullet's cente
     *@param radius the radius of this circle
     *@param ast a reference to the Asteroid array in AsteroidsGame for collision detection
     *@param group a reference to the Group from the game for adding and removing bullets
     *@param ag a reference to the AsteroidsGame this bullet is being added to
     */
    public Bullet(double centerX, double centerY, double radius,
                  Asteroid[] ast, Group group, AsteroidsGame ag) {
        super(centerX, centerY, radius);
        setAsteroids(ast);
        bulletBounds = this.getBoundsInParent();
        this.group = group;
        asteroidsGame = ag;
    }

    /**
     * This method sets the Asteroid array
     * for detecting collisions.
     *
     *@param ast an Asteroid array
     */
    public void setAsteroids(Asteroid[] ast) {
        targets = ast;
    }

    /**
     * This method checks to see if this bullet object
     * is on the screen or not.
     *
     *@return onScreen a boolean variable
     */
    public boolean isOnScreen() {
        boolean onScreen;
        if (this.getTranslateX() < 650.0 && this.getTranslateX() > -650.0
            && this.getTranslateY() < 480.0 && this.getTranslateY() > -480.0) {
            onScreen = true;
        } else {
            onScreen = false;
        }
        return onScreen;
    } // isOnScreen

    /**
     * This method checks to see if a bullet object
     * has hit anyof the asteroids in the Asteroid array,
     *
     *@param ship a reference to the ship that is shooting.
     *@return hit a boolan if this bullet has made a hit.
     */
    public boolean check(Ship ship) {
        boolean hit = false;
        for (Asteroid a : targets) {
            if (bulletBounds.intersects(a.getBoundsInParent()) && a.getActive()) {
                hit = true;
                alreadyHit = true;
                a.addHit();
                a.setFill(explode);
                a.setActive(false);
                this.updateScore(a, ship);
                break;
            }
        }
        return hit;
    }

    /**
     * This method takes the score before updating, then finds the next threshold
     * that the player must reach in order to gain another life. The
     * next threshold is the next number that is evenly divisible by 10000.
     *
     *@return threshold the next goal to reach to gain another life.
     */
    public Double findThreshold() {
        int tempScore = asteroidsGame.getScore();
        Double threshold;
        Double n = 10000.0;
        if (tempScore < n) {
            threshold = n;
        } else if (tempScore >= n && tempScore < (2 * n)) {
            threshold = 2 * n;
        } else if (tempScore >= (2 * n) && tempScore < (3 * n)) {
            threshold = 3 * n;
        } else if (tempScore >= (3 * n) && tempScore < (4 * n)) {
            threshold = 4 * n;
        } else if (tempScore >= (4 * n) && tempScore < (5 * n)) {
            threshold = 5 * n;
        } else if (tempScore >= (5 * n) && tempScore < (6 * n)) {
            threshold = 6 * n;
        } else if (tempScore >= (6 * n) && tempScore < (7 * n)) {
            threshold = 7 * n;
        } else if (tempScore >= (7 * n) && tempScore < (8 * n)) {
            threshold = 8 * n;
        } else if (tempScore >= (8 * n) && tempScore < (9 * n)) {
            threshold = 9 * n;
        } else if (tempScore >= (9 * n) && tempScore < (10 * n)) {
            threshold = 10 * n;
        } else {
            threshold = 11 * n;
        }
        return threshold;
    }

    /**
     * This method updates the AsteroidsGame score based off of
     * the size of the asteroid that was hit. Large ones are worth
     * 25 points, medium are 50, and the smallest asteorids are worth 75.
     *
     *@param a a reference to the Asteroid object that was hit
     *@param ship a reference to the ship that is shooting
     */
    public void updateScore(Asteroid a, Ship ship) {
        Double threshold = this.findThreshold();
        if (a.getSideLength() == 60.0) {
            asteroidsGame.setScore(asteroidsGame.getScore() + 25);
        } else if (a.getSideLength() == 30.0) {
            asteroidsGame.setScore(asteroidsGame.getScore() + 50);
        } else if (a.getSideLength() == 15.0) {
            asteroidsGame.setScore(asteroidsGame.getScore() + 75);
        }
        int newScore = asteroidsGame.getScore();
        if (newScore >= threshold) {
            ship.setLives(ship.getLives() + 1);
        }
    }

    /**
     * This method makes the bullet fly 10 spaces every 15
     * milliseconds.
     *
     *@param ship a reference to the ship that shoots the bullet
     */
    public void fly(Ship ship) {
        EventHandler<ActionEvent> moveBullet = e -> {
            if (this.isOnScreen()) {
                Double rad = Math.toRadians(ship.getAngle());
                Double x2 = 10.0 * Math.cos(rad); // amt to move by on x axis
                Double y2 = 10.0 * Math.sin(rad); // amt to move by on y axis
                this.setTranslateX(this.getTranslateX() + x2);
                this.setTranslateY(this.getTranslateY() - y2);
                bulletBounds = this.getBoundsInParent();
                if (!alreadyHit && this.check(ship)) {
                    group.getChildren().remove(this);
                }
            }
        };
        Duration dur = new Duration(15.0);
        KeyFrame kf = new KeyFrame(dur, moveBullet);
        Timeline tm = new Timeline();
        tm.setCycleCount(Timeline.INDEFINITE);
        tm.getKeyFrames().add(kf);
        tm.play();
        if (!this.isOnScreen()) {
            tm.stop();
        }
    }
} // Bullet
