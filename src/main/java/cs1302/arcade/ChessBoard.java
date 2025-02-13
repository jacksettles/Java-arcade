package cs1302.arcade;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
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
import cs1302.arcade.Pawn;
import cs1302.arcade.Rook;
import javafx.scene.image.Image;
import javafx.scene.text.Text;

/**
 *Sets up chess board scene with chessPiece objs.
 */

public class ChessBoard {

    Stage stage;
    Scene chessScene;
    Scene switchBack;
    GridPane chessGrid = new GridPane();
    final int boardSize = 8;
    ChessPiece c;
    Pawn[] pawnsWhite = new Pawn[8];
    Pawn[] pawnsBlack = new Pawn[8];
    ChessPiece[][] board = new ChessPiece[8][8];
    VBox frame = new VBox();
    HBox heading = new HBox();
    HBox footing = new HBox();
    Text whiteScore = new Text("0");
    Text blackScore = new Text("0");
    int wScore = 0;
    int bScore = 0;

    /**
     *Chess Board constructor.
     *Sets up gridPane and initializes pieces.
     */

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
            chessGrid.getColumnConstraints().add(new ColumnConstraints(480 / 8, 560 / 8, 720 / 8));
            chessGrid.getRowConstraints().add(new RowConstraints(480 / 8, 560 / 8, 720 / 8));
        } //for
        footing.getChildren().addAll(new Text("White Score: "), whiteScore);
        heading.getChildren().addAll(new Text("Black Score: "), blackScore);
        //Piecs add Test
        addPieces();
        frame.getChildren().addAll(heading, chessGrid, footing);
        chessScene = new Scene(frame, 560, 590);
        chessScene.setOnKeyPressed(e -> { //Able to go back and forth from main to chess
            if (e.getCode() == KeyCode.Q) {
                stage.setScene(switchBack);
            } //if
        });
    } //ChessBoard Construct

    /**
     *Fetches the chessScene.
     *@return the chess Scene.
     */

    public Scene getScene() {
        return chessScene;
    } //getScene

    /**
     *Fetches menu scene to swap out and gets stage for sending in chess scene.
     *@param stage for swapping back.
     *@param scene for swapping back.
     */

    public void getSwitch(Stage stage, Scene scene) {
        this.stage = stage;
        switchBack = scene;
    } //getSwitch

    /**
     * Adds all the pieces.
     * 8 pawns each.
     * Rook, Knight, Bishop, Queen, King for each side.
     */

    public void addPieces() {
        for (int i = 0; i < boardSize; i++) { //Black Pawns
            pawnsBlack[i] = new Pawn(false, 1, i, chessGrid, board, false, blackScore);
            chessGrid.add(pawnsBlack[i].getRect(), pawnsBlack[i].getCol(), pawnsBlack[i].getRow());
            board[1][i] = pawnsBlack[i];
        } //for
        for (int i = 0; i < boardSize; i++) { //White Pawns
            pawnsWhite[i] = new Pawn(true, 6, i, chessGrid, board, false, whiteScore);
            chessGrid.add(pawnsWhite[i].getRect(), pawnsWhite[i].getCol(), pawnsWhite[i].getRow());
            board[6][i] = pawnsWhite[i];
        } //for
        Rook rookLW = new Rook(true, 7, 0, chessGrid, board, false, whiteScore); //White pieces
        chessGrid.add(rookLW.getRect(), rookLW.getCol(), rookLW.getRow());
        board[7][0] = rookLW;
        Rook rookRW = new Rook(true, 7, 7, chessGrid, board, false, whiteScore);
        chessGrid.add(rookRW.getRect(), rookRW.getCol(), rookRW.getRow());
        board[7][7] = rookRW;
        Knight knightLW = new Knight(true, 7, 1, chessGrid, board, false, whiteScore);
        chessGrid.add(knightLW.getRect(), knightLW.getCol(), knightLW.getRow());
        board[7][1] = knightLW;
        Knight knightRW = new Knight(true, 7, 6, chessGrid, board, false, whiteScore);
        chessGrid.add(knightRW.getRect(), knightRW.getCol(), knightRW.getRow());
        board[7][6] = knightRW;
        Bishop bishopLW = new Bishop(true, 7, 2, chessGrid, board, false, whiteScore);
        chessGrid.add(bishopLW.getRect(), bishopLW.getCol(), bishopLW.getRow());
        board[7][2] = bishopLW;
        Bishop bishopRW = new Bishop(true, 7, 5, chessGrid, board, false, whiteScore);
        chessGrid.add(bishopRW.getRect(), bishopRW.getCol(), bishopRW.getRow());
        board[7][5] = bishopRW;
        King kingW = new King(true, 7, 4, chessGrid, board, true, whiteScore);
        chessGrid.add(kingW.getRect(), kingW.getCol(), kingW.getRow());
        board[7][4] = kingW;
        Queen queenW = new Queen(true, 7, 3, chessGrid, board, false, whiteScore);
        chessGrid.add(queenW.getRect(), queenW.getCol(), queenW.getRow());
        board[7][3] = queenW;
        Rook rookLB = new Rook(false, 0, 0, chessGrid, board, false, blackScore); //Now black peices
        chessGrid.add(rookLB.getRect(), rookLB.getCol(), rookLB.getRow());
        board[0][0] = rookLB;
        Rook rookRB = new Rook(false, 0, 7, chessGrid, board, false, blackScore);
        chessGrid.add(rookRB.getRect(), rookRB.getCol(), rookRB.getRow());
        board[0][7] = rookRB;
        Knight knightLB = new Knight(false, 0, 1, chessGrid, board, false, blackScore);
        chessGrid.add(knightLB.getRect(), knightLB.getCol(), knightLB.getRow());
        board[0][1] = knightLB;
        Knight knightRB = new Knight(false, 0, 6, chessGrid, board, false, blackScore);
        chessGrid.add(knightRB.getRect(), knightRB.getCol(), knightRB.getRow());
        board[0][6] = knightRB;
        Bishop bishopLB = new Bishop(false, 0, 2, chessGrid, board, false, blackScore);
        chessGrid.add(bishopLB.getRect(), bishopLB.getCol(), bishopLB.getRow());
        board[0][2] = bishopLB;
        Bishop bishopRB = new Bishop(false, 0, 5, chessGrid, board, false, blackScore);
        chessGrid.add(bishopRB.getRect(), bishopRB.getCol(), bishopRB.getRow());
        board[0][5] = bishopRB;
        King kingB = new King(false, 0, 4, chessGrid, board, true, blackScore);
        chessGrid.add(kingB.getRect(), kingB.getCol(), kingB.getRow());
        board[0][4] = kingB;
        Queen queenB = new Queen(false, 0, 3, chessGrid, board, false, blackScore);
        chessGrid.add(queenB.getRect(), queenB.getCol(), queenB.getRow());
        board[0][3] = queenB;
    } //addPeices
} //ChessBoard
