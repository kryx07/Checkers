package com.academy.sda.tictactoe.logic;


import android.util.Log;

import static com.academy.sda.tictactoe.logic.Game.MoveType.MOVE_FINAL;
import static com.academy.sda.tictactoe.logic.Game.MoveType.MOVE_ILLEGAL;
import static com.academy.sda.tictactoe.logic.Game.MoveType.MOVE_NOT_FINAL;

public class Game {

    public enum MoveType {
        MOVE_FINAL, MOVE_ILLEGAL, MOVE_NOT_FINAL;
    }

    //private Board board;
    public final static int PLAYER_NONE = 0;
    public final static int PLAYER_A = 1;
    public final static int PLAYER_B = -1;

    private int currentPlayer;

    private int[][] board = new int[8][8];

    public int[][] getBoard() {
        return board;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public Game() {
        currentPlayer = PLAYER_A;
        Coordinates coordinates;
        for (int i = 0; i <= 2; ++i) {
            for (int j = 0; j < 8; j++) {
                coordinates = new Coordinates(i, j);
                if (isFieldBlack(coordinates)) {
                    setField(coordinates, PLAYER_A);
                }
            }
        }

        for (int i = 5; i <= 7; ++i) {
            for (int j = 0; j < 8; j++) {
                coordinates = new Coordinates(i, j);
                if (isFieldBlack(coordinates)) {
                    setField(coordinates, PLAYER_B);
                }
            }
        }
    }

    public MoveType makeMove(Coordinates from, Coordinates to) {

        if (from.equals(to)) {
            logDebug("Equal");
            return MOVE_ILLEGAL;
        }

        if (!isCurrentPlayer(from)) {
            logDebug("not your turn");
            return MOVE_ILLEGAL;
        }

        /*if(isOutOfBounds(to)){

        }*/

        if (!isFieldBlack(from) || !isFieldBlack(to)) {
            logDebug("NotBlack");
            return MOVE_ILLEGAL;
        }
        if (!isValidDirection(from, to)) {
            logDebug("Invalid Direction");
            return MOVE_ILLEGAL;
        }
        logDebug("Initial check passed");

        if (isNeighbour(from, to)) {
            logDebug("isNeighbour");
            //Regular moves
            if (isFieldEmpty(to)) {
                logDebug("isEmpty");
                //Pawn is moved(regular move) / flag informs the controller that the move is final
                setField(to, currentPlayer);
                currentPlayer = getEnemy(currentPlayer);
                return MOVE_FINAL;
            }
        }
        if (!isCaptureDistance(from, to)) {
            logDebug("not capture distance");
            return MOVE_ILLEGAL;
        } else {
            if (isEnemyInBetween(from, to)) {
                logDebug("enemy is between");
                if (isAnotherCapturePossibleFrom(to)) {
                    logDebug("another capture is possible");
                    capture(from, to);
                    return MOVE_NOT_FINAL;
                } else {
                    //Capturing move
                    logDebug("capturing move");
                    capture(from, to);
                    currentPlayer = getEnemy(currentPlayer);
                    return MOVE_FINAL;
                }
            }
        }
        logDebug("No legal moves!");
        return MOVE_ILLEGAL;
    }

    private boolean isFieldBlack(Coordinates coordinates) {
        return (coordinates.getRow() + coordinates.getColumn()) % 2 == 0;
    }

    private boolean isOutOfBounds(Coordinates coordinates) {
        return coordinates.getRow() < 0 || coordinates.getColumn() < 0 ||
                coordinates.getRow() > 7 || coordinates.getColumn() > 7;
    }

    private boolean isCurrentPlayer(Coordinates from) {
        int player = board[from.getRow()][from.getColumn()];
        return currentPlayer == player;
    }

    private boolean isValidDirection(Coordinates from, Coordinates to) {
        if (currentPlayer == PLAYER_A) {
            return from.getRow() < to.getRow();
        } else {
            return from.getRow() > to.getRow();
        }
    }

    private boolean isNeighbour(Coordinates from, Coordinates to) {
        return Math.abs(from.getRow() - to.getRow()) <= 1 &&
                Math.abs(from.getColumn() - to.getColumn()) <= 1;
    }

    private boolean isCaptureDistance(Coordinates from, Coordinates to) {
        return Math.abs(from.getRow() - to.getRow()) <= 2 &&
                Math.abs(from.getColumn() - to.getColumn()) <= 2 &&
                from.getColumn() != to.getColumn() && from.getRow() != to.getColumn();
    }

    private boolean isEnemyInBetween(Coordinates from, Coordinates to) {
        Coordinates fieldInBetween = getFieldInBetween(from, to);
        return board[fieldInBetween.getRow()][fieldInBetween.getColumn()] == getEnemy(currentPlayer);
    }

    private Coordinates getFieldInBetween(Coordinates from, Coordinates to) {
        return new Coordinates((from.getRow() + to.getRow()) / 2,
                (from.getColumn() + to.getColumn()) / 2);
    }

    private void setField(Coordinates field, int player) {
        board[field.getRow()][field.getColumn()] = player;
    }

    private boolean isFieldEmpty(Coordinates coordinates) {
        return board[coordinates.getRow()][coordinates.getColumn()] == PLAYER_NONE;
    }

    private int getEnemy(int player) {
        return (-1) * player;
    }

    private boolean isAnotherCapturePossibleFrom(Coordinates coordinates) {
        return isEnemyInBetween(coordinates,
                new Coordinates(coordinates.getRow() + currentPlayer * 2,
                        coordinates.getColumn() + currentPlayer));
    }

    private void capture(Coordinates from, Coordinates to) {
        setField(getFieldInBetween(from, to), PLAYER_NONE);
        setField(to, currentPlayer);
        currentPlayer = getEnemy(currentPlayer);
    }

    private void logDebug(String msg) {
        Log.d(this.getClass().getSimpleName(), msg);

    }

}
