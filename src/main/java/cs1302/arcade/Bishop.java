package cs1302.arcade;

import javafx.scene.layout.GridPane;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class Bishop extends ChessPiece {

    GridPane chessGrid;

    public Bishop(boolean isWhite, int row, int col, GridPane chessGrid, ChessPiece[][] board) {
        super(isWhite, row, col, chessGrid, board);
        this.chessGrid = chessGrid;
        this.getRect().setOnMouseClicked(move());
    } //Bishop

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

} //Bishop
