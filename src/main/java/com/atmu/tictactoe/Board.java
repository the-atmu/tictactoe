package com.atmu.tictactoe;

import org.jblas.DoubleMatrix;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
