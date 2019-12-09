package cs1302.arcade;

import javafx.scene.layout.GridPane;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class King extends ChessPiece {

    GridPane chessGrid;
    ChessPiece[][] board;
    Rectangle[] possibleMoves = new Rectangle[8];
    Image imgW = new Image("akiross-Chess-Set-6.png");
    Image imgB = new Image("akiross-Chess-Set-12.png");
    ImagePattern imgPW = new ImagePattern(imgW);
    ImagePattern imgPB = new ImagePattern(imgB);

    public King(boolean isWhite, int row, int col, GridPane chessGrid, ChessPiece[][] board) {
        super(isWhite, row, col, chessGrid, board);
        this.chessGrid = chessGrid;
        this.board = board;
        this.getRect().setOnMouseClicked(move());
        if (this.isWhite()) {
            this.getRect().setFill(imgPW);
        } else {
            this.getRect().setFill(imgPB);
        } //if
        for (int i = 0; i < 8; i++) { //8 max possible moves for a king
            possibleMoves[i] = new Rectangle(40, 40, Color.GRAY);
            possibleMoves[i].setOnMouseClicked(replace(i));
        } //for
    } //King

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
        boolean stopLoop = false;
        this.board = this.getBoard();
        int index = 0;
        if (this.getRow() + 1 < 8) {
            if (this.board[this.getRow() + 1][this.getCol()] == null) {
                chessGrid.add(possibleMoves[0], this.getCol(), this.getRow() + 1);
                moved = true;
            } else if (this.board[this.getRow() + 1][this.getCol()].isWhite() != this.isWhite()) {
                chessGrid.add(possibleMoves[0], this.getCol(), this.getRow() + 1);
                moved = true;
            } //if
            if (this.getCol() + 1 < 8) {
                if (this.board[this.getRow() + 1][this.getCol() + 1] == null) {
                    chessGrid.add(possibleMoves[1], this.getCol() + 1, this.getRow() + 1);
                    moved = true;
                } else if (this.board[this.getRow() + 1][this.getCol() + 1].isWhite() != this.isWhite()) {
                    chessGrid.add(possibleMoves[1], this.getCol() + 1, this.getRow() + 1);
                    moved = true;
                } //if
            } //if
            if (this.getCol() - 1 >= 0) {
                if (this.board[this.getRow() + 1][this.getCol() - 1] == null) {
                    chessGrid.add(possibleMoves[2], this.getCol() - 1, this.getRow() + 1);
                    moved = true;
                } else if (this.board[this.getRow() + 1][this.getCol() - 1].isWhite() != this.isWhite()) {
                    chessGrid.add(possibleMoves[2], this.getCol() - 1, this.getRow() + 1);
                    moved = true;
                } //if
            } //if
        } //bounds

        if (this.getRow() - 1 >= 0) {
            if (this.board[this.getRow() - 1][this.getCol()] == null) {
                chessGrid.add(possibleMoves[3], this.getCol(), this.getRow() - 1);
                moved = true;
            } else if (this.board[this.getRow() - 1][this.getCol()].isWhite() != this.isWhite()) {
                chessGrid.add(possibleMoves[3], this.getCol(), this.getRow() - 1);
                moved = true;
            } //if
            if (this.getCol() + 1 < 8) {
                if (this.board[this.getRow() - 1][this.getCol() + 1] == null) {
                    chessGrid.add(possibleMoves[4], this.getCol() + 1, this.getRow() - 1);
                    moved = true;
                } else if (this.board[this.getRow() - 1][this.getCol() + 1].isWhite() != this.isWhite()) {
                    chessGrid.add(possibleMoves[4], this.getCol() + 1, this.getRow() - 1);
                    moved = true;
                } //if
            } //if
            if (this.getCol() - 1 >= 0) {
                if (this.board[this.getRow() - 1][this.getCol() - 1] == null) {
                    chessGrid.add(possibleMoves[5], this.getCol() - 1, this.getRow() - 1);
                    moved = true;
                } else if (this.board[this.getRow() - 1][this.getCol() - 1].isWhite() != this.isWhite()) {
                    chessGrid.add(possibleMoves[5], this.getCol() - 1, this.getRow() - 1);
                    moved = true;
                } //if
            } //if
        } //bounds

        if (this.getCol() + 1 < 8) {
            if (this.board[this.getRow()][this.getCol() + 1] == null) {
                chessGrid.add(possibleMoves[6], this.getCol() + 1, this.getRow());
                moved = true;
            } else if (this.board[this.getRow()][this.getCol() + 1].isWhite() != this.isWhite()) {
                chessGrid.add(possibleMoves[6], this.getCol() + 1, this.getRow());
                moved = true;
            } //if
        } //if
        if (this.getCol() - 1 >= 0) {
            if (this.board[this.getRow()][this.getCol() - 1] == null) {
                chessGrid.add(possibleMoves[7], this.getCol() - 1, this.getRow());
                moved = true;
            } else if (this.board[this.getRow()][this.getCol() - 1].isWhite() != this.isWhite()) {
                chessGrid.add(possibleMoves[7], this.getCol() - 1, this.getRow());
                moved = true;
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



} //King
