package cs1302.arcade;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
public class ChessPiece {

    public boolean isWhite;
    public int row;
    public int col;
    public Rectangle r;

    public ChessPiece(boolean isWhite, int row, int col) {

        this.isWhite = isWhite;
        this.row = row;
        this.col = col;
        if (isWhite) {
            r = new Rectangle(40, 40, Color.WHITE);
        } else {
            r = new Rectangle(40, 40, Color.BLACK);
        } //if
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

} //ChessPiece
