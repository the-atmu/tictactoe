package com.atmu.tictactoe;

import java.util.Scanner;
/*
This is the player class
Here the logic of choosing a position from the keyboard takes place.
It proposes the player/user to provide two numbers that represent a move on the board, and if the given
position has been taken previously it prompts again the user for another correct input.
Any further logic is processed by the game class.
 */
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

    void chooseMove(){
        if(this.equals(game.player1)){
            System.out.println("Player 1 choose your next move");
            Scanner scanner = new Scanner(System.in);
            System.out.print("(0-2) row: ");
            int i = scanner.nextInt();
            System.out.print("(0-2) col: ");
            int j = scanner.nextInt();

            if(!game.checkAvailableMove(i,j)){
                System.out.println("Choose available position");
                again();
            }else{
                game.executeMove(this,i,j);}
        }else{
            System.out.println("Player 2 choose your next move");
            Scanner scanner = new Scanner(System.in);
            System.out.print("(0-2) row: ");
            int i = scanner.nextInt();
            System.out.print("(0-2) col: ");
            int j = scanner.nextInt();

            if(!game.checkAvailableMove(i,j)){
                System.out.println("Choose available position");
                again();
            }else{
                game.executeMove(this,i,j);}
        }
    }

    private void again(){
        chooseMove();
    }

}
