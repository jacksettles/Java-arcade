package cs1302.arcade;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import cs1302.arcade.Rook;
import cs1302.arcade.Pawn;
import cs1302.arcade.Knight;
import cs1302.arcade.Bishop;
import cs1302.arcade.King;
import cs1302.arcade.Queen;

public class ChessPiece {

    public boolean isWhite;
    public int row;
    public int col;
    public Rectangle r;
    public Rectangle prevR;
    public GridPane chessGrid;
    public boolean whiteWent = false;
    public int xFrom;
    public int yFrom;
    public int xTo;
    public int yTo;
    public ChessPiece[][] board;
    public boolean isClicked = false;

    public ChessPiece(boolean isWhite, int row, int col, GridPane chessGrid, ChessPiece[][] board) {
        this.chessGrid = chessGrid;
        this.isWhite = isWhite;
        this.row = row;
        this.col = col;
        this.board = board;
        if (isWhite) {
            r = new Rectangle(70, 70, Color.WHITE);
        } else {
            r = new Rectangle(70, 70, Color.BLACK);
            r.setDisable(true);
        } //if
    } //ChessPConstruct

    public Rectangle getRect() {
        return r;
    } //getRect

    public void setRect(Rectangle r) {
        this.r = r;
    } //setRect

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

    public ChessPiece[][] getBoard() {
        return board;
    } //getB

    public void setClicked(boolean clicked) {
        this.isClicked = clicked;
    } //isClicked

    public boolean getClicked() {
        return isClicked;
    } //getClicked

    public void setBoard(ChessPiece[][] board) {
        this.board = board;
    } //setB

    public boolean getWhiteWent() {
        return whiteWent;
    } //getWW

    public void setWhiteWent(boolean ww) {
        this.whiteWent = ww;
    } //setWW

    public boolean whiteWentCheck() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j].getWhiteWent()) {
                    return true;
                } //if
            } //for
        } //for
        return false;
    } //wwCheck
} //ChessPiece
