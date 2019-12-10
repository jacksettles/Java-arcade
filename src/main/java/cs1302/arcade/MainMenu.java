package cs1302.arcade;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.event.*;
import javafx.util.Duration;
import javafx.scene.text.TextAlignment;

/**
 * This class represents the main menu of ArcadeApp.
 */
public class MainMenu {

    Stage stage;
    Scene menuScene;
    Scene switchBack;
    HBox root;
    VBox vbox;
    Text welcome;
    Text chess;
    Text asteroids;
    Text controls;
    Text quit;
    String welcText = "Welcome to AverageCodeGuys Arcade!";
    String chessText = "Press '1' to play Chess";
    String astText = "Press '2' to play Asteroids";
    String controlText = "Press '3' to see game controls";
    String quitText = "Press 'Q' to exit";
    String[] art = { //FOR WHATEVER REASON THIS BELOW IS WHAT PASSES CHECKSTYLE
        "                         <>\n" +
            "                        <==>\n" +
            "                       <====>\n",
        "                      <======>\n" +
            "                     <==={}===>\n" +
            "                    <==={..}===>\n",
        "                   <==={....}===>\n" +
            "                  <==={......}===>\n" +
            "                 <==={...()...}===>\n",
        "                <==={...{()}...}===>\n" +
            "               <==={...{{()}}...}===>\n" +
            "              <==={...{{{()}}}...}===>\n",
        "             <==={...{{{(@@)}}}...}===>\n" +
            "            <==={...{{{(@@@@)}}}...}===>\n" +
            "           <==={...{{{(@@**@@)}}}...}===>\n",
        "          <==={...{{{(@@****@@)}}}...}===>\n" +
            "         <==={...{{{(@@**##**@@)}}}...}===>\n" +
            "        <==={...{{{(@@**####**@@)}}}...}===>\n",
        "       <==={...{{{(@@**##<>##**@@)}}}...}===>\n" +
            "      <==={...{{{(@@**##<==>##**@@)}}}...}===>\n" +
            "     <==={...{{{(@@**##<====>##**@@)}}}...}===>\n",
        "    <==={...{{{(@@**##<======>##**@@)}}}...}===>\n" +
            "   <==={...{{{(@@**##<==={}===>##**@@)}}}...}===>\n" +
            "  <==={...{{{(@@**##<==={..}===>##**@@)}}}...}===>\n",
        " <==={...{{{(@@**##<==={....}===>##**@@)}}}...}===>\n" +
            "<==={...{{{(@@**##<==={......}===>##**@@)}}}...}===>\n" +
            "==={...{{{(@@**##<==={...()...}===>##**@@)}}}...}===\n",
        "=={...{{{(@@**##<==={...{()}...}===>##**@@)}}}...}==\n" +
            "={...{{{(@@**##<==={...{{()}}...}===>##**@@)}}}...}=\n" +
            "{...{{{(@@**##<==={...{{{()}}}...}===>##**@@)}}}...}\n",
        "...{{{(@@**##<==={...{{{(@@)}}}...}===>##**@@)}}}...\n" +
            "..{{{(@@**##<==={...{{{(@@@@)}}}...}===>##**@@)}}}..\n" +
            ".{{{(@@**##<==={...{{{(@@**@@)}}}...}===>##**@@)}}}.\n",
        "{{{(@@**##<==={...{{{(@@****@@)}}}...}===>##**@@)}}}\n" +
            "{{(@@**##<==={...{{{(@@**##**@@)}}}...}===>##**@@)}}\n" +
            "{(@@**##<==={...{{{(@@**####**@@)}}}...}===>##**@@)}\n",
        "(@@**##<==={...{{{(@@**##<>##**@@)}}}...}===>##**@@)\n" +
            "@@**##<==={...{{{(@@**##<==>##**@@)}}}...}===>##**@@\n" +
            "@**##<==={...{{{(@@**##<====>##**@@)}}}...}===>##**@\n",
        "**##<==={...{{{(@@**##<======>##**@@)}}}...}===>##**\n" +
            "*##<==={...{{{(@@**##<==={}===>##**@@)}}}...}===>##*\n" +
            "##<==={...{{{(@@**##<==={..}===>##**@@)}}}...}===>##\n",
        "#<==={...{{{(@@**##<==={....}===>##**@@)}}}...}===>#\n" +
            "<==={...{{{(@@**##<==={......}===>##**@@)}}}...}===>\n" +
            "==={...{{{(@@**##<==={...()...}===>##**@@)}}}...}===\n",
        "=={...{{{(@@**##<==={...{()}...}===>##**@@)}}}...}==\n" +
            "={...{{{(@@**##<==={...{{()}}...}===>##**@@)}}}...}=\n" +
            "{...{{{(@@**##<==={...{{{()}}}...}===>##**@@)}}}...}\n"
        };
    Text artText = new Text(art[0]);
    int count = 1;
    Timeline timeline;

    /**
     * This constructor sets up the main menu.
     */
    public MainMenu() {
        welcome = new Text(welcText);
        chess = new Text(chessText);
        asteroids = new Text(astText);
        controls = new Text(controlText);
        quit = new Text(quitText);
        EventHandler<ActionEvent> handler = event -> {
            updateText();
        };
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.5), handler);
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
        artText.setTextAlignment(TextAlignment.CENTER);
        vbox = new VBox(welcome, chess, asteroids, controls, quit, artText);
        root = new HBox(vbox);
        menuScene = new Scene(root, 640, 480);
    }

    /**
     * This method updates the text animation for the main menu.
     */

    public void updateText() {
        if (count >= art.length) {
            count = 0;
        } //if
        artText.setText(art[count]);
        count++;
    } //updateText

    /**
     * This method returns the root of the main menu.
     *@return root an HBox which is the root of the main menu
     */

    public HBox getRoot() {
        return root;
    }

    /**
     * This method returns the Scene of this menu.
     *@return menuScene the scene of this main menu
     */

    public Scene getScene() {
        return menuScene;
    }

    /**
     * This method swaps the scene for the main stage of ArcadeApp.
     *@param stage a stage object from ArcadeApp
     *@param scene a Scene object
     */

    public void getSwitch(Stage stage, Scene scene) {
        this.stage = stage;
        switchBack = scene;
    }

} // MainMenu
