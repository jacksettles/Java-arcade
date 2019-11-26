package cs1302.arcade;

import javafx.event.EventHandler;
import javafx.scene.shape.Circle;
import cs1302.arcade.Ship;
import javafx.util.Duration;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;

public class Bullet extends Circle {

    public Bullet(double centerX, double centerY, double radius) {
        super(centerX, centerY, radius);
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

    public void fly(Ship ship) {
        EventHandler<ActionEvent> moveBullet = e -> {
            if (this.isOnScreen()) {
                Double rad = Math.toRadians(ship.getAngle());
                Double x2 = 10.0 * Math.cos(rad); // amt to move by on x axis
                Double y2 = 10.0 * Math.sin(rad); // amt to move by on y axis
                this.setTranslateX(this.getTranslateX() + x2);
                this.setTranslateY(this.getTranslateY() - y2);
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
