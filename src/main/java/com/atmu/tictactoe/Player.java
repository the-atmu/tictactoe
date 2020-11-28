package com.atmu.tictactoe;

import java.util.Scanner;

public class Player {
    Game game;
    Board board;
    boolean winner;
    int moves;
    public Player(Game game,Board board){
        this.game = game;
        this.board = board;
        this.winner = false;
        moves = 0;
    }

    void chooseMoveTwo(){
        System.out.println("Player 2 choose your next move");
        Scanner scanner = new Scanner(System.in);
        System.out.print("(0-2) row: ");
        int i = scanner.nextInt();
        System.out.print("(0-2) col: ");
        int j = scanner.nextInt();

        if(!game.checkAvailableMove(i,j)){
            System.out.println("Choose available position");
            game.tryagain(this);
        }else{
            game.executeMove(this,i,j);}
    }

    void chooseMoveOne(){
        System.out.println("Player 1 choose your next move");
        Scanner scanner = new Scanner(System.in);
        System.out.print("(0-2) row: ");
        int i = scanner.nextInt();
        System.out.print("(0-2) col: ");
        int j = scanner.nextInt();

        if(!game.checkAvailableMove(i,j)){
            System.out.println("Choose available position");
            game.tryagain(this);
        }else{
            game.executeMove(this,i,j);}
    }

}
