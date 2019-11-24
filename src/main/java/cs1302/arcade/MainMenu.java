package cs1302.arcade;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MainMenu {

    Stage stage;
    Scene menuScene;
    Scene switchBack;
    HBox root;
    VBox vbox;
    Text welcome;
    Text chess;
    Text asteroids;
    Text quit;
    String welcText = "Welcome to AverageCodeGuys Arcade!";
    String chessText = "Press '1' to play Chess";
    String astText = "Press '2' to play Asteroids";
    String quitText = "Press 'Q' to exit";

    public MainMenu() {
        welcome = new Text(welcText);
        chess = new Text(chessText);
        asteroids = new Text(astText);
        quit = new Text(quitText);
        vbox = new VBox(welcome, chess, asteroids, quit);
        root = new HBox(vbox);
        menuScene = new Scene(root, 640, 480);
    }

    public HBox getRoot() {
        return root;
    }

    public Scene getScene() {
        return menuScene;
    }

    public void getSwitch(Stage stage, Scene scene) {
        this.stage = stage;
        switchBack = scene;
    }

} // MainMenu
