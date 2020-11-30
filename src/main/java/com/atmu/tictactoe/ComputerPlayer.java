package com.atmu.tictactoe;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
/*
* Here the logic exists that can detect a certain threat or a possibility of a win
* If there's no potential of losing the algorithms chooses to move towards a win.
* If a threat by player 1 is detected, meaning that player 1 is one move away from winning then it can
* block the potential win of player 1. Player 2 in computer mode however is not unbeatable
* it cannot foresee possibilities of winning like other algorithms such as minimax.
 */
public class ComputerPlayer extends Player {
    final int P1_MARK = 2;
    final int P2_MARK = -2;

    ComputerPlayer(Game game, Board board){
        super(game,board);
    }

    private boolean rowforloop(int mark){
        int row,col;
        for(int i=0; i<3; i++) {
            int[] arr = board.matrix.getRow(i).toIntArray();
            int sum = (int) board.matrix.getRow(i).sum();
            if (sum == mark) {
                row = Arrays.stream(arr).boxed().collect(Collectors.toList()).indexOf(0);
                col = i;
                if (game.checkAvailableMove(row, col)) {
                    managePotentialMove(row,col);
                    System.out.printf("Computer played row: %d col: %d%n",row,col);
                    return true;
                }
            }
        }

        return false;
    }

    private boolean colforloop(int mark){
        int row,col;
        for(int i=0; i<3; i++) {
            int[] arr = board.matrix.getColumn(i).toIntArray();
            int sum = (int) board.matrix.getColumn(i).sum();

            if (sum == mark) {
                row = i;
                col = Arrays.stream(arr).boxed().collect(Collectors.toList()).indexOf(0);
                if (game.checkAvailableMove(row, col)) {
                    managePotentialMove(row,col);
                    System.out.printf("Computer played row: %d col: %d%n",row,col);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean diagonal(int mark){
        int row ,col;
        int diagonalSum = (int)board.matrix.diag().sum();
        int[] array = board.matrix.dup().diag().toIntArray();

        if(diagonalSum == mark) {
            row = Arrays.stream(array).boxed().collect(Collectors.toList()).indexOf(0);
            col = row;
            if(game.checkAvailableMove(row,col)){
                managePotentialMove(row,col);
                System.out.printf("Computer played row: %d col: %d%n",row,col);
                return true;
            }
        }
        return false;
    }

    private boolean reverseDiagonal(int mark){
        int row,col;
        int[] array = board.matrix.dup().swapColumns(0,2).diag().toIntArray();
        int reverseDiagonalSum = (int)board.matrix.dup().swapColumns(0,2).diag().sum();

        if(reverseDiagonalSum == mark){
            List<Integer> list = Arrays.stream(array).boxed().collect(Collectors.toList());
            Collections.reverse(list);
            col = Arrays.stream(array).boxed().collect(Collectors.toList()).indexOf(0);
            row = list.indexOf(0);
            if(game.checkAvailableMove(row,col)){
                System.out.printf("Computer played row: %d col: %d%n",row,col);
                managePotentialMove(row,col);
                return true;
            }
        }
        return false;
    }

    private boolean checkPotentialLoss(){
        return diagonal(P1_MARK) || reverseDiagonal(P1_MARK)
                || rowforloop(P1_MARK) || colforloop(P1_MARK);
    }

    private boolean checkPotentialWin(){
        return diagonal(P2_MARK) || reverseDiagonal(P2_MARK)
                || rowforloop(P2_MARK) || colforloop(P2_MARK);
    }

    private void managePotentialMove(int row,int col){
        game.executeMove(this,row,col);
    }

    @Override
    public void chooseMove(){
        if(this.checkPotentialWin());
        else if (this.checkPotentialLoss());
        else this.randomizeMove();
    }

    private void randomizeMove(){
        Random random = new Random();
        int row = random.nextInt(3);
        int col = random.nextInt(3);
        if(game.checkAvailableMove(row,col)) {
            managePotentialMove(row, col);
            System.out.printf("Computer played row: %d col: %d%n",row,col);
        }
        else randomizeMove();
    }
}
