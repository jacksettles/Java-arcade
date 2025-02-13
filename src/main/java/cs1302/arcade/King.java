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
 * King class child of ChessPiece.
 */

public class King extends ChessPiece {

    GridPane chessGrid;
    ChessPiece[][] board;
    Rectangle[] possibleMoves = new Rectangle[8];
    Image imgW = new Image("akiross-Chess-Set-6.png");
    Image imgB = new Image("akiross-Chess-Set-12.png");
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

    public King(boolean isWhite, int row, int col, GridPane chessGrid,
                ChessPiece[][] board, boolean isKing, Text score) {
        super (isWhite, row, col, chessGrid, board, isKing, score, 0);
        this.chessGrid = chessGrid;
        this.board = board;
        this.getRect().setOnMouseClicked(move());
        if (this.isWhite()) {
            this.getRect().setFill(imgPW);
        } else {
            this.getRect().setFill(imgPB);
        } //if
        for (int i = 0; i < 8; i++) { //8 max possible moves for a king
            possibleMoves[i] = new Rectangle(40, 40, Color.GRAY);
            possibleMoves[i].setOnMouseClicked(replace(i));
            GridPane.setHalignment(possibleMoves[i], HPos.CENTER);
        } //for
    } //King

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
        boolean stopLoop = false;
        this.board = this.getBoard();
        int index = 0;
        if (this.getRow() + 1 < 8) {
            if (this.board[this.getRow() + 1][this.getCol()] == null) {
                chessGrid.add(possibleMoves[0], this.getCol(), this.getRow() + 1);
                moved = true;
                this.setPBM(this.getRow() + 1, this.getCol(), true);
            } else if (this.board[this.getRow() + 1][this.getCol()].isWhite() != this.isWhite()) {
                chessGrid.add(possibleMoves[0], this.getCol(), this.getRow() + 1);
                moved = true;
            } //if
            if (this.getCol() + 1 < 8) {
                if (this.board[this.getRow() + 1][this.getCol() + 1] == null) {
                    chessGrid.add(possibleMoves[1], this.getCol() + 1, this.getRow() + 1);
                    moved = true;
                } else if (this.board[this.getRow() + 1]
                           [this.getCol() + 1].isWhite() != this.isWhite()) {
                    chessGrid.add(possibleMoves[1], this.getCol() + 1, this.getRow() + 1);
                    moved = true;
                } //if
            } //if
            if (this.getCol() - 1 >= 0) {
                if (this.board[this.getRow() + 1][this.getCol() - 1] == null) {
                    chessGrid.add(possibleMoves[2], this.getCol() - 1, this.getRow() + 1);
                    moved = true;
                } else if (this.board[this.getRow() + 1]
                           [this.getCol() - 1].isWhite() != this.isWhite()) {
                    chessGrid.add(possibleMoves[2], this.getCol() - 1, this.getRow() + 1);
                    moved = true;
                } //if
            } //if
        } //bounds
        moved = canMove2(moved);
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
     *Finishes work of CanMove().
     *@param move true if moved.
     *@return moved tru if moved.
     */

    public boolean canMove2(boolean move) {
        boolean moved = move;
        if (this.getRow() - 1 >= 0) {
            if (this.board[this.getRow() - 1][this.getCol()] == null) {
                chessGrid.add(possibleMoves[3], this.getCol(), this.getRow() - 1);
                moved = true;
            } else if (this.board[this.getRow() - 1][this.getCol()].isWhite() != this.isWhite()) {
                chessGrid.add(possibleMoves[3], this.getCol(), this.getRow() - 1);
                moved = true;
            } //if
            if (this.getCol() + 1 < 8) {
                if (this.board[this.getRow() - 1][this.getCol() + 1] == null) {
                    chessGrid.add(possibleMoves[4], this.getCol() + 1, this.getRow() - 1);
                    moved = true;
                } else if (this.board[this.getRow() - 1]
                           [this.getCol() + 1].isWhite() != this.isWhite()) {
                    chessGrid.add(possibleMoves[4], this.getCol() + 1, this.getRow() - 1);
                    moved = true;
                } //if
            } //if
            if (this.getCol() - 1 >= 0) {
                if (this.board[this.getRow() - 1][this.getCol() - 1] == null) {
                    chessGrid.add(possibleMoves[5], this.getCol() - 1, this.getRow() - 1);
                    moved = true;
                } else if (this.board[this.getRow() - 1]
                           [this.getCol() - 1].isWhite() != this.isWhite()) {
                    chessGrid.add(possibleMoves[5], this.getCol() - 1, this.getRow() - 1);
                    moved = true;
                } //if
            } //if
        } //bounds
        if (this.getCol() + 1 < 8) {
            if (this.board[this.getRow()][this.getCol() + 1] == null) {
                chessGrid.add(possibleMoves[6], this.getCol() + 1, this.getRow());
                moved = true;
            } else if (this.board[this.getRow()][this.getCol() + 1].isWhite() != this.isWhite()) {
                chessGrid.add(possibleMoves[6], this.getCol() + 1, this.getRow());
                moved = true;
            } //if
        } //if
        if (this.getCol() - 1 >= 0) {
            if (this.board[this.getRow()][this.getCol() - 1] == null) {
                chessGrid.add(possibleMoves[7], this.getCol() - 1, this.getRow());
                moved = true;
            } else if (this.board[this.getRow()][this.getCol() - 1].isWhite() != this.isWhite()) {
                chessGrid.add(possibleMoves[7], this.getCol() - 1, this.getRow());
                moved = true;
            } //if
        } //if
        return moved;
    } //canMove2

    /**
     * Sets the future possible moves of piece.
     */

    public void setPBM() {
        boolean stopLoop = false;
        this.board = this.getBoard();
        if (this.getRow() + 1 < 8) {
            if (this.board[this.getRow() + 1][this.getCol()] == null) {
                this.setPBM(this.getRow() + 1, this.getCol(), true);
            } else if (this.board[this.getRow() + 1][this.getCol()].isWhite() != this.isWhite()) {
                this.setPBM(this.getRow() + 1, this.getCol(), true);
            } //if
            if (this.getCol() + 1 < 8) {
                if (this.board[this.getRow() + 1][this.getCol() + 1] == null) {
                    this.setPBM(this.getRow() + 1, this.getCol() + 1, true);
                } else if (this.board[this.getRow() + 1]
                           [this.getCol() + 1].isWhite() != this.isWhite()) {
                    this.setPBM(this.getRow() + 1, this.getCol(), true);
                } //if
            } //if
            if (this.getCol() - 1 >= 0) {
                if (this.board[this.getRow() + 1][this.getCol() - 1] == null) {
                    this.setPBM(this.getRow() + 1, this.getCol() - 1, true);
                } else if (this.board[this.getRow() + 1]
                           [this.getCol() - 1].isWhite() != this.isWhite()) {
                    this.setPBM(this.getRow() + 1, this.getCol() - 1, true);
                } //if
            } //if
        } //bounds
        if (this.getRow() - 1 >= 0) {
            if (this.board[this.getRow() - 1][this.getCol()] == null) {
                this.setPBM(this.getRow() - 1, this.getCol(), true);
            } else if (this.board[this.getRow() - 1][this.getCol()].isWhite() != this.isWhite()) {
                this.setPBM(this.getRow() - 1, this.getCol(), true);
            } //if
            if (this.getCol() + 1 < 8) {
                if (this.board[this.getRow() - 1][this.getCol() + 1] == null) {
                    this.setPBM(this.getRow() - 1, this.getCol() + 1, true);
                } else if (this.board[this.getRow() - 1]
                           [this.getCol() + 1].isWhite() != this.isWhite()) {
                    this.setPBM(this.getRow() - 1, this.getCol() + 1, true);
                } //if
            } //if
            if (this.getCol() - 1 >= 0) {
                if (this.board[this.getRow() - 1][this.getCol() - 1] == null) {
                    this.setPBM(this.getRow() - 1, this.getCol() - 1, true);
                } else if (this.board[this.getRow() - 1]
                           [this.getCol() - 1].isWhite() != this.isWhite()) {
                    this.setPBM(this.getRow() - 1, this.getCol() - 1, true);
                } //if
            } //if
        } //bounds
        setPBM2();
    } //setPBM

    /**
     *Finishes work of setPBM().
     */

    public void setPBM2() {
        if (this.getCol() + 1 < 8) {
            if (this.board[this.getRow()][this.getCol() + 1] == null) {
                this.setPBM(this.getRow(), this.getCol() + 1, true);
            } else if (this.board[this.getRow()][this.getCol() + 1].isWhite() != this.isWhite()) {
                this.setPBM(this.getRow(), this.getCol() + 1, true);
            } //if
        } //if
        if (this.getCol() - 1 >= 0) {
            if (this.board[this.getRow()][this.getCol() - 1] == null) {
                this.setPBM(this.getRow(), this.getCol() - 1, true);
            } else if (this.board[this.getRow()][this.getCol() - 1].isWhite() != this.isWhite()) {
                this.setPBM(this.getRow(), this.getCol() - 1, true);
            } //if
        } //if
    } //setPBM2

} //King
