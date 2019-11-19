package cs1302.arcade;

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
import cs1302.arcade.ChessPiece;
import javafx.scene.shape.Rectangle;

public class ChessBoard {
    Stage stage;
    Scene chessScene;
    Scene switchBack;
    GridPane chessGrid = new GridPane();
    final int boardSize = 8;
    public ChessBoard() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                StackPane stack = new StackPane();
                if ((i + j) % 2 == 0) { //Colors picked at RapidTables RGB Color Codes Chart
                    stack.setStyle("-fx-background-color: #FFD7A2");
                } else {
                    stack.setStyle("-fx-background-color: #8A5A1B");
                } //if
                chessGrid.add(stack, j, i);
            } //for
        } //for
        // Wouldn't work for the longest time with just code above
        // Had to add constraints to see Color.
        for (int i = 0; i < boardSize; i++) {
            chessGrid.getColumnConstraints().add(new ColumnConstraints(480/8, 560/8, 720/8));
            chessGrid.getRowConstraints().add(new RowConstraints(480/8, 560/8, 720/8));
        } //for

        //Piecs add Test
        for (int i = 0; i < boardSize; i++) {
            ChessPiece c = new ChessPiece(false, 0, i);
            chessGrid.add(c.getRect(), c.getCol(), c.getRow());
            c = new ChessPiece(false, 1, i);
            chessGrid.add(c.getRect(), c.getCol(), c.getRow());
        } //for
        for (int i = 0; i < boardSize; i++) {
            ChessPiece c = new ChessPiece(true, 7, i);
            chessGrid.add(c.getRect(), c.getCol(), c.getRow());
            c = new ChessPiece(true, 6, i);
            chessGrid.add(c.getRect(), c.getCol(), c.getRow());
        } //for

        chessScene = new Scene(chessGrid, 560, 560);
        chessScene.setOnKeyPressed(e -> { //Able to go back and forth from main to chess
                if (e.getCode() == KeyCode.Q) {
                    stage.setScene(switchBack);
                } //if
            });
    } //ChessBoard Construct

    public Scene getScene() {
        return chessScene;
    } //getScene

    public void getSwitch(Stage stage, Scene scene) {
        this.stage = stage;
        switchBack = scene;
    } //getSwitch

} //ChessBoard
