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

public class Bishop extends ChessPiece {

    GridPane chessGrid;
    ChessPiece[][] board;
    Rectangle[] possibleMoves = new Rectangle[13];
    Image imgW = new Image("akiross-Chess-Set-4.png");
    Image imgB = new Image("akiross-Chess-Set-10.png");
    ImagePattern imgPW = new ImagePattern(imgW);
    ImagePattern imgPB = new ImagePattern(imgB);

    /**
     * Bishop constructor.
     *@param isWhite true if isWhite false is black.
     *@param row what row in gridpane.
     *@param col what cal in gridpane.
     *@param chessGrid the gridpane it's self.
     *@param board stores position of pieces.
     *@param isKing tells if piece is a king.
     *@param score the text for changing for different team scores.
     */

    public Bishop(boolean isWhite, int row, int col, GridPane chessGrid,
                  ChessPiece[][] board, boolean isKing, Text score) {
        super(isWhite, row, col, chessGrid, board, isKing, score, 3);
        this.chessGrid = chessGrid;
        this.board = board;
        this.getRect().setOnMouseClicked(move());
        if (this.isWhite()) {
            this.getRect().setFill(imgPW);
        } else {
            this.getRect().setFill(imgPB);
        } //if
        for (int i = 0; i < 13; i++) { //13 max possible moves for a bishop
            possibleMoves[i] = new Rectangle(40, 40, Color.GRAY);
            possibleMoves[i].setOnMouseClicked(replace(i));
            GridPane.setHalignment(possibleMoves[i], HPos.CENTER);
        } //for
    } //Bishop

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

    private EventHandler<? super MouseEvent> replace(int index) {
        return event -> {
            for(int i = 0; i < 13; i++) {
                if(i != index) {
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

    public void canMove() {
        boolean moved = false;
        boolean stopLoop = false;
        this.board = this.getBoard();
        int index = 0;
        int bCol = this.getCol();
        int bRow = this.getRow();
        int col = bCol;
        for (int row = bRow; row < 8; row++) { //FIX ALL THIS NEED RATIO
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
        for (int row = bRow; row >= 0; row--) { //FIX ALL THIS NEED RATIO
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
        stopLoop = false;
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

    public void setPBM() {
        boolean moved = false;
        boolean stopLoop = false;
        this.board = this.getBoard();
        int bCol = this.getCol();
        int bRow = this.getRow();
        int col = bCol;
        for (int row = bRow; row < 8; row++) { //FIX ALL THIS NEED RATIO
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
        for (int row = bRow; row >= 0; row--) { //FIX ALL THIS NEED RATIO
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
        for (int row = bRow; row < 8; row++) { //FIX ALL THIS NEED RATIO
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
        for (int row = bRow; row >= 0; row--) { //FIX ALL THIS NEED RATIO
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
    } //setPBM

} //Bishop
