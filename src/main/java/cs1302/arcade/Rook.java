package cs1302.arcade;

import javafx.scene.layout.GridPane;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.geometry.HPos;
import javafx.scene.text.Text;

/**
 * Rook class, child of ChessPiece.
 */

public class Rook extends ChessPiece {

    GridPane chessGrid;
    ChessPiece[][] board;
    Rectangle[] possibleMoves = new Rectangle[14];
    Image imgW = new Image("akiross-Chess-Set-2.png");
    Image imgB = new Image("akiross-Chess-Set-8.png");
    ImagePattern imgPW = new ImagePattern(imgW);
    ImagePattern imgPB = new ImagePattern(imgB);

    /**
     * Rook constructor.
     *@param isWhite true if isWhite false is black.
     *@param row what row in gridpane.
     *@param col what cal in gridpane.
     *@param chessGrid the gridpane it's self.
     *@param board stores position of pieces.
     *@param isKing tells if piece is a king.
     *@param score the text for changing for different team scores.
     */

    public Rook(boolean isWhite, int row, int col, GridPane chessGrid,
                ChessPiece[][] board, boolean isKing, Text score) {
        super (isWhite, row, col, chessGrid, board, isKing, score, 5);
        this.chessGrid = chessGrid;
        this.board = board;
        this.getRect().setOnMouseClicked(move());
        if (this.isWhite()) {
            this.getRect().setFill(imgPW);
        } else {
            this.getRect().setFill(imgPB);
        } //if
        for (int i = 0; i < 14; i++) { //14 max possible moves for a Rook
            possibleMoves[i] = new Rectangle(40, 40, Color.GRAY);
            possibleMoves[i].setOnMouseClicked(replace(i));
            GridPane.setHalignment(possibleMoves[i], HPos.CENTER);
        } //for
    } //Rook

    /**
     * Event handler for piece rectangles.
     *@return event disables other peices calls canMove().
     */

    private EventHandler<? super MouseEvent> move() {
        return event -> {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (this.board[i][j] != null) {
                        this.board[i][j].getRect().setDisable(true);
                    } //if
                } //for
            } //for
            canMove();
        }; //return
    } //move

    /**
     * Event Handler for moving this peice to new position.
     *@param index index of the possibleMove chosen.
     *@return event updates board checks for checkmate/check enables other team peices.
     */

    private EventHandler<? super MouseEvent> replace(int index) {
        return event -> {
            for (int i = 0; i < 14; i++) {
                if (i != index) {
                    chessGrid.getChildren().remove(possibleMoves[i]);
                } //if
            } //for
            chessGrid.getChildren().remove(this.getRect());
            this.board[row][col] = null;
            this.row = GridPane.getRowIndex(possibleMoves[index]);
            this.col = GridPane.getColumnIndex(possibleMoves[index]);
            chessGrid.getChildren().remove(possibleMoves[index]);
            if (this.board[this.row][this.col] != null) {
                chessGrid.getChildren().remove(board[row][col].getRect());
                setScore(board[row][col].getVal());
            } //if
            this.setRow(row);
            this.setCol(col);
            chessGrid.add(this.getRect(), col, row);
            this.board[row][col] = this;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (this.board[i][j] != null && this.isWhite() != this.board[i][j].isWhite()) {
                        this.board[i][j].getRect().setDisable(false);
                        this.board[i][j].setBoard(this.board);
                    } //if
                } //for
            } //for
            setPBM();
            checkForCheck();
        }; //return
    } //move

    /**
     * Checks if piece can move and updates grid if can.
     */

    public void canMove() {
        boolean moved = false;
        boolean stopLoop = false;
        this.board = this.getBoard();
        int index = 0;
        int bCol = this.getCol();
        int bRow = this.getRow();
        for (int row = bRow; row < 8; row++) { //Straight down row
            if (!stopLoop && bRow != row) {
                if (this.board[row][bCol] == null) {
                    chessGrid.add(possibleMoves[index], bCol, row);
                    moved = true;
                    index++;
                } else if (this.board[row][bCol].isWhite() != this.isWhite()) {
                    chessGrid.add(possibleMoves[index], bCol, row);
                    moved = true;
                    stopLoop = true;
                    if (this.board[row][bCol].isKing()) {
                        this.board[row][bCol].setCheck(true);
                    } //if
                    index++;
                } else {
                    stopLoop = true;
                } //if
            } //if
        } //for
        stopLoop = false;
        for (int row = bRow; row >= 0; row--) { //Straight up row
            if (!stopLoop && bRow != row) {
                if (this.board[row][bCol] == null) {
                    chessGrid.add(possibleMoves[index], bCol, row);
                    moved = true;
                    index++;
                } else if (this.board[row][bCol].isWhite() != this.isWhite()) {
                    chessGrid.add(possibleMoves[index], bCol, row);
                    moved = true;
                    stopLoop = true;
                    index++;
                    if (this.board[row][bCol].isKing()) {
                        this.board[row][bCol].setCheck(true);
                    } //if
                } else {
                    stopLoop = true;
                } //if
            } //if
        } //for
        moved = canMove2(index, moved);
        if (!moved) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (this.board[i][j] != null) {
                        this.board[i][j].getRect().setDisable(false);
                        this.board[i][j].setBoard(this.board);
                    } //if
                } //for
            } //for
        } else {
            this.setClicked(true);
        } //if
    } //canMove

    /**
     *Check is can move up or down.
     *@param index index of possibleMoves.
     *@param moved2 true if moved.
     *@return moved true if peice can move.
     */

    public boolean canMove2(int index, boolean moved2) {
        boolean moved = moved2;
        boolean stopLoop = false;
        this.board = this.getBoard();
        int bCol = this.getCol();
        int bRow = this.getRow();
        for (int col = bCol; col < 8; col++) { //Straight down col
            if (!stopLoop && bCol != col) {
                if (this.board[bRow][col] == null) {
                    chessGrid.add(possibleMoves[index], col, bRow);
                    moved = true;
                    index++;
                } else if (this.board[bRow][col].isWhite() != this.isWhite()) {
                    chessGrid.add(possibleMoves[index], col, bRow);
                    moved = true;
                    stopLoop = true;
                    index++;
                    if (this.board[bRow][col].isKing()) {
                        this.board[bRow][col].setCheck(true);
                    } //if
                } else {
                    stopLoop = true;
                } //if
            } //if
        } //for
        stopLoop = false;
        for (int col = bCol; col >= 0; col--) { //Straight up col
            if (!stopLoop && bCol != col) {
                if (this.board[bRow][col] == null) {
                    chessGrid.add(possibleMoves[index], col, bRow);
                    moved = true;
                    index++;
                } else if (this.board[bRow][col].isWhite() != this.isWhite()) {
                    chessGrid.add(possibleMoves[index], col, bRow);
                    moved = true;
                    stopLoop = true;
                    index++;
                    if (this.board[bRow][col].isKing()) {
                        this.board[bRow][col].setCheck(true);
                    } //if
                } else {
                    stopLoop = true;
                } //if
            } //if
        } //for
        stopLoop = false;
        return moved;
    } //canMove2

    /**
     * Sets the future possible moves of piece.
     */

    public void setPBM() {
        boolean stopLoop = false;
        this.board = this.getBoard();
        int bCol = this.getCol();
        int bRow = this.getRow();
        for (int row = bRow; row < 8; row++) { //Straight down row
            if (!stopLoop && bRow != row) {
                if (this.board[row][bCol] == null) {
                    this.setPBM(row, bCol, true);
                } else if (this.board[row][bCol].isWhite() != this.isWhite()) {
                    stopLoop = true;
                    this.setPBM(row, bCol, true);
                    if (this.board[row][bCol].isKing()) {
                        this.board[row][bCol].setCheck(true);
                    } //if
                } else {
                    stopLoop = true;
                } //if
            } //if
        } //for
        stopLoop = false;
        for (int row = bRow; row >= 0; row--) { //Straight up row
            if (!stopLoop && bRow != row) {
                if (this.board[row][bCol] == null) {
                    this.setPBM(row, bCol, true);
                } else if (this.board[row][bCol].isWhite() != this.isWhite()) {
                    stopLoop = true;
                    this.setPBM(row, bCol, true);
                    if (this.board[row][bCol].isKing()) {
                        this.board[row][bCol].setCheck(true);
                    } //if
                } else {
                    stopLoop = true;
                } //if
            } //if
        } //for
        setPBM2();
    } //setPBM

    /**
     * Finishes up work of setPBM().
     */

    public void setPBM2() {
        boolean stopLoop = false;
        this.board = this.getBoard();
        int bCol = this.getCol();
        int bRow = this.getRow();
        for (int col = bCol; col < 8; col++) { //Straight down col
            if (!stopLoop && bCol != col) {
                if (this.board[bRow][col] == null) {
                    this.setPBM(bRow, col, true);
                } else if (this.board[bRow][col].isWhite() != this.isWhite()) {
                    stopLoop = true;
                    this.setPBM(bRow, col, true);
                    if (this.board[bRow][col].isKing()) {
                        this.board[bRow][col].setCheck(true);
                    } //if
                } else {
                    stopLoop = true;
                } //if
            } //if
        } //for
        stopLoop = false;
        for (int col = bCol; col >= 0; col--) { //Straight up col
            if (!stopLoop && bCol != col) {
                if (this.board[bRow][col] == null) {
                    this.setPBM(bRow, col, true);
                } else if (this.board[bRow][col].isWhite() != this.isWhite()) {
                    stopLoop = true;
                    this.setPBM(bRow, col, true);
                    if (this.board[bRow][col].isKing()) {
                        this.board[bRow][col].setCheck(true);
                    } //if
                } else {
                    stopLoop = true;
                } //if
            } //if
        } //for
    } //setPBM2

} //Rook
