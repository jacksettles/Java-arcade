package cs1302.arcade;

import javafx.scene.layout.GridPane;

public class Pawn extends ChessPiece {

    public Pawn(boolean isWhite, int row, int col, GridPane chessGrid) {
        super(isWhite, row, col, chessGrid);
    } //Pawn

    public boolean canMove() {
        //TODO: Movement variables
        return true;
    } //canMove

} //Pawn
