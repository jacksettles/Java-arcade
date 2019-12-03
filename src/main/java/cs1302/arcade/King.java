package cs1302.arcade;

import javafx.scene.layout.GridPane;

public class King extends ChessPiece {

    public King(boolean isWhite, int row, int col, GridPane chessGrid) {
        super(isWhite, row, col, chessGrid);
    } //King

    public boolean canMove() {
        //TODO: Movement variables
        return false;
    } //canMove

} //King
