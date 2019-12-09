package cs1302.arcade;

import javafx.scene.layout.GridPane;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class Rook extends ChessPiece {

    GridPane chessGrid;
    ChessPiece[][] board;
    Rectangle[] possibleMoves = new Rectangle[14];
    Image imgW = new Image("akiross-Chess-Set-2.png");
    Image imgB = new Image("akiross-Chess-Set-8.png");
    ImagePattern imgPW = new ImagePattern(imgW);
    ImagePattern imgPB = new ImagePattern(imgB);


    public Rook(boolean isWhite, int row, int col, GridPane chessGrid, ChessPiece[][] board) {
        super(isWhite, row, col, chessGrid, board);
        this.chessGrid = chessGrid;
        this.board = board;
        this.getRect().setOnMouseClicked(move());
        if (this.isWhite()) {
            this.getRect().setFill(imgPW);
        } else {
            this.getRect().setFill(imgPB);
        } //if
        for (int i = 0; i < 14; i++) { //14 max possible moves for a Rook
            possibleMoves[i] = new Rectangle(40, 40, Color.GRAY);
            possibleMoves[i].setOnMouseClicked(replace(i));
        } //for
    } //Rook

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
            for(int i = 0; i < 14; i++) {
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
        int bCol = this.getCol();
        int bRow = this.getRow();
        for (int row = bRow; row < 8; row++) { //Straight down row
            if (!stopLoop && bRow != row) {
                if (this.board[row][bCol] == null) {
                    chessGrid.add(possibleMoves[index], bCol, row);
                    moved = true;
                    index++;
                } else if (this.board[row][bCol].isWhite() != this.isWhite()) {
                    chessGrid.add(possibleMoves[index], bCol, row);
                    moved = true;
                    stopLoop = true;
                    index++;
                } else {
                    stopLoop = true;
                } //if
            } //if
        } //for
        stopLoop = false;
        for (int row = bRow; row >= 0; row--) { //Straight up row
            if (!stopLoop && bRow != row) {
                if (this.board[row][bCol] == null) {
                    chessGrid.add(possibleMoves[index], bCol, row);
                    moved = true;
                    index++;
                } else if (this.board[row][bCol].isWhite() != this.isWhite()) {
                    chessGrid.add(possibleMoves[index], bCol, row);
                    moved = true;
                    stopLoop = true;
                    index++;
                } else {
                    stopLoop = true;
                } //if
            } //if
        } //for
        stopLoop = false;
        for (int col = bCol; col < 8; col++) { //Straight down col
            if (!stopLoop && bCol != col) {
                if (this.board[bRow][col] == null) {
                    chessGrid.add(possibleMoves[index], col, bRow);
                    moved = true;
                    index++;
                } else if (this.board[bRow][col].isWhite() != this.isWhite()) {
                    chessGrid.add(possibleMoves[index], col, bRow);
                    moved = true;
                    stopLoop = true;
                    index++;
                } else {
                    stopLoop = true;
                } //if
            } //if
        } //for
        stopLoop = false;
        for (int col = bCol; col >= 0; col--) { //Straight up col
            if (!stopLoop && bCol != col) {
                if (this.board[bRow][col] == null) {
                    chessGrid.add(possibleMoves[index], col, bRow);
                    moved = true;
                    index++;
                } else if (this.board[bRow][col].isWhite() != this.isWhite()) {
                    chessGrid.add(possibleMoves[index], col, bRow);
                    moved = true;
                    stopLoop = true;
                    index++;
                } else {
                    stopLoop = true;
                } //if
            } //if
        } //for
        stopLoop = false;

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


} //Rook
