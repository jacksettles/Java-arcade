package cs1302.arcade;

import javafx.scene.layout.GridPane;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import java.util.Arrays;

public class Pawn extends ChessPiece {

    GridPane chessGrid;
    boolean firstMove = true;
    int newY;
    int newX;
    Rectangle[] possibleMoves = new Rectangle[4];
    ChessPiece[] pieces = new ChessPiece[3];
    ChessPiece[][] board = new ChessPiece[8][8];

    public Pawn(boolean isWhite, int row, int col, GridPane chessGrid, ChessPiece[][] board) {
        super(isWhite, row, col, chessGrid, board);
        this.chessGrid = chessGrid;
        this.getRect().setOnMouseClicked(move());
        for (int i = 0; i < 4; i++) {
            possibleMoves[i] = new Rectangle(40, 40, Color.GRAY);
            possibleMoves[i].setOnMouseClicked(replace(i));
        } //for
    } //Pawn

    private EventHandler<? super MouseEvent> move() {
        return event -> {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (board[i][j] != null) {
                        board[i][j].getRect().setDisable(true);
                    } //if
                } //for
            } //for
            canMove();
        }; //return
    } //move

    private EventHandler<? super MouseEvent> replace(int index) {
        return event -> {
            for(int i = 0; i < 4; i++) {
                if(i != index) {
                    chessGrid.getChildren().remove(possibleMoves[i]);
                } //if
            } //for
            chessGrid.getChildren().remove(this.getRect());
            this.board[row][col] = null;
            this.row = GridPane.getRowIndex(possibleMoves[index]);
            this.col = GridPane.getColumnIndex(possibleMoves[index]);
            chessGrid.getChildren().remove(possibleMoves[index]);
            this.setRow(row);
            this.setCol(col);
            chessGrid.add(this.getRect(), col, row);
            this.board[row][col] = this;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (this.board[i][j] != null) {
                        this.board[i][j].getRect().setDisable(false);
                        this.board[i][j].setBoard(this.board);
                    } //if
                } //for
            } //for
        }; //return
    } //move

    public void canMove() {
        boolean moved = false;
        boolean noJump = true;
        this.board = this.getBoard();
        System.out.println(Arrays.deepToString(this.board));
        if (isWhite) {
            if (firstMove) {
                if (this.board[this.getRow() - 1][this.getCol()] == null) {
                    chessGrid.add(possibleMoves[0], this.getCol(), this.getRow() - 1);
                    moved = true;
                } else {
                    noJump = false;
                } //if
                if (noJump) {
                    if (this.board[this.getRow() - 2][this.getCol()] == null) {
                        chessGrid.add(possibleMoves[1], this.getCol(), this.getRow() - 2);
                        moved = true;
                    } //if
                } //if
                if (moved) {
                    firstMove = false;
                } //if
            } else {
                if (this.board[this.getRow() - 1][this.getCol()] == null) {
                    chessGrid.add(possibleMoves[0], this.getCol(), this.getRow() - 1);
                    moved = true;
                } //if
            } //if
            this.setClicked(true);
        } else { //isBlack
            if (firstMove) {
                if (this.board[this.getRow() + 1][this.getCol()] == null) {
                    chessGrid.add(possibleMoves[0], this.getCol(), this.getRow() + 1);
                    moved = true;
                } else {
                    noJump = false;
                } //if
                if (noJump) {
                    if (this.board[this.getRow() + 2][this.getCol()] == null) {
                        chessGrid.add(possibleMoves[1], this.getCol(), this.getRow() + 2);
                        moved = true;
                    } //if
                } //if
                if (moved) {
                    firstMove = false;
                } //if
            } else {
                if (this.board[this.getRow() + 1][this.getCol()] == null) {
                    chessGrid.add(possibleMoves[0], this.getCol(), this.getRow() + 1);
                    moved = true;
                } //if
            } //if
        } //if
        if (!moved) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (this.board[i][j] != null) {
                        this.board[i][j].getRect().setDisable(false);
                        this.board[i][j].setBoard(this.board);
                    } //if
                } //for
            } //for
        } else {
            this.setClicked(true);
        } //if
    } //canMove

    public boolean hasPiece(int row, int col) {
        ChessPiece c = null;
        return false;
    } //hasPiece

} //Pawn
