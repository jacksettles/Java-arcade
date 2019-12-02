package cs1302.arcade;

import javafx.util.Duration;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.event.EventHandler;

/**
 * This class represents an {@code Asteroid} object
 * to be floating around in space in the Asteroids Game.
 */
public class Asteroid extends Rectangle {

    private boolean init = false;
    private Image astIm = new Image("http://clipart-library.com/image_gallery/275522.png");
    private ImagePattern ip = new ImagePattern(astIm);

    public Asteroid() {
        super(40, 40);
        this.setX(randX());
        this.setY(randY());
        init = true;
        this.setFill(ip);
        this.drift();
    }

    public Double randX() {
        return Math.random() * 640.0;
    }

    public Double randY() {
        return Math.random() * 480.0;
    }

    public Double randAngle() {
        return Math.random() * 360.0;
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

    public void drift() {
        Double rad = Math.toRadians(randAngle());
        EventHandler<ActionEvent> moveAst = e -> {
            Double x2 = 1.0 * Math.cos(rad); // amt to move by on x axis
            Double y2 = 1.0 * Math.sin(rad); // amt to move by on y axis
            this.setTranslateX(this.getTranslateX() + x2);
            this.setTranslateY(this.getTranslateY() - y2);
        };
        Duration dur = new Duration(100.0);
        KeyFrame kf = new KeyFrame(dur, moveAst);
        Timeline tm = new Timeline();
        tm.setCycleCount(Timeline.INDEFINITE);
        tm.getKeyFrames().add(kf);
        tm.play();
    }
}
