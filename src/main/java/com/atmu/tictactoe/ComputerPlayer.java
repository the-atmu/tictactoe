package com.atmu.tictactoe;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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
                managePotentialMove(row,col);
                return true;
            }
        }
        return false;
    }

    boolean checkPotentialLoss(){
        if(diagonal(P1_MARK)) return true;
        if(reverseDiagonal(P1_MARK)) return true;
        if(rowforloop(P1_MARK)) return true;
        if(colforloop(P1_MARK)) return true;
        return false;
    }

    boolean checkPotentialWin(){
        if(diagonal(P2_MARK)) return true;
        if(reverseDiagonal(P2_MARK)) return true;
        if(rowforloop(P2_MARK)) return true;
        if(colforloop(P2_MARK)) return true;
        return false;
    }

    private void managePotentialMove(int row,int col){
        game.executeMove(this,row,col);
    }

    public static void strategy(ComputerPlayer player){

        if(player.checkPotentialWin()) return;
        else if (player.checkPotentialLoss()) return;
        else player.randomizeMove();
    }

    void randomizeMove(){
        Random random = new Random();
        int row = random.nextInt(3);
        int col = random.nextInt(3);
        if(game.checkAvailableMove(row,col))
            managePotentialMove(row,col);
        else randomizeMove();
    }
}
