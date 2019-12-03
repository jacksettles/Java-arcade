package cs1302.arcade;

import javafx.scene.layout.GridPane;

public class Queen extends ChessPiece {

    public Queen(boolean isWhite, int row, int col, GridPane chessGrid) {
        super(isWhite, row, col, chessGrid);
    } //Queen

    public boolean canMove() {
        //TODO: Movement variables
        return false;
    } //canMove

} //Queen
