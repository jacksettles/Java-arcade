package cs1302.arcade;

import javafx.scene.layout.GridPane;

public class Rook extends ChessPiece {

    public Rook(boolean isWhite, int row, int col, GridPane chessGrid) {
        super(isWhite, row, col, chessGrid);
    } //Rook

    public boolean canMove() {
        //TODO: Movement variables
        return false;
    } //canMove

} //Rook
