package com.academy.sda.checkers.logic;


import android.util.Log;

import java.lang.reflect.Array;

import static com.academy.sda.checkers.logic.Game.MoveType.MOVE_FINAL;
import static com.academy.sda.checkers.logic.Game.MoveType.MOVE_ILLEGAL;
import static com.academy.sda.checkers.logic.Game.MoveType.MOVE_NOT_FINAL;

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
        logDebug("Attempting a move: from: " + from + "to: " + to);
        if (!initialCheck(from, to)) {
            return MOVE_ILLEGAL;
        }
        logDebug("Initial check passed");

        if (isNeighbour(from, to)) {
            logDebug("It is a neighbouring field");
            //Regular moves
            if (isFieldEmpty(to)) {
                logDebug("The target field is empty");
                //Pawn is moved(regular move) / flag informs the controller that the move is final
                makeRegularMove(from, to);
                logDebug("Regular move has been made from: " + from + " to: " + to);
                return MOVE_FINAL;
            }
        }
        if (!isCaptureDistance(from, to)) {
            logDebug("not makeCapturingMove distance");
            return MOVE_ILLEGAL;
        } else {
            if (isEnemyInBetween(from, to)) {
                logDebug("enemy is between");
                if (isAnotherCapturePossibleFrom(to)) {
                    makeCapturingMove(from, to);
                    logDebug("Capturing move has been made from: " + from + " to: " + to);
                    logDebug("Another capture is possible");
                    return MOVE_NOT_FINAL;
                } else {
                    //Capturing move
                    makeCapturingMove(from, to);
                    currentPlayer = getEnemy(currentPlayer);
                    logDebug("Capturing move has been made from: " + from + " to: " + to);
                    return MOVE_FINAL;
                }
            }
        }
        logDebug("No legal moves!");
        return MOVE_ILLEGAL;
    }

    private boolean initialCheck(Coordinates from, Coordinates to) {
        if (from.equals(to)) {
            logDebug("From and to coordinates are the same - no move!");
            return false;
        }
        if (!isCurrentPlayer(from)) {
            logDebug("Not your turn");
            return false;
        }
        if (isOutOfBounds(to)) {
            logDebug("Move out of board's bounds");
            return false;
        }
        if (!isFieldBlack(from) || !isFieldBlack(to)) {
            logDebug("Source or target field is not black");
            return false;
        }
        if (!isValidDirection(from, to) && !isEnemyInBetween(from, to)) {
            logDebug("Invalid Direction");
            return false;
        }
        return true;
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
        return Math.abs(from.getRow() - to.getRow()) == 2 &&
                Math.abs(from.getColumn() - to.getColumn()) == 2;
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
        return isCapturePossible(coordinates,
                        new Coordinates(coordinates.getRow() + 2, coordinates.getColumn() + 2)) ||
                isCapturePossible(coordinates,
                        new Coordinates(coordinates.getRow() - 2, coordinates.getColumn() - 2)) ||
                isCapturePossible(coordinates,
                        new Coordinates(coordinates.getRow() + 2, coordinates.getColumn() - 2)) ||
                isCapturePossible(coordinates,
                        new Coordinates(coordinates.getRow() - 2, coordinates.getColumn() + 2));
    }

    private boolean isCapturePossible(Coordinates from, Coordinates to) {
        boolean capturePossible;
        try {
            capturePossible = isFieldEmpty(to) && isEnemyInBetween(from, to) ;
        } catch (ArrayIndexOutOfBoundsException e){
            logDebug("Is capture from " + from + " to " + to + " possible? " + false);
            return false;
        }
        logDebug("Is capture from " + from + " to " + to + " possible? " + capturePossible);
        return  capturePossible;

    }

    private void makeCapturingMove(Coordinates from, Coordinates to) {
        setField(getFieldInBetween(from, to), PLAYER_NONE);
        setField(from, PLAYER_NONE);
        setField(to, currentPlayer);

    }

    private void makeRegularMove(Coordinates from, Coordinates to) {
        setField(to, currentPlayer);
        setField(from, PLAYER_NONE);
        currentPlayer = getEnemy(currentPlayer);
    }

    private void logDebug(String msg) {
        Log.d(this.getClass().getSimpleName(), msg);
    }

}
