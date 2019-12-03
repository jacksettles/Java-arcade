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
    private Double roomRight;
    private Double roomLeft;
    private Double roomUp;
    private Double roomDown;
    private Double xPos;
    private Double yPos;
    private boolean hasCrossed = false;
    private static final Double compare = 0.01;

    public Asteroid() {
        super(40, 40);
        Double randX = randX();
        Double randY = randY();
        this.setX(randX);
        this.setY(randY);
        xPos = this.getX() + this.getTranslateX();
        yPos = this.getY() - this.getTranslateY();
//        this.setTranslateX(randX);
//        this.setTranslateY(randY);
//        System.out.println(this.getX() + ", " + this.getY());
//        System.out.println(this.getTranslateX() + ", " + this.getTranslateY());
        roomRight = 640 - this.getX();
        roomLeft = 0 - this.getX();
        roomDown = 480 - this.getY();
        roomUp = 0 - this.getY();
//        System.out.println(roomLeft);
//        System.out.println(roomUp);
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
        if (xPos - 640.0 <= compare) {
            System.out.println("Should flip to left");
            this.setTranslateX(0.0);
        } else if (xPos - 0.0 <= compare) {
            System.out.println("Should flip to right");
            this.setTranslateX(640.0);
        }
        if (yPos - 480.0 <= compare) {
            System.out.println("Should flip to up");
            this.setTranslateY(0.0);
        } else if (yPos - 0.0 <= compare) {
            System.out.println("Should flip to down");
            this.setTranslateY(480.0);
        }
    }

    public void drift() {
        Double rad = Math.toRadians(randAngle());
        EventHandler<ActionEvent> moveAst = e -> {
            Double x2 = 1.0 * Math.cos(rad); // amt to move by on x axis
            Double y2 = 1.0 * Math.sin(rad); // amt to move by on y axis
            this.setTranslateX(this.getTranslateX() + x2);
            this.setTranslateY(this.getTranslateY() - y2);
            xPos += x2;
            yPos -= y2;
            System.out.println(xPos+ ", " + yPos);
            this.flip();
        };
        Duration dur = new Duration(1000.0);
        KeyFrame kf = new KeyFrame(dur, moveAst);
        Timeline tm = new Timeline();
        tm.setCycleCount(Timeline.INDEFINITE);
        tm.getKeyFrames().add(kf);
        tm.play();
    }
}
