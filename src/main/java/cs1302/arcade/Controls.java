package cs1302.arcade;


import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.event.ActionEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Controls {

    private Stage stage;
    private Scene controlsScene;
    private Scene switchBack;
    private VBox controlText;
    private HBox root;
    private Text asteroidsControls;
    private Text chessControls;
    private Text back;
    private String astString;
    private String chessString;
    private String backText;

    /**
     * Constructs an instance of a Controls object,
     * explaining the controls or both Chess and Asteroids.
     */
    public Controls() {
        root = new HBox();
        controlText = new VBox();
        astString = "**Asteroids Controls**\n"
            + "\tUP arrow key to move forward.\n"
            + "\tLEFT and RIGHT arrow keys to rotate left and right.\n"
            + "\tSPACEBAR to shoot at asteroids. Small asteroids are worth 75 points,\n"
            + "\tmedium ones are worth 50 points, and the large ones are worth 25 points.\n"
            + "\tD to send the ship into hyperspace "
            + "(at the risk of landing on another asteroid).\n"
            + "\tQ to quit the game and return to the main menu.\n\n"
            + "\t**NOTE: when the ship's position is reset to the middle,\n"
            + "\teither after being hit by an asteroid or leveling up,\n"
            + "\tit will be unaffected by asteroids for about 5 seconds.\n"
            + "\tThis is done so in case of there being an asteroid at the center,\n"
            + "\tthe player does not continue to 'crash' and lose lives.";
        chessString = "**Chess Controls**";
        backText = "\nPress 'Q' to go back to the main menu.";
        asteroidsControls = new Text(astString);
        chessControls = new Text(chessString);
        back = new Text(backText);
        controlText.getChildren().addAll(chessControls, asteroidsControls, back);
        root.getChildren().add(controlText);
        controlsScene = new Scene(root, 640, 480);
        controlsScene.setOnKeyPressed(back());
    }

    /**
     * This method returns the scene for the Controls class.
     *
     *@return controlsScene a scene object
     */
    public Scene getScene() {
        return controlsScene;
    }

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

    /**
     * This method returns an event handler that sends
     * the user back to the main menu when they press Q.
     *
     *@return an EventHandler of type KeyEvent
     */
    public EventHandler<? super KeyEvent> back() {
        return event -> {
            switch (event.getCode()) {
            case Q:
                stage.setScene(switchBack);
                break;
            }
        };
    }
} //Controls
