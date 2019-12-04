package cs1302.arcade;

import javafx.scene.layout.GridPane;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class King extends ChessPiece {

    GridPane chessGrid;

    public King(boolean isWhite, int row, int col, GridPane chessGrid, ChessPiece[][] board) {
        super(isWhite, row, col, chessGrid, board);
        this.chessGrid = chessGrid;
        this.getRect().setOnMouseClicked(move());
    } //King

    private EventHandler<? super MouseEvent> move() {
        return event -> {
            if (this.canMove()) {
            chessGrid.getChildren().remove(r);
            } //if
        };
    } //move

    public boolean canMove() {
        //TODO: Movement variables
        return true;
    } //canMove

} //King
