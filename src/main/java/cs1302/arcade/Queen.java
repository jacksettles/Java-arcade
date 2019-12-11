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
 *Queen child of ChessPiece.
 */

public class Queen extends ChessPiece {

    GridPane chessGrid;
    ChessPiece[][] board;
    Rectangle[] possibleMoves = new Rectangle[27];
    Image imgW = new Image("akiross-Chess-Set-5.png");
    Image imgB = new Image("akiross-Chess-Set-11.png");
    ImagePattern imgPW = new ImagePattern(imgW);
    ImagePattern imgPB = new ImagePattern(imgB);

    /**
     * King constructor.
     *@param isWhite true if isWhite false is black.
     *@param row what row in gridpane.
     *@param col what cal in gridpane.
     *@param chessGrid the gridpane it's self.
     *@param board stores position of pieces.
     *@param isKing tells if piece is a king.
     *@param score the text for changing for different team scores.
     */

    public Queen(boolean isWhite, int row, int col, GridPane chessGrid,
                 ChessPiece[][] board, boolean isKing, Text score) {
        super(isWhite, row, col, chessGrid, board, isKing, score, 9);
        this.chessGrid = chessGrid;
        this.board = board;
        this.getRect().setOnMouseClicked(move());
        if (this.isWhite()) {
            this.getRect().setFill(imgPW);
        } else {
            this.getRect().setFill(imgPB);
        } //if
        for (int i = 0; i < 27; i++) { //13 max possible moves for a bishop
            possibleMoves[i] = new Rectangle(40, 40, Color.GRAY);
            possibleMoves[i].setOnMouseClicked(replace(i));
            GridPane.setHalignment(possibleMoves[i], HPos.CENTER);
        } //for
    } //Queen

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
            for (int i = 0; i < 27; i++) {
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
     *@param moved2 true is moved.
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
        moved = canMove3(index, moved);
        return moved;
    } //canMove2


    /**
     *Finished up work of Can move.
     *@param index continues the index of possible move.
     *@param move true if moved.
     *@return moved true if moved.
     */

    public boolean canMove3(int index, boolean move) {
        boolean moved = move;
        boolean stopLoop = false;
        this.board = this.getBoard();
        int bCol = this.getCol();
        int bRow = this.getRow();
        int col = bCol;
        for (int row = bRow; row < 8; row++) {
            if (!stopLoop && bRow != row && col < 7) {
                col++;
                if (this.board[row][col] == null) {
                    chessGrid.add(possibleMoves[index], col, row);
                    moved = true;
                    index++;
                } else if (this.board[row][col].isWhite() != this.isWhite()) {
                    chessGrid.add(possibleMoves[index], col, row);
                    moved = true;
                    stopLoop = true;
                    index++;
                    if (this.board[row][col].isKing()) {
                        this.board[row][col].setCheck(true);
                    } //if
                } else {
                    stopLoop = true;
                } //if
            } //if
        } //for
        stopLoop = false;
        col = bCol;
        for (int row = bRow; row >= 0; row--) {
            if (!stopLoop && bRow != row && col > 0) {
                col--;
                if (this.board[row][col] == null) {
                    chessGrid.add(possibleMoves[index], col, row);
                    moved = true;
                    index++;
                } else if (this.board[row][col].isWhite() != this.isWhite()) {
                    chessGrid.add(possibleMoves[index], col, row);
                    moved = true;
                    stopLoop = true;
                    index++;
                    if (this.board[row][col].isKing()) {
                        this.board[row][col].setCheck(true);
                    } //if
                } else {
                    stopLoop = true;
                } //if
            } //if
        } //for
        moved = canMove4(index, moved);
        return moved;
    } //canMove

    /**
     * Enables all pieces if not moved.
     *@param moved true if moved.
     */

    public void checkMoved(boolean moved) {
        if (!moved) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (this.board[i][j] != null) {
                        this.board[i][j].getRect().setDisable(false);
                        this.board[i][j].setBoard(this.board);
                    } //if
                } //for
            } //for
        } //if
    } //if

    /**
     *Finished up work of Can move.
     *@param index continues the index of possible move.
     *@param moved2 true if moved.
     *@return moved true if moved.
     */

    public boolean canMove4(int index, boolean moved2) {
        boolean stopLoop = false;
        boolean moved = moved2;
        this.board = this.getBoard();
        int bCol = this.getCol();
        int bRow = this.getRow();
        int col = bCol;
        for (int row = bRow; row < 8; row++) { //FIX ALL THIS NEED RATIO
            if (!stopLoop && bRow != row && col > 0) {
                col--;
                if (this.board[row][col] == null) {
                    chessGrid.add(possibleMoves[index], col, row);
                    moved = true;
                    index++;
                } else if (this.board[row][col].isWhite() != this.isWhite()) {
                    chessGrid.add(possibleMoves[index], col, row);
                    moved = true;
                    stopLoop = true;
                    index++;
                    if (this.board[row][col].isKing()) {
                        this.board[row][col].setCheck(true);
                    } //if
                } else {
                    stopLoop = true;
                } //if
            } //if
        } //for
        stopLoop = false;
        col = bCol;
        for (int row = bRow; row >= 0; row--) { //FIX ALL THIS NEED RATIO
            if (!stopLoop && bRow != row && col < 7) {
                col++;
                if (this.board[row][col] == null) {
                    chessGrid.add(possibleMoves[index], col, row);
                    moved = true;
                    index++;
                } else if (this.board[row][col].isWhite() != this.isWhite()) {
                    chessGrid.add(possibleMoves[index], col, row);
                    moved = true;
                    stopLoop = true;
                    index++;
                    if (this.board[row][col].isKing()) {
                        this.board[row][col].setCheck(true);
                    } //if
                } else {
                    stopLoop = true;
                } //if
            } //if
        } //for
        return moved;
    } //canMove4


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
        setPBM3();
    } //setPBM2

    /**
     * Finishes work of PBM2().
     */

    public void setPBM3() {
        boolean stopLoop = false;
        this.board = this.getBoard();
        int bCol = this.getCol();
        int bRow = this.getRow();
        int col = bCol;
        for (int row = bRow; row < 8; row++) { //Bottom right diagnol
            if (!stopLoop && bRow != row && col < 7) {
                col++;
                if (this.board[row][col] == null) {
                    this.setPBM(row, col, true);
                } else if (this.board[row][col].isWhite() != this.isWhite()) {
                    stopLoop = true;
                    this.setPBM(row, col, true);
                    if (this.board[row][col].isKing()) {
                        this.board[row][col].setCheck(true);
                    } //if
                } else {
                    stopLoop = true;
                } //if
            } //if
        } //for
        stopLoop = false;
        col = bCol;
        for (int row = bRow; row >= 0; row--) { //Upper left Diagnol
            if (!stopLoop && bRow != row && col > 0) {
                col--;
                if (this.board[row][col] == null) {
                    this.setPBM(row, col, true);
                } else if (this.board[row][col].isWhite() != this.isWhite()) {
                    stopLoop = true;
                    this.setPBM(row, col, true);
                    if (this.board[row][col].isKing()) {
                        this.board[row][col].setCheck(true);
                    } //if
                } else {
                    stopLoop = true;
                } //if
            } //if
        } //for
        setPBM4();
    } //setPBM3

    /**
     *Finishes setPBM.
     */

    public void setPBM4() {
        boolean stopLoop = false;
        this.board = this.getBoard();
        int bCol = this.getCol();
        int bRow = this.getRow();
        int col = bCol;
        for (int row = bRow; row < 8; row++) { //Bottom left Diagnal
            if (!stopLoop && bRow != row && col > 0) {
                col--;
                if (this.board[row][col] == null) {
                    this.setPBM(row, col, true);
                } else if (this.board[row][col].isWhite() != this.isWhite()) {
                    stopLoop = true;
                    this.setPBM(row, col, true);
                    if (this.board[row][col].isKing()) {
                        this.board[row][col].setCheck(true);
                    } //if
                } else {
                    stopLoop = true;
                } //if
            } //if
        } //for
        stopLoop = false;
        col = bCol;
        for (int row = bRow; row >= 0; row--) { //Upper right diagnal
            if (!stopLoop && bRow != row && col < 7) {
                col++;
                if (this.board[row][col] == null) {
                    this.setPBM(row, col, true);
                } else if (this.board[row][col].isWhite() != this.isWhite()) {
                    stopLoop = true;
                    this.setPBM(row, col, true);
                    if (this.board[row][col].isKing()) {
                        this.board[row][col].setCheck(true);
                    } //if
                } else {
                    stopLoop = true;
                } //if
            } //if
        } //for
        stopLoop = false;
    } //setPBM4


} //Queen
