package cs1302.arcade;

import javafx.scene.layout.GridPane;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import java.util.Arrays;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class Pawn extends ChessPiece {

    GridPane chessGrid;
    boolean firstMove = true;
    int newY;
    int newX;
    Rectangle[] possibleMoves = new Rectangle[4];
    ChessPiece[] pieces = new ChessPiece[3];
    ChessPiece[][] board = new ChessPiece[8][8];
    Image imgW = new Image("akiross-Chess-Set-1.png");
    Image imgB = new Image("akiross-Chess-Set-7.png");
    ImagePattern imgPW = new ImagePattern(imgW);
    ImagePattern imgPB = new ImagePattern(imgB);

    public Pawn(boolean isWhite, int row, int col, GridPane chessGrid, ChessPiece[][] board, boolean isKing) {
        super(isWhite, row, col, chessGrid, board, isKing);
        this.chessGrid = chessGrid;
        this.board = board;
        this.getRect().setOnMouseClicked(move());
        if (this.isWhite()) {
            this.getRect().setFill(imgPW);
        } else {
            this.getRect().setFill(imgPB);
        } //if
        for (int i = 0; i < 4; i++) {
            possibleMoves[i] = new Rectangle(40, 40, Color.GRAY);
            possibleMoves[i].setOnMouseClicked(replace(i));
        } //for
    } //Pawn

    private EventHandler<? super MouseEvent> move() {
        return event -> {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (this.board[i][j] != null) {
                        this.board[i][j].getRect().setDisable(true);
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
            if (this.board[this.row][this.col] != null) {
                chessGrid.getChildren().remove(board[row][col].getRect());
            } //if
            this.setRow(row);
            this.setCol(col);
            chessGrid.add(this.getRect(), col, row);
            this.board[row][col] = this;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (this.board[i][j] != null && this.isWhite() != this.board[i][j].isWhite()) {
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
                if ((this.getRow() - 1) >= 0) {
                    if (this.board[this.getRow() - 1][this.getCol()] == null) {
                        chessGrid.add(possibleMoves[0], this.getCol(), this.getRow() - 1);
                        moved = true;
                    } //if
                } //bounds
            } //if
            if ((this.getRow() - 1) >= 0) {
                if (this.getCol() - 1 >= 0) {
                    if (this.board[this.getRow() - 1][this.getCol() - 1] != null) {
                        if(!this.board[this.getRow() - 1][this.getCol() - 1].isWhite()) {
                            chessGrid.add(possibleMoves[2], this.getCol() - 1, this.getRow() - 1);
                            moved = true;

                        } //check attack
                    } //check null
                } //bounds
                if (this.getCol() + 1 <= 7) {
                    if (this.board[this.getRow() - 1][this.getCol() + 1] != null) {
                        if (!this.board[this.getRow() - 1][this.getCol() + 1].isWhite()) {
                            chessGrid.add(possibleMoves[3], this.getCol() + 1, this.getRow() - 1);
                            moved = true;
                        } //check attack
                    } //check null
                } //bounds
            } //bounds
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
                if ((this.getRow() + 1) < 8) {
                    if (this.board[this.getRow() + 1][this.getCol()] == null) {
                        chessGrid.add(possibleMoves[0], this.getCol(), this.getRow() + 1);
                        moved = true;
                    } //if
                } //bounds
            } //if
            if ((this.getRow() + 1) < 8) {
                if (this.getCol() - 1 >= 0) {
                    if(this.board[this.getRow() + 1][this.getCol() - 1] != null) {
                        if(this.board[this.getRow() + 1][this.getCol() - 1].isWhite()) {
                            chessGrid.add(possibleMoves[2], this.getCol() - 1, this.getRow() + 1);
                            moved = true;
                        } //check attack
                    } //check null
                } //bounds
                if (this.getCol() + 1 < 8) {
                    if(this.board[this.getRow() + 1][this.getCol() + 1] != null) {
                        if(this.board[this.getRow() + 1][this.getCol() + 1].isWhite()) {
                            chessGrid.add(possibleMoves[3], this.getCol() + 1, this.getRow() + 1);
                            moved = true;
                        } //check attack
                    } //check null
                } //bounds
            } //bounds
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

} //Pawn
