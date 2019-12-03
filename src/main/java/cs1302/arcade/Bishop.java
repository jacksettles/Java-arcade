package cs1302.arcade;

import javafx.scene.layout.GridPane;

public class Bishop extends ChessPiece {

    public Bishop(boolean isWhite, int row, int col, GridPane chessGrid) {
        super(isWhite, row, col, chessGrid);
    } //Bishop

    public boolean canMove() {
        //TODO: Movement variables
        return true;
    } //canMove

} //Bishop
