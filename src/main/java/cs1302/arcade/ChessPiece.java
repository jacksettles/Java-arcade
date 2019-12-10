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

/**
 * The chess Piece parent class.
 */

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
    public int total = 0;
    public boolean[][] possibleBM = new boolean[8][8]; //board moves

    /**
     * Chess peice constructor.
     *@param isWhite true if isWhite false is black.
     *@param row what row in gridpane.
     *@param col what cal in gridpane.
     *@param chessGrid the gridpane it's self.
     *@param board stores position of pieces.
     *@param isKing tells if piece is a king.
     *@param score the text for changing for different team scores.
     *@param val the val of each piece.
     */

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
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                possibleBM[i][j] = false;
            } //for
        } //for
    } //ChessPConstruct

    /**
     *Gets the peices Rectangle.
     *@return the rectangle.
     */

    public Rectangle getRect() {
        return r;
    } //getRect

    /**
     *Sets the Rectangle of a piece.
     *@param r the rectangle.
     */

    public void setRect(Rectangle r) {
        this.r = r;
    } //setRect

    /**
     *Returns if peice is white.
     *@return isWhite returns isWhite.
     */

    public boolean isWhite() {
        return isWhite;
    } //isWhite

    /**
     *sets peices row.
     *@param row the row position.
     */

    public void setRow(int row) {
        this.row = row;
    } //setRow

    /**
     *sets peices col.
     *@param col the col position.
     */

    public void setCol(int col) {
        this.col = col;
    } //setCol

    /**
     *Gets the row.
     *@return row of piece.
     */

    public int getRow() {
        return row;
    } //getRow

    /**
     *Gets the row.
     *@return row of piece.
     */

    public int getCol() {
        return col;
    } //getCol

    /**
     *Gets the Board.
     *@return Board.
     */

    public ChessPiece[][] getBoard() {
        return board;
    } //getB

    /**
     * Sets if piece is clicked.
     *@param clicked boolean true if clicked.
     */

    public void setClicked(boolean clicked) {
        this.isClicked = clicked;
    } //isClicked

    /**
     *Gets boolean of clicked or not.
     *@return isClicked true if clicked.
     */

    public boolean getClicked() {
        return isClicked;
    } //getClicked

    /**
     *Sets the board of piece.
     *@param board the representation of the chess board.
     */

    public void setBoard(ChessPiece[][] board) {
        this.board = board;
    } //setB

    /**
     *Gets boolean of isKing.
     *@return isKing true if king.
     */

    public boolean isKing() {
        return isKing;
    } //isKing

    /**
     *Gets boolean of isCheck.
     *@return isCheck true if king in check.
     */

    public boolean isCheck() {
        return isCheck;
    } //isKing

    /**
     *Sets the boolean of is Check.
     *@param check sets isCheck to boolean of check.
     */

    public void setCheck(boolean check) {
        this.isCheck = check;
    } //setCheck

    /**
     *Sets the possible moves.
     *@param pm the possible moves of a peice.
     */

    public void setPM(ChessPiece[] pm) {
        this.possibleMoves = pm;
    } //setPM

    /**
     *Sets the Score for each team.
     *@param add val of taken piece.
     */

    public void setScore(int add) {
        score.setText("" + (Integer.parseInt(score.getText()) + add));
    } //setScore

    /**
     *Gets val of a piece.
     *@return val the point value of a piece.
     */

    public int getVal() {
        return val;
    } //getVal

    /**
     *Sets the possible board moves.
     *@param row row of possible move.
     *@param col col of possible move.
     *@param possible true is a possible move.
     */

    public void setPBM(int row, int col, boolean possible) {
        possibleBM[row][col] = possible;
    } //setPBM

    /**
     *Gets if a position is a possible move.
     *@param row row of possible move.
     *@param col col of possible move.
     *@return true if possition is a possible move.
     */

    public boolean getPBM(int row, int col) {
        return possibleBM[row][col];
    } //getPBM

    /**
     *Checks if king is in check.
     */

    public void checkForCheck() {
        boolean checkMate = false;
        String str = "";
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] != null && board[i][j].isKing()) {
                    if (board[i][j].isCheck()) {
                        if (board[i][j].isWhite()) {
                            str = "White is in";
                        } else {
                            str = "Black is in";
                        } //if
                        System.out.println(str + " Check!");
                        checkMate = checkForCheckMate(board[i][j]);
                    } //if
                } //if
            } //for
        } //for
        if (checkMate) {
            System.out.println(str + " CheckMate!");
        } //if
    } //checkCheck

    /**
     *Checks for check mate.
     *@param inCheck the king that is in check.
     *@return isCheckMate true if checkmate.
     */

    public boolean checkForCheckMate(ChessPiece inCheck) {
        boolean isCheckMate = true;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] != null && board[i][j].isWhite() != inCheck.isCheck()) {
                    if (inCheck.getPBM(i, j)) {
                        if (board[i][j].getPBM(i, j)) {
                            inCheck.setPBM(i, j, false);
                        } //if
                    } //if
                } //if
            } //for
        } //for
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (inCheck.getPBM(i, j)) {
                    isCheckMate = false;
                    inCheck.setCheck(false);
                } //if
            } //for
        } //for
        return isCheckMate;
    } //checkFCM

} //ChessPiece
