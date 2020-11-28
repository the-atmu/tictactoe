package com.atmu.tictactoe;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Game {

    final Board board = new Board();
    final Player player1 = new Player(this,board);
    Player player2;
    ComputerPlayer computerPlayer;

    boolean checkAvailableMove(int i,int j){
        return (board.matrix.get(j, i)) == 0;
    }

    boolean diagonalWinState(){
        int[] arr = board.matrix.diag().toIntArray();
        return IntStream.of(arr)
                .allMatch(x->Arrays.stream(arr)
                        .sum()==Math.abs(3));

    }

    boolean revWinState(){
        int[] arr = board.matrix.dup().swapColumns(0,2).diag().toIntArray();
        return IntStream.of(arr)
                .allMatch(x->Arrays.stream(arr)
                        .sum()==Math.abs(3));
    }

    boolean rowColWinState(){
        for(int i=0; i<3; i++){
            int columnSum = (int)board.matrix.getColumn(i).sum();
            if(Math.abs(columnSum)==3){
                return true;
            }

            int rowSum = (int)board.matrix.getRow(i).sum();
            if(Math.abs(rowSum)==3) {
                return true;
            }
        }
        return false;
    }


    private boolean checkWinningState(){
        if(diagonalWinState()){
            board.win = true;
            return true;
        }

        if(revWinState()){
            board.win = true;
            return true;
        }

        if(rowColWinState()){
            board.win = true;
            return true;
        }
        return board.win;
    }

    private boolean checkTieState(){
        return player1.moves == 5;
    }

    void executeMove(Player player,int i,int j){
        player.moves++;
        if(player.equals(player1)) {
            board.matrix.put(j,i,1);
        }else board.matrix.put(j,i,-1);

        if(checkWinningState()){
            player.winner = true;
            board.winner = player;
        }

        if(checkTieState()){
            board.tie = true;
        }
    }

    void tryagain(Player player){
        if(player.equals(player1)) player.chooseMoveOne();
        else player2.chooseMoveTwo();
    }

    public void runComputerGame(){
        computerPlayer = new ComputerPlayer(this,board);
        do{
            player1.chooseMoveOne();
            board.printBoard();
            if(board.win || board.tie) break;
            ComputerPlayer.strategy(computerPlayer);
            board.printBoard();
        }while(!(board.win || board.tie));

        if(player1.equals(board.winner)) System.out.println("Player 1 has won!");
        else if(computerPlayer.equals(board.winner)) System.out.println("Computer Player has won!");
        else System.out.println("Tie!");
    }


    public void runGame(){
        player2 = new Player(this,board);
         do {
            player1.chooseMoveOne();
            board.printBoard();
            if(board.win || board.tie) break;
            player2.chooseMoveTwo();
            board.printBoard();
        }while(!(board.win || board.tie));

        if(player1.equals(board.winner)) System.out.println("Player 1 has won!");
        else if(player2.equals(board.winner)) System.out.println("Player 2 has won!");
        else System.out.println("Tie!");
    }
}
