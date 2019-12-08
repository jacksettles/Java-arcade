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
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

/**
 * This class represents an instance of a game of Asteroids.
 */
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
    Asteroid[] ast;
    int score;
    int level;
    Label scoreboard;
    Label finalScore;
    String scoreText = "Score: ";
    String levelText = "\tLevel: ";
    String lifeText = "\tLife: ";
    String gameInfo;
    String finText = "Final Score: ";

    /**
     * The lone constructor for Asteroids. An instance of this
     * sets the whole game up.
     */
    public AsteroidsGame() {
        score = 0;
        level = 1;
        ship = new Ship(xCords, yCords);
        ship.setFill(Color.GOLD);
        ship.setLives(5);
        ast = new Asteroid[15];
        for (int i = 0; i < 15; i++) {
            double length = 0.0;
            int randSize = (int) (Math.random() * 3);
            switch (randSize) {
            case 0:
                length = 15.0;
                break;
            case 1:
                length = 30.0;
                break;
            case 2:
                length = 60.0;
                break;
            }
            ast[i] = new Asteroid(length, ship, this);
        }
        scoreboard  = new Label();
        scoreboard.setFont(new Font("Comic Sans", 25.0));
        finalScore = new Label();
        scoreboard.setTextFill(Color.WHITE);
        scoreboard.setTranslateX(130.0);
        group = new Group();
        group.getChildren().add(ship);
        group.getChildren().addAll(ast);
        group.getChildren().addAll(scoreboard);
        asteroidsScene = new Scene(group, 640, 480);
        asteroidsScene.setOnKeyPressed(moveShip());
        asteroidsScene.setFill(Color.BLACK);
        this.removeAsteroid();
        this.updateScoreboard();
    }

    /**
     * This method returns the {@code Group} object of this AsteroidsGame.
     *
     *@return group the group for the game.
     */
    public Group getGroup() {
        return group;
    }

    /**
     * This method presents the final score when the ship runs out of lives.
     */
    public void presentFinalScore() {
        finText = "Final Score: " + score;
        finalScore.setText(finText);
        finalScore.setFont(new Font("Comic Sans", 25.0));
        finalScore.setTextFill(Color.WHITE);
        group.getChildren().add(finalScore);
        finalScore.setTranslateX(260.0);
        finalScore.setTranslateY(240.0);
    }

    /**
     * This method updates the scoreboard every tenth of a second.
     */
    public void updateScoreboard() {
        EventHandler<ActionEvent> update = e -> {
            scoreText = "Score: " + score;
            levelText = "\tLevel: " + level;
            lifeText = "\tLives: " + ship.getLives();
            gameInfo = scoreText + levelText + lifeText;
            scoreboard.setText(gameInfo);
        };
        Duration dur = new Duration(100.0);
        KeyFrame kf = new KeyFrame(dur, update);
        Timeline tm = new Timeline();
        tm.setCycleCount(Timeline.INDEFINITE);
        tm.getKeyFrames().add(kf);
        tm.play();
    }

    /**
     * This method returns the score of this game.
     *
     *@return score the integer value of this game's score
     */
    public int getScore() {
        return score;
    }

    /**
     * This method sets the score to the parameter.
     *
     *@param s an integer value for the score
     */
    public void setScore(int s) {
        score = s;
    }

    /**
     * This method returns the integer value of what level the game is on.
     *
     *@return level the level this game is on
     */
    public int getLevel() {
        return level;
    }

    /**
     * This method sets the level for the game to the value passed in.
     *
     *@param lev an integer level
     */
    public void setLevel(int lev) {
        level = lev;
    }

    /**
     * This method returns the Asteroid array of this game.
     *
     *@return ast an array of Asteroids
     */
    public Asteroid[] getAsteroids() {
        return ast;
    }

    /**
     * This method removes any asteroids that have been hit every two seconds.
     */
    public void removeAsteroid() {
        EventHandler<ActionEvent> remove = e -> {
            for (int i = 0; i < ast.length; i++) {
                if (ast[i].getHitCount() >= 1) {
                    group.getChildren().remove(ast[i]);
                    if (this.isOutOfAsteroids()) {
                        level++;
                        ship.resetPos();
                        this.refillAsteroids();
                    }
                }
            }
        };
        Duration dur = new Duration(2000.0);
        KeyFrame kf = new KeyFrame(dur, remove);
        Timeline tm = new Timeline();
        tm.setCycleCount(Timeline.INDEFINITE);
        tm.getKeyFrames().add(kf);
        tm.play();
    }

    /**
     * This method refills the game with more asteroids when they have all been hit.
     */
    public void refillAsteroids() {
        int newSize = ast.length + 3;
        ast = new Asteroid[newSize];
        for (int i = 0; i < ast.length; i++) {
            double length = 0.0;
            int randSize = (int) (Math.random() * 3);
            switch (randSize) {
            case 0:
                length = 15.0;
                break;
            case 1:
                length = 30.0;
                break;
            case 2:
                length = 60.0;
                break;
            }
            ast[i] = new Asteroid(length, ship, this);
            ast[i].getTimeLine().play();
        }
        group.getChildren().addAll(ast);
        ship.setCrashed(true);
    }

    /**
     * Returns whether or not this game is out of Asteroids so they can
     * be refilled.
     *
     *@return empty a boolean which returns true if this game's
     * group has no asteroids in it.
     */
    public boolean isOutOfAsteroids() {
        boolean empty = true;
        for (int i = 0; i < ast.length; i++) {
            if (group.getChildren().contains(ast[i])) {
                empty = false;
                break;
            }
        }
        return empty;
    }

    /**
     * This method returns an EventHandler of type KeyEvent that: quits the game (Q)
     * moves the ship forward (UP), rotates (RIGHT and LEFT), shoots (SPACEBAR),
     * and sends the ship into hyperspace by resetting its location randomly (D).
     *
     *@return an EventHandler of type KeyEvent.
     */
    private EventHandler<? super KeyEvent> moveShip() {
        return event -> {
            switch (event.getCode()) {
            case UP:
                if (ship.getMove()) {
                    Double radAng = Math.toRadians(ship.getAngle());
                    Double x = 10.0 * Math.cos(radAng); // amt to move by on x axis
                    Double y = 10.0 * Math.sin(radAng); // amt to move by on y axis
                    ship.setTranslateX(ship.getTranslateX() + x);
                    ship.setTranslateY(ship.getTranslateY() - y);
                    ship.flip();
                }
                break;
            case RIGHT:
                if (ship.getMove()) {
                    shipCenter = ship.getCenter();
                    right = new Rotate(15.0, shipCenter.getX(), shipCenter.getY());
                    ship.addAngle(-15.0);
                    ship.getTransforms().add(right);
                }
                break;
            case LEFT:
                if (ship.getMove()) {
                    shipCenter = ship.getCenter();
                    left = new Rotate(-15.0, shipCenter.getX(), shipCenter.getY());
                    ship.addAngle(15.0);
                    ship.getTransforms().add(left);
                }
                break;
            case SPACE:
                if (ship.getMove()) {
                    Bullet b = ship.shoot(ast, group, this);
                    group.getChildren().add(b);
                    b.fly(ship);
                }
                break;
            case D:
                if (ship.getMove()) {
                    Double transX = (Math.random() * 480.0) - 240.0;
                    Double transY = (Math.random() * 640.0) - 320.0;
                    ship.setTranslateX(transX);
                    ship.setTranslateY(transY);
                }
                break;
            case Q:
                group.getChildren().clear();
                ship.setLives(5);
                score = 0;
                level = 1;
                stage.setScene(switchBack);
                break;
            } // switch
        };
    }

    /**
     * This method returns the scene of this game.
     *
     *@return asteroidsScene
     */
    public Scene getScene() {
        return asteroidsScene;
    } //getScene

    /**
     * This method swaps the scene for the main stage of ArcadeApp.
     *
     *@param stage the stage of ArcadeApp
     *@param scene the scene for AsteroidsGame
     */
    public void getSwitch(Stage stage, Scene scene) {
        this.stage = stage;
        switchBack = scene;
    } //getSwitch

} // AsteroidsGame
