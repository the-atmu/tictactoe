package com.atmu.tictactoe;

import org.jblas.DoubleMatrix;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
/*
The board class navigates the game by handling the visual representation of the board(front end)
It starts of by creating a DoubleMatrix object from the jblas external library and fills it with 0's
The behind the scenes implementation works as follows:
The matrix is a 3 by 3 table which contains a 0 if the position has not been used by any player.
It contains 1 guaranteeing that player 1 has taken this position. And finally -1 is the mark of player 2
being either a real player or a computer player.

It also formats the DoubleMatrix object into a string and formats it into a visual representable form. It converts
the contents of the matrix 0 , 1 , -1 into "-" , "X", "O" respectively.
 */
public class Board {
    public Board(){ }

    final DoubleMatrix matrix = new DoubleMatrix(3,3).fill(0);//- = 0 x = 1 O = -1
    Player winner;
    boolean win = false;
    boolean tie = false;


    void printBoard(){
        System.out.println(formatList(getList()));
    }


    private List<String> getList(){
        int[] array = matrix.toIntArray();
        List<String> boardList = Arrays.stream(array).mapToObj(value -> {
            if (value == 0) return "-";
            if (value == -1) return "O";
            if (value == 1) return "X";
            return "-";
        }).collect(Collectors.toList());
        return boardList;
    }

    private String formatList(List<String> list){
            StringBuilder sb = new StringBuilder();
            for(int i=0; i<list.size(); i++){
                sb.append(list.get(i));
                if(i == 2|| i==5 || i==8) sb.append(" \n");
                else sb.append(" | ");
            }
            return sb.toString();
    }

}
