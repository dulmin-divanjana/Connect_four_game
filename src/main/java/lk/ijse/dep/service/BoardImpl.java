package lk.ijse.dep.service;

@SuppressWarnings("ALL")
public class BoardImpl implements Board {
    final Piece [][] pieces;
    final BoardUI boardUI;
    public BoardImpl(BoardUI boardUI) {
        this.boardUI = boardUI;
        pieces = new Piece[NUM_OF_COLS][NUM_OF_ROWS];
        setArrayValues();

        System.out.println("Array Initialized");
    }

    private void setArrayValues() {
        for (int i = 0; i < NUM_OF_COLS; i++){
            for (int j = 0; j < NUM_OF_ROWS; j++){
                pieces [i][j] = Piece.EMPTY;
            }
        }
        System.out.println("Values set Piece.Empty");
    }

    @Override
    public BoardUI getBoardUI() {return boardUI;}

    @Override
    public int findNextAvailableSpot(int col) {
        System.out.println("In findNextAvaliableSpot Method");
        for (int i = 0; i<pieces[col].length; i++){
            if(pieces[col][i].equals(Piece.EMPTY)) return i;
        }
        return -1;
    }

    @Override
    public boolean isLegalMove(int col){
        System.out.println("in isLegalMove method");
        for (int i = 0; i < NUM_OF_ROWS; i++){
            if (pieces[col][i].equals(Piece.EMPTY)){
                System.out.println("Legal Move");
                return true;
            }
        }
        System.out.println("Illegal Move");
        return false;
    }

    @Override
    public boolean existLegalMoves() {
        for (Piece [] elementsGroup : pieces){
            for (Piece ele : elementsGroup){
                if (ele.equals(Piece.EMPTY)) return false;
            }
        }
        return true;
    }

    @Override
    public void updateMove(int col, Piece move) {
        pieces[col][findNextAvailableSpot(col)] = move;
        boardUI.update(col, !move.equals(Piece.GREEN));
    }

    @Override
    public void updateMove(int col, int row, Piece move) {

    }

    @Override
    public void findWinner() {
        for (int i = 0; i < 5; i++){
            int countPlayer = 0, countAI = 0;
            for (int j = 0; j < 4; j++) {
                switch (pieces[i][j]){
                    case BLUE:
                        countPlayer++; break;
                    case GREEN:countAI++;break;
                }
            }
            if (countPlayer == 4){
                boardUI.notifyWinner(new Winner(Piece.BLUE, 0, i, 3, i));
                break;
            } else if (countAI == 4) {
                boardUI.notifyWinner(new Winner(Piece.GREEN, 0, i, 3, i));
                break;
            } else if (countAI != 4 & countPlayer != 4 & existLegalMoves()) {
                boardUI.notifyWinner(new Winner(Piece.EMPTY));
            } else {
                for (int x = 0; x < 5; x++) {
                    int countPlayer1 = 0, countAI1 = 0;
                    for (int y = 1; y < 5; y++) {
                        switch (pieces[y][x]){
                            case BLUE:countPlayer1++; break;
                            case GREEN:countAI1++; break;
                            default: break;
                        }
                    }
                    if (countPlayer1 == 4) {
                        boardUI.notifyWinner(new Winner(Piece.BLUE, 1, x, 4, x));
                        break;
                    } else if (countAI1 == 4) {
                        boardUI.notifyWinner(new Winner(Piece.GREEN, 1, x, 4, x));
                        break;
                    } else {
                        for (int b = 0; b < 5; b++) {
                            int countPlayer2 = 0, countAI2 = 0;
                            for (int a = 2; a < 6; a++) {
                                switch (pieces[a][b]){
                                    case BLUE:countPlayer2++; break;
                                    case GREEN:countAI2++; break;
                                    default: break;
                                }
                            }
                            if(patternMethod(2,5, b, b, countPlayer2, countAI2)) break;
                        }
                    }
                }
            }
        }

        for(int i = 0; i < 6; i++){
            int countPlayer = 0, countAI = 0;
            for (int j = 0; j < 4; j++) {
                switch (pieces[i][j]){
                    case BLUE: countPlayer++; break;
                    case GREEN: countAI++; break;
                    default: break;
                }
                if (countPlayer == 4) {
                    boardUI.notifyWinner(new Winner(Piece.BLUE, i, 0, i , 3));
                    break;
                } else if (countAI == 4) {
                    boardUI.notifyWinner(new Winner(Piece.GREEN, i, 0, i , 3));
                    break;
                } else if (countPlayer != 4 & countAI != 4 & existLegalMoves()) {
                    boardUI.notifyWinner(new Winner(Piece.EMPTY));
                } else {
                    for (int x = 0; x < 6; x++) {
                        int countPlayer1 = 0, countAI1 = 0;
                        for (int y = 1; y < 5; y++) {
                            switch (pieces[x][y]){
                                case BLUE: countPlayer1++; break;
                                case GREEN: countAI1++; break;
                                default: break;
                            }
                            if (patternMethod(x,x,1,4, countPlayer1, countAI1)) {
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean patternMethod(int col1, int col2, int row1, int row2, int countPlayer, int countAI) {
        if (countPlayer == 4){
            boardUI.notifyWinner(new Winner(Piece.BLUE, col1, row1, col2, row2));
            return true;
        } else if (countAI == 4) {
            boardUI.notifyWinner(new Winner(Piece.GREEN, col1, row1, col2, row2));
            return true;
        }
        return false;
    }


}
