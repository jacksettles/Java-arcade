package cs1302.arcade;

import javafx.scene.layout.GridPane;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.geometry.HPos;
import javafx.scene.text.Text;

public class Knight extends ChessPiece {

    GridPane chessGrid;
    ChessPiece[][] board;
    Rectangle[] possibleMoves = new Rectangle[8];
    Image imgW = new Image("akiross-Chess-Set-3.png");
    Image imgB = new Image("akiross-Chess-Set-9.png");
    ImagePattern imgPW = new ImagePattern(imgW);
    ImagePattern imgPB = new ImagePattern(imgB);

    public Knight(boolean isWhite, int row, int col, GridPane chessGrid,
                  ChessPiece[][] board, boolean isKing, Text score) {
        super(isWhite, row, col, chessGrid, board, isKing, score, 3);
        this.chessGrid = chessGrid;
        this.board = board;
        this.getRect().setOnMouseClicked(move());
        if (this.isWhite()) {
            this.getRect().setFill(imgPW);
        } else {
            this.getRect().setFill(imgPB);
        } //if
        for (int i = 0; i < 8; i++) {
            possibleMoves[i] = new Rectangle(40, 40, Color.GRAY);
            possibleMoves[i].setOnMouseClicked(replace(i));
            GridPane.setHalignment(possibleMoves[i], HPos.CENTER);
        } //for
    } //knight

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
            for(int i = 0; i < 8; i++) {
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
                //for (int i = 0; i < 8; i++) {
                //for (int j = 0; j < 8; j++) {
                //if (board[i][j] != null && this.isWhite() == board[i][j].isWhite()) {
                setValScore(board[this.row][this.col].getVal());
                //} //if
                //} //for
                // } //for
                setScore();
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
        this.board = this.getBoard();

        if (this.getRow() + 2 < 8) {
            if (this.getCol() + 1 < 8) {
                if (this.board[this.getRow() + 2][this.getCol() + 1] == null) {
                    chessGrid.add(possibleMoves[0], this.getCol() + 1, this.getRow() + 2);
                    moved = true;
                } else if (this.board[this.getRow() + 2][this.getCol() + 1].isWhite() != this.isWhite()) {
                    chessGrid.add(possibleMoves[0], this.getCol() + 1, this.getRow() + 2);
                    moved = true;
                    if (this.board[this.getRow() + 2][this.getCol() + 1].isKing()) {
                        this.board[this.getRow() + 2][this.getCol() + 1].setCheck(true);
                    } //if
                } //if
            } //if
            if (this.getCol() - 1 >= 0) {
                if (this.board[this.getRow() + 2][this.getCol() - 1] == null) {
                    chessGrid.add(possibleMoves[1], this.getCol() - 1, this.getRow() + 2);
                    moved = true;
                } else if (this.board[this.getRow() + 2][this.getCol() - 1].isWhite() != this.isWhite()) {
                    chessGrid.add(possibleMoves[1], this.getCol() - 1, this.getRow() + 2);
                    moved = true;
                    if (this.board[this.getRow() + 2][this.getCol() - 1].isKing()) {
                        this.board[this.getRow() + 2][this.getCol() - 1].setCheck(true);
                    } //if
                } //if
            } //if
        } //bounds

        if (this.getRow() - 2 >= 0) {
            if (this.getCol() + 1 < 8) {
                if (this.board[this.getRow() - 2][this.getCol() + 1] == null) {
                    chessGrid.add(possibleMoves[2], this.getCol() + 1, this.getRow() - 2);
                    moved = true;
                } else if (this.board[this.getRow() - 2][this.getCol() + 1].isWhite() != this.isWhite()) {
                    chessGrid.add(possibleMoves[2], this.getCol() + 1, this.getRow() - 2);
                    moved = true;
                    if (this.board[this.getRow() - 2][this.getCol() + 1].isKing()) {
                        this.board[this.getRow() - 2][this.getCol() + 1].setCheck(true);
                    } //if
                } //if
            } //if
            if (this.getCol() - 1 >= 0) {
                if (this.board[this.getRow() - 2][this.getCol() - 1] == null) {
                    chessGrid.add(possibleMoves[3], this.getCol() - 1, this.getRow() - 2);
                    moved = true;
                } else if (this.board[this.getRow() - 2][this.getCol() - 1].isWhite() != this.isWhite()) {
                    chessGrid.add(possibleMoves[3], this.getCol() - 1, this.getRow() - 2);
                    moved = true;
                    if (this.board[this.getRow() - 2][this.getCol() - 1].isKing()) {
                        this.board[this.getRow() - 2][this.getCol() - 1].setCheck(true);
                    } //if
                } //if
            } //if
        } //bounds

        if (this.getCol() + 2 < 8) {
            if (this.getRow() + 1 < 8) {
                if (this.board[this.getRow() + 1][this.getCol() + 2] == null) {
                    chessGrid.add(possibleMoves[4], this.getCol() + 2, this.getRow() + 1);
                    moved = true;
                } else if (this.board[this.getRow() + 1][this.getCol() + 2].isWhite() != this.isWhite()) {
                    chessGrid.add(possibleMoves[4], this.getCol() + 2, this.getRow() + 1);
                    moved = true;
                    if (this.board[this.getRow() + 1][this.getCol() + 2].isKing()) {
                        this.board[this.getRow() + 1][this.getCol() + 2].setCheck(true);
                    } //if
                } //if
            } //if
            if (this.getRow() - 1 >= 0) {
                if (this.board[this.getRow() - 1][this.getCol() + 2] == null) {
                    chessGrid.add(possibleMoves[5], this.getCol() + 2, this.getRow() - 1);
                    moved = true;
                } else if (this.board[this.getRow() - 1][this.getCol() + 2].isWhite() != this.isWhite()) {
                    chessGrid.add(possibleMoves[5], this.getCol() + 2, this.getRow() - 1);
                    moved = true;
                    if (this.board[this.getRow() - 1][this.getCol() + 2].isKing()) {
                        this.board[this.getRow() - 1][this.getCol() + 2].setCheck(true);
                    } //if
                } //if
            } //if
        } //bounds

        if (this.getCol() - 2 >= 0) {
            if (this.getRow() + 1 < 8) {
                if (this.board[this.getRow() + 1][this.getCol() - 2] == null) {
                    chessGrid.add(possibleMoves[6], this.getCol() - 2, this.getRow() + 1);
                    moved = true;
                } else if (this.board[this.getRow() + 1][this.getCol() - 2].isWhite() != this.isWhite()) {
                    chessGrid.add(possibleMoves[6], this.getCol() - 2, this.getRow() + 1);
                    moved = true;
                    if (this.board[this.getRow() + 1][this.getCol() - 2].isKing()) {
                        this.board[this.getRow() + 1][this.getCol() - 2].setCheck(true);
                    } //if
                } //if
            } //if
            if (this.getRow() - 1 >= 0) {
                if (this.board[this.getRow() - 1][this.getCol() - 2] == null) {
                    chessGrid.add(possibleMoves[7], this.getCol() - 2, this.getRow() - 1);
                    moved = true;
                } else if (this.board[this.getRow() - 1][this.getCol() - 2].isWhite() != this.isWhite()) {
                    chessGrid.add(possibleMoves[7], this.getCol() - 2, this.getRow() - 1);
                    moved = true;
                    if (this.board[this.getRow() - 1][this.getCol() - 2].isKing()) {
                        this.board[this.getRow() - 1][this.getCol() - 2].setCheck(true);
                    } //if
                } //if
            } //if
        } //bounds

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


} //Knight
