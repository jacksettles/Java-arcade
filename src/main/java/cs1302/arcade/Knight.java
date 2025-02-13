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
 *Knight Child class of ChessPiece.
 */

public class Knight extends ChessPiece {

    GridPane chessGrid;
    ChessPiece[][] board;
    Rectangle[] possibleMoves = new Rectangle[8];
    Image imgW = new Image("akiross-Chess-Set-3.png");
    Image imgB = new Image("akiross-Chess-Set-9.png");
    ImagePattern imgPW = new ImagePattern(imgW);
    ImagePattern imgPB = new ImagePattern(imgB);

    /**
     * Pawn constructor.
     *@param isWhite true if isWhite false is black.
     *@param row what row in gridpane.
     *@param col what cal in gridpane.
     *@param chessGrid the gridpane it's self.
     *@param board stores position of pieces.
     *@param isKing tells if piece is a king.
     *@param score the text for changing for different team scores.
     */

    public Knight(boolean isWhite, int row, int col, GridPane chessGrid,
                  ChessPiece[][] board, boolean isKing, Text score) {
        super (isWhite, row, col, chessGrid, board, isKing, score, 3);
        this.chessGrid = chessGrid;
        this.board = board;
        this.getRect().setOnMouseClicked(move());
        if (this.isWhite()) {
            this.getRect().setFill(imgPW);
        } else {
            this.getRect().setFill(imgPB);
        } //if
        for (int i = 0; i < 8; i++) {
            possibleMoves[i] = new Rectangle(40, 40, Color.GRAY);
            possibleMoves[i].setOnMouseClicked(replace(i));
            GridPane.setHalignment(possibleMoves[i], HPos.CENTER);
        } //for
    } //knight

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
            for (int i = 0; i < 8; i++) {
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
        this.board = this.getBoard();
        if (this.getRow() + 2 < 8) {
            if (this.getCol() + 1 < 8) {
                if (this.board[this.getRow() + 2][this.getCol() + 1] == null) {
                    chessGrid.add(possibleMoves[0], this.getCol() + 1, this.getRow() + 2);
                    moved = true;
                } else if (this.board[this.getRow() + 2]
                           [this.getCol() + 1].isWhite() != this.isWhite()) {
                    chessGrid.add(possibleMoves[0], this.getCol() + 1, this.getRow() + 2);
                    moved = true;
                    if (this.board[this.getRow() + 2][this.getCol() + 1].isKing()) {
                        this.board[this.getRow() + 2][this.getCol() + 1].setCheck(true);
                    } //if
                } //if
            } //if
            if (this.getCol() - 1 >= 0) {
                if (this.board[this.getRow() + 2][this.getCol() - 1] == null) {
                    chessGrid.add(possibleMoves[1], this.getCol() - 1, this.getRow() + 2);
                    moved = true;
                } else if (this.board[this.getRow() + 2]
                           [this.getCol() - 1].isWhite() != this.isWhite()) {
                    chessGrid.add(possibleMoves[1], this.getCol() - 1, this.getRow() + 2);
                    moved = true;
                    if (this.board[this.getRow() + 2][this.getCol() - 1].isKing()) {
                        this.board[this.getRow() + 2][this.getCol() - 1].setCheck(true);
                    } //if
                } //if
            } //if
        } //bounds
        moved = canMove2(moved);
        moved = canMove3(moved);
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
     * Finishes work of canMove().
     *@param moved true if moved.
     *@return moved true if moved.
     */

    public boolean canMove2(boolean moved) {
        if (this.getCol() + 2 < 8) {
            if (this.getRow() - 1 >= 0) {
                if (this.board[this.getRow() - 1][this.getCol() + 2] == null) {
                    chessGrid.add(possibleMoves[5], this.getCol() + 2, this.getRow() - 1);
                    moved = true;
                } else if (this.board[this.getRow() - 1]
                           [this.getCol() + 2].isWhite() != this.isWhite()) {
                    chessGrid.add(possibleMoves[5], this.getCol() + 2, this.getRow() - 1);
                    moved = true;
                    if (this.board[this.getRow() - 1][this.getCol() + 2].isKing()) {
                        this.board[this.getRow() - 1][this.getCol() + 2].setCheck(true);
                    } //if
                } //if
            } //if
        } //bounds
        if (this.getCol() - 2 >= 0) {
            if (this.getRow() + 1 < 8) {
                if (this.board[this.getRow() + 1][this.getCol() - 2] == null) {
                    chessGrid.add(possibleMoves[6], this.getCol() - 2, this.getRow() + 1);
                    moved = true;
                } else if (this.board[this.getRow() + 1]
                           [this.getCol() - 2].isWhite() != this.isWhite()) {
                    chessGrid.add(possibleMoves[6], this.getCol() - 2, this.getRow() + 1);
                    moved = true;
                    if (this.board[this.getRow() + 1][this.getCol() - 2].isKing()) {
                        this.board[this.getRow() + 1][this.getCol() - 2].setCheck(true);
                    } //if
                } //if
            } //if
            if (this.getRow() - 1 >= 0) {
                if (this.board[this.getRow() - 1][this.getCol() - 2] == null) {
                    chessGrid.add(possibleMoves[7], this.getCol() - 2, this.getRow() - 1);
                    moved = true;
                } else if (this.board[this.getRow() - 1]
                           [this.getCol() - 2].isWhite() != this.isWhite()) {
                    chessGrid.add(possibleMoves[7], this.getCol() - 2, this.getRow() - 1);
                    moved = true;
                    if (this.board[this.getRow() - 1][this.getCol() - 2].isKing()) {
                        this.board[this.getRow() - 1][this.getCol() - 2].setCheck(true);
                    } //if
                } //if
            } //if
        } //bounds
        return moved;
    } //canMove2

    /**
     * Finishes work of canMove().
     *@param moved true if moved.
     *@return moved true if moved.
     */

    public boolean canMove3(boolean moved) {
        if (this.getRow() - 2 >= 0) {
            if (this.getCol() + 1 < 8) {
                if (this.board[this.getRow() - 2][this.getCol() + 1] == null) {
                    chessGrid.add(possibleMoves[2], this.getCol() + 1, this.getRow() - 2);
                    moved = true;
                } else if (this.board[this.getRow() - 2]
                           [this.getCol() + 1].isWhite() != this.isWhite()) {
                    chessGrid.add(possibleMoves[2], this.getCol() + 1, this.getRow() - 2);
                    moved = true;
                    if (this.board[this.getRow() - 2][this.getCol() + 1].isKing()) {
                        this.board[this.getRow() - 2][this.getCol() + 1].setCheck(true);
                    } //if
                } //if
            } //if
            if (this.getCol() - 1 >= 0) {
                if (this.board[this.getRow() - 2][this.getCol() - 1] == null) {
                    chessGrid.add(possibleMoves[3], this.getCol() - 1, this.getRow() - 2);
                    moved = true;
                } else if (this.board[this.getRow() - 2]
                           [this.getCol() - 1].isWhite() != this.isWhite()) {
                    chessGrid.add(possibleMoves[3], this.getCol() - 1, this.getRow() - 2);
                    moved = true;
                    if (this.board[this.getRow() - 2][this.getCol() - 1].isKing()) {
                        this.board[this.getRow() - 2][this.getCol() - 1].setCheck(true);
                    } //if
                } //if
            } //if
        } //bounds
        if (this.getCol() + 2 < 8) {
            if (this.getRow() + 1 < 8) {
                if (this.board[this.getRow() + 1][this.getCol() + 2] == null) {
                    chessGrid.add(possibleMoves[4], this.getCol() + 2, this.getRow() + 1);
                    moved = true;
                } else if (this.board[this.getRow() + 1]
                           [this.getCol() + 2].isWhite() != this.isWhite()) {
                    chessGrid.add(possibleMoves[4], this.getCol() + 2, this.getRow() + 1);
                    moved = true;
                    if (this.board[this.getRow() + 1][this.getCol() + 2].isKing()) {
                        this.board[this.getRow() + 1][this.getCol() + 2].setCheck(true);
                    } //if
                } //if
            } //if
        } //if
        return moved;
    } //canMove3

    /**
     *Places possible future moves.
     */

    public void setPBM() {
        this.board = this.getBoard();
        if (this.getRow() + 2 < 8) {
            if (this.getCol() + 1 < 8) {
                if (this.board[this.getRow() + 2][this.getCol() + 1] == null) {
                    this.setPBM(this.getRow() + 2, this.getCol() + 1, true);
                } else if (this.board[this.getRow() + 2]
                           [this.getCol() + 1].isWhite() != this.isWhite()) {
                    this.setPBM(this.getRow() + 2, this.getCol() + 1, true);
                    if (this.board[this.getRow() + 2][this.getCol() + 1].isKing()) {
                        this.board[this.getRow() + 2][this.getCol() + 1].setCheck(true);
                    } //if
                } //if
            } //if
            if (this.getCol() - 1 >= 0) {
                if (this.board[this.getRow() + 2][this.getCol() - 1] == null) {
                    this.setPBM(this.getRow() + 2, this.getCol() - 1, true);
                } else if (this.board[this.getRow() + 2]
                           [this.getCol() - 1].isWhite() != this.isWhite()) {
                    this.setPBM(this.getRow() + 2, this.getCol() - 1, true);
                    if (this.board[this.getRow() + 2][this.getCol() - 1].isKing()) {
                        this.board[this.getRow() + 2][this.getCol() - 1].setCheck(true);
                    } //if
                } //if
            } //if
        } //bounds

        if (this.getRow() - 2 >= 0) {
            if (this.getCol() + 1 < 8) {
                if (this.board[this.getRow() - 2][this.getCol() + 1] == null) {
                    this.setPBM(this.getRow() - 2, this.getCol() + 1, true);
                } else if (this.board[this.getRow() - 2]
                           [this.getCol() + 1].isWhite() != this.isWhite()) {
                    this.setPBM(this.getRow() - 2, this.getCol() + 1, true);
                    if (this.board[this.getRow() - 2][this.getCol() + 1].isKing()) {
                        this.board[this.getRow() - 2][this.getCol() + 1].setCheck(true);
                    } //if
                } //if
            } //if
            if (this.getCol() - 1 >= 0) {
                if (this.board[this.getRow() - 2][this.getCol() - 1] == null) {
                    this.setPBM(this.getRow() - 2, this.getCol() - 1, true);
                } else if (this.board[this.getRow() - 2]
                           [this.getCol() - 1].isWhite() != this.isWhite()) {
                    this.setPBM(this.getRow() - 2, this.getCol() - 1, true);
                    if (this.board[this.getRow() - 2][this.getCol() - 1].isKing()) {
                        this.board[this.getRow() - 2][this.getCol() - 1].setCheck(true);
                    } //if
                } //if
            } //if
        } //bounds
        setPBM2();
    } //setPBM

    /**
     *Finishes work of setPBM.
     */

    public void setPBM2() {
        if (this.getCol() + 2 < 8) {
            if (this.getRow() + 1 < 8) {
                if (this.board[this.getRow() + 1][this.getCol() + 2] == null) {
                    this.setPBM(this.getRow() + 1, this.getCol() + 2, true);
                } else if (this.board[this.getRow() + 1]
                           [this.getCol() + 2].isWhite() != this.isWhite()) {
                    this.setPBM(this.getRow() + 1, this.getCol() + 2, true);
                    if (this.board[this.getRow() + 1][this.getCol() + 2].isKing()) {
                        this.board[this.getRow() + 1][this.getCol() + 2].setCheck(true);
                    } //if
                } //if
            } //if
            if (this.getRow() - 1 >= 0) {
                if (this.board[this.getRow() - 1][this.getCol() + 2] == null) {
                    this.setPBM(this.getRow() - 1, this.getCol() + 2, true);
                } else if (this.board[this.getRow() - 1]
                           [this.getCol() + 2].isWhite() != this.isWhite()) {
                    this.setPBM(this.getRow() - 1, this.getCol() + 2, true);
                    if (this.board[this.getRow() - 1][this.getCol() + 2].isKing()) {
                        this.board[this.getRow() - 1][this.getCol() + 2].setCheck(true);
                    } //if
                } //if
            } //if
        } //bounds

        if (this.getCol() - 2 >= 0) {
            if (this.getRow() + 1 < 8) {
                if (this.board[this.getRow() + 1][this.getCol() - 2] == null) {
                    this.setPBM(this.getRow() + 1, this.getCol() - 2, true);
                } else if (this.board[this.getRow() + 1]
                           [this.getCol() - 2].isWhite() != this.isWhite()) {
                    this.setPBM(this.getRow() + 1, this.getCol() - 2, true);
                    if (this.board[this.getRow() + 1][this.getCol() - 2].isKing()) {
                        this.board[this.getRow() + 1][this.getCol() - 2].setCheck(true);
                    } //if
                } //if
            } //if
            if (this.getRow() - 1 >= 0) {
                if (this.board[this.getRow() - 1][this.getCol() - 2] == null) {
                    this.setPBM(this.getRow() - 1, this.getCol() - 2, true);
                } else if (this.board[this.getRow() - 1]
                           [this.getCol() - 2].isWhite() != this.isWhite()) {
                    this.setPBM(this.getRow() - 1, this.getCol() - 2, true);
                    if (this.board[this.getRow() - 1][this.getCol() - 2].isKing()) {
                        this.board[this.getRow() - 1][this.getCol() - 2].setCheck(true);
                    } //if
                } //if
            } //if
        } //bounds
    } //setPBM2
} //Knight
