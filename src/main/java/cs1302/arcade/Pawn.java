package cs1302.arcade;

import javafx.scene.layout.GridPane;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class Pawn extends ChessPiece {

    GridPane chessGrid;
    boolean firstMove = true;
    int newY;
    int newX;
    Rectangle[] possibleMoves = new Rectangle[4];


    public Pawn(boolean isWhite, int row, int col, GridPane chessGrid) {
        super(isWhite, row, col, chessGrid);
        this.chessGrid = chessGrid;
        this.getRect().setOnMouseClicked(move());
        for (int i = 0; i < 4; i++) {
            possibleMoves[i] = new Rectangle(40, 40, Color.GRAY);
            possibleMoves[i].setOnMouseClicked(replace(i));
        } //for
    } //Pawn

    private EventHandler<? super MouseEvent> move() {
        return event -> {
            canMove();
        };
    } //move

    private EventHandler<? super MouseEvent> replace(int index) {
        return event -> {
            for(int i = 0; i < 4; i++) {
                if(i != index) {
                    chessGrid.getChildren().remove(possibleMoves[i]);
                } //if
            } //for
            chessGrid.getChildren().remove(this.getRect());
            this.row = GridPane.getRowIndex(possibleMoves[index]);
            this.col = GridPane.getColumnIndex(possibleMoves[index]);
            chessGrid.getChildren().remove(possibleMoves[index]);
            this.setRow(row);
            this.setCol(col);
            chessGrid.add(this.getRect(), col, row);
        };
    } //move

    public void canMove() {
        if (isWhite) {
            if(firstMove) {
                chessGrid.add(possibleMoves[0], this.getCol(), this.getRow() - 1);
                chessGrid.add(possibleMoves[1], this.getCol(), this.getRow() - 2);
                firstMove = false;
            } else {
                chessGrid.add(possibleMoves[0], this.getCol(), this.getRow() - 1);
            } //if
        } else {
            if(firstMove) {
                chessGrid.add(possibleMoves[0], this.getCol(), this.getRow() + 1);
                chessGrid.add(possibleMoves[1], this.getCol(), this.getRow() + 2);
                firstMove = false;
            } else {
                chessGrid.add(possibleMoves[0], this.getCol(), this.getRow() + 1);
            } //if
        } //if
    } //canMove

    public boolean hasPiece(int row, int col) {
        ChessPiece c = null;
        return false;
    } //hasPiece

} //Pawn
