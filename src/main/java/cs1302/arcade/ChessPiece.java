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
import javafx.scene.text.Text;

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
    public boolean isKing;
    public boolean isCheck = false;
    public ChessPiece[] possibleMoves;
    public Text score;
    public int valScore = 0;
    public int val;

    public ChessPiece(boolean isWhite, int row, int col, GridPane chessGrid,
                      ChessPiece[][] board, boolean isKing, Text score, int val) {
        this.chessGrid = chessGrid;
        this.isWhite = isWhite;
        this.row = row;
        this.col = col;
        this.board = board;
        this.isKing = isKing;
        this.score = score;
        this.val = val;
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

    public boolean isKing() {
        return isKing;
    } //isKing

    public boolean isCheck() {
        return isCheck;
    } //isKing

    public void setCheck(boolean check) {
        this.isCheck = check;
    } //setCheck

    public void setPM(ChessPiece[] pm) {
        this.possibleMoves = pm;
    } //setPM

    public void setScore() {
        this.score.setText("" + valScore);
    } //setScore

    public void setValScore(int val) {
        valScore += val;
    } //setVS

    public int getVal() {
        return val;
    } //
} //ChessPiece
