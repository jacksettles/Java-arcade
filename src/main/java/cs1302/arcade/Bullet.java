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

public class Bullet extends Circle {

    private Bounds bulletBounds;
    private Asteroid[] targets;
    private boolean alreadyHit = false;
    private String explodeLink = "https://media.istockphoto.com/illustrations/explosion"
        + "-fire-isolated-on-black-background-detonation-bomb-as-game-illustration-"
        + "id637859556?k=6&m=637859556&s=612x612&w=0&h=XYjy3d7YSrMpRgZFUte7DvL2yJkM"
        + "ssE3CGcgIGZB1lA=";
    private Image explosion = new Image(explodeLink);
    private ImagePattern explode = new ImagePattern(explosion);

    public Bullet(double centerX, double centerY, double radius, Asteroid[] ast) {
        super(centerX, centerY, radius);
        setAsteroids(ast);
        bulletBounds = this.getBoundsInParent();
    }

    public void setAsteroids(Asteroid[] ast) {
        targets = ast;
    }

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

    public boolean check() {
        boolean hit = false;
        for (Asteroid a : targets) {
            if (bulletBounds.intersects(a.getBoundsInParent())) {
                hit = true;
                alreadyHit = true;
                a.addHit();
                a.setFill(explode);
                a.setActive(false);
                if (a.getSideLength() == 50.0) {
                    //update score
                } else if (a.getSideLength() == 30.0) {
                    //update score
                } else if (a.getSideLength() == 15.0) {
                    //update score
                }
                break;
            }
        }
        return hit;
    }

    public void fly(Ship ship) {
        EventHandler<ActionEvent> moveBullet = e -> {
            if (this.isOnScreen()) {
                Double rad = Math.toRadians(ship.getAngle());
                Double x2 = 10.0 * Math.cos(rad); // amt to move by on x axis
                Double y2 = 10.0 * Math.sin(rad); // amt to move by on y axis
                this.setTranslateX(this.getTranslateX() + x2);
                this.setTranslateY(this.getTranslateY() - y2);
                bulletBounds = this.getBoundsInParent();
                if (!alreadyHit && this.check()) {
//                    System.out.println("It's a hit!");
                    // this could be where you deactivate the bullet
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
