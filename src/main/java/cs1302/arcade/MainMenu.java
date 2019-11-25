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
    String[] art = {
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
    public MainMenu() {
        welcome = new Text(welcText);
        chess = new Text(chessText);
        asteroids = new Text(astText);
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
        vbox = new VBox(welcome, chess, asteroids, quit, artText);
        root = new HBox(vbox);
        menuScene = new Scene(root, 640, 480);
    }
    public void updateText() {
        if(count >= art.length) {
            count = 0;
        } //if
        artText.setText(art[count]);
        count++;
    } //updateText
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
