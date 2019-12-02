package cs1302.arcade;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Control;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import javafx.scene.Group;
import cs1302.arcade.Ship;
import javafx.scene.transform.Rotate;
import javafx.scene.shape.Polygon;
import javafx.geometry.Point2D;
import cs1302.arcade.Bullet;
import javafx.concurrent.Task;
import javafx.application.Platform;
import javafx.util.Duration;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import cs1302.arcade.Asteroid;
import javafx.scene.paint.Color;

public class AsteroidsGame {
    Stage stage;
    Scene asteroidsScene;
    Scene switchBack;
    Group group;
    Ship ship;
    Double[] xCords = {320.0, 312.929, 327.071};
    Double[] yCords = {225.858, 247.071, 247.071};
    Point2D shipCenter;
    Rotate right;
    Rotate left;
    Asteroid[] ast = new Asteroid[1];

    public AsteroidsGame() {
        ship = new Ship(xCords, yCords);
        ship.setFill(Color.GOLD);
        for (int i = 0; i < 1; i++) {
            ast[i] = new Asteroid();
        }
        group = new Group();
        group.getChildren().add(ship);
        group.getChildren().addAll(ast);
        asteroidsScene = new Scene(group, 640, 480);
        asteroidsScene.setOnKeyPressed(moveShip());
        asteroidsScene.setFill(Color.BLACK);
    }

    private EventHandler<? super KeyEvent> moveShip() {
        return event -> {
            switch (event.getCode()) {
            case UP:
                Double radAng = Math.toRadians(ship.getAngle());
                Double x = 10.0 * Math.cos(radAng); // amt to move by on x axis
                Double y = 10.0 * Math.sin(radAng); // amt to move by on y axis
                ship.setTranslateX(ship.getTranslateX() + x);
                ship.setTranslateY(ship.getTranslateY() - y);
                ship.flip();
                break;
            case RIGHT:
                shipCenter = ship.getCenter();
                right = new Rotate(15.0, shipCenter.getX(), shipCenter.getY());
                ship.addAngle(-15.0);
                //System.out.println(ship.getAngle());
                ship.getTransforms().add(right);
                break;
            case LEFT:
                shipCenter = ship.getCenter();
                left = new Rotate(-15.0, shipCenter.getX(), shipCenter.getY());
                ship.addAngle(15.0);
                //System.out.println(ship.getAngle());
                ship.getTransforms().add(left);
                break;
            case Q:
                stage.setScene(switchBack);
                break;
            case SPACE:
                Bullet b = ship.shoot();
                group.getChildren().add(b);
                b.fly(ship);
                break;
            } // switch
        };
    }

    public Scene getScene() {
        return asteroidsScene;
    } //getScene

    public void getSwitch(Stage stage, Scene scene) {
        this.stage = stage;
        switchBack = scene;
    } //getSwitch

} // AsteroidsGame
