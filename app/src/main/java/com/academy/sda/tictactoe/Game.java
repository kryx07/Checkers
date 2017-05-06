package com.academy.sda.tictactoe;


public class Game {

    //private Board board;
    public final static int PLAYER_NONE = 0;
    public final static int PLAYER_A = 1;
    public final static int PLAYER_B = 2;
    private int currentPlayer;

    private int[][] board = new int[8][8];

    public Game() {
        for (int i = 0; i <= 2; ++i) {
            for (int j = 0; j < 8; j++) {
                if (isBlack(i, j)) {
                    board[i][j] = PLAYER_A;
                }
            }
        }

        for (int i = 5; i <= 7; ++i) {
            for (int j = 0; j < 8; j++) {
                if (isBlack(i, j)) {
                    board[i][j] = PLAYER_B;
                }
            }
        }
    }

    private boolean move(Coordinates from, Coordinates to) {

        if (!isCurrentPlayer(board[from.getRow()][from.getColumn()])) {
            return false;
        }
        if (isOutOfBounds(to)) {
            return false;
        }
        if (!(isBlack(from) && isBlack(to))) {
            return false;
        }
        if (!isValidDirection(from, to)) {
            return false;
        }
        if (isNeigbour(from, to)) {
            //Regular moves


        } else {
            if (!isCaptureDistance(from, to)) {
                return false;
            } else {
                //Capturing moves
            }
        }

        return true;

    }

    private boolean isBlack(Coordinates coordinates) {
        return (coordinates.getRow() + coordinates.getColumn()) % 2 == 0;
    }

    private boolean isBlack(int x, int y) {
        return (x + y) % 2 == 0;
    }

    private boolean isOutOfBounds(Coordinates coordinates) {
        return coordinates.getRow() < 0 || coordinates.getColumn() < 0 ||
                coordinates.getRow() > 7 || coordinates.getColumn() > 7;
    }

    private boolean isCurrentPlayer(int player) {
        return currentPlayer == player;
    }

    private boolean isValidDirection(Coordinates from, Coordinates to) {
        if (currentPlayer == PLAYER_A) {
            return from.getRow() < to.getRow();
        } else {
            return from.getRow() > to.getRow();
        }
    }

    private boolean isNeigbour(Coordinates from, Coordinates to) {
        return Math.abs(from.getRow() - to.getRow()) <= 1 &&
                Math.abs(from.getColumn() - to.getColumn()) <= 1;
    }

    private boolean isCaptureDistance(Coordinates from, Coordinates to) {
        return Math.abs(from.getRow() - to.getRow()) <= 2 &&
                Math.abs(from.getColumn() - to.getColumn()) <= 2 &&
                from.getColumn() != to.getColumn() && from.getRow() != to.getColumn();
    }

    private boolean wasEnemyCaptured() {

    }

    private boolean isFieldEmpty(){
        
    }
}
