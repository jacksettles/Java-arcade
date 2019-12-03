package cs1302.arcade;

import javafx.scene.layout.GridPane;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class Knight extends ChessPiece {

    GridPane chessGrid;

    public Knight(boolean isWhite, int row, int col, GridPane chessGrid) {
        super(isWhite, row, col, chessGrid);
        this.chessGrid = chessGrid;
        this.getRect().setOnMouseClicked(move());
    } //Knight

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

} //Knight
