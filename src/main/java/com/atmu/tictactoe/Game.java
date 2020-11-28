package com.atmu.tictactoe;

import java.util.Arrays;
import java.util.stream.IntStream;

/*Game class handles all the mechanics and logic of the game
* It manages a board and two players
* Player 1 is always the user, and navigates it's course in the game by the inputs of the keyboard
* The coordination system works with zero base row's and columns. For example 0[enter]2[enter] means
* mark my position at row 0 and column 2
*
* The respective methods can always detect if the board is in a winning state
* if it is, then it can approve a winner based on the player with the most moves
* in the moment of the detection of the winning state.
*
* The game can be accessed with either the runGame() method, which is the known duel mode
* or with the runComputerGame() which invokes the second player to be processed and managed by an algorithm
* that analyzed player 1's moves, which is the class of ComputerPlayer
*
* At the end of the match, the winning player is announced.
*
*/
public class Game {

    final Board board = new Board();
    final Player player1 = new Player(this,board);
    Player player2;

    private void checkState(){
        if(checkWinningState()){
            player1.winner = player1.moves > player2.moves;
            player2.winner = !player1.winner;
            board.winner = player1.winner ? player1 : player2;
        }

        if(checkTieState()){
            board.tie = true;
        }
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

    private boolean diagonalWinState(){
        int[] arr = board.matrix.dup().diag().toIntArray();
        return IntStream.of(arr)
                .allMatch(x->Arrays.stream(arr)
                        .sum()==Math.abs(3));

    }

    private boolean revWinState(){
        int[] arr = board.matrix.dup().swapColumns(0,2).diag().toIntArray();
        return IntStream.of(arr)
                .allMatch(x->Arrays.stream(arr)
                        .sum()==Math.abs(3));
    }

    private boolean rowColWinState(){
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

    private boolean checkTieState(){
        return player1.moves == 5;
    }

    boolean checkAvailableMove(int i,int j){
        return (board.matrix.get(j, i)) == 0;
    }

    void executeMove(Player player,int i,int j){

        if(player.equals(player1)) {
            board.matrix.put(j,i,1);
        }
        else board.matrix.put(j,i,-1);

        player.moves++;

    }

    private void computerGameProcess(){
        do{
            player1.chooseMove();
            checkState();
            board.printBoard();
            if(board.win || board.tie) break;
            player2.chooseMove();
            checkState();
            board.printBoard();
        }while(!(board.win || board.tie));
    }

    private void gameProcess(){
        do {
            player1.chooseMove();
            checkState();
            board.printBoard();
            if(board.win || board.tie) break;
            player2.chooseMove();
            checkState();
            board.printBoard();
        }while(!(board.win || board.tie));
    }

    private void announceWinner(){
        if(player1.equals(board.winner)){
            System.out.println("Player 1 has won!");
        }
        else if(player2.equals(board.winner)){
            if(player2 instanceof ComputerPlayer){
                System.out.println("Computer has won!");
            }else{
                System.out.println("Player 2 has won!");
            }
        }
        else System.out.println("Tie!");
    }


    public void runComputerGame(){
        player2 = new ComputerPlayer(this,board);
        computerGameProcess();
        announceWinner();
    }


    public void runGame(){
        player2 = new Player(this,board);
        gameProcess();
        announceWinner();
    }
}
