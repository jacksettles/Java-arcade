package cs1302.arcade;

import javafx.scene.layout.GridPane;

public class Knight extends ChessPiece {

    public Knight(boolean isWhite, int row, int col, GridPane chessGrid) {
        super(isWhite, row, col, chessGrid);
    } //Knight

    public boolean canMove() {
        //TODO: Movement variables
        return true;
    } //canMove

} //Knight
