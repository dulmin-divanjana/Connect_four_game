package lk.ijse.dep.service;

import java.util.Random;

public class AiPlayer extends Player {
    public AiPlayer(Board newBoard) {super(newBoard);}

    @Override
    public void movePiece(int col){
        Random input = new Random();
        do{
            col = input.nextInt(6);
            if (col<6 && board.isLegalMove(col)){
                board.updateMove(col, Piece.GREEN);
                board.findWinner();
            }else col = 6;
        }while (col == 6);
    }
}
