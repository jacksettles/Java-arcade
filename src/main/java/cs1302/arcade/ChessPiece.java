package cs1302.arcade;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class ChessPiece {

    public boolean isWhite;
    public int row;
    public int col;
    public Rectangle r;
    public GridPane chessGrid;

    public ChessPiece(boolean isWhite, int row, int col, GridPane chessGrid) {

        this.chessGrid = chessGrid;
        this.isWhite = isWhite;
        this.row = row;
        this.col = col;
        if (isWhite) {
            r = new Rectangle(40, 40, Color.WHITE);
        } else {
            r = new Rectangle(40, 40, Color.BLACK);
        } //if
        r.setOnMouseClicked(move());
    } //ChessPConstruct

    public Rectangle getRect() {
        return r;
    } //getRect

    public boolean isWhite() {
        return isWhite;
    } //isWhite

    public void setRow(int row) {
        this.row = row;
    } //setRow

    public void setCol(int col) {
        this.col = col;
    } //setCol

    public int getRow() {
        return row;
    } //getRow

    public int getCol() {
        return col;
    } //getCol

    private EventHandler<? super MouseEvent> move() {
        //if(c.canMove()) {
        return event -> {
            chessGrid.getChildren().remove(this.r);
            //Work in Progress gotta figure out how to get pieces to be clicked
        };
        //} //if
    } //move

} //ChessPiece
