package com.academy.sda.checkers.logic;


import android.util.Log;

import com.academy.sda.checkers.model.Board;
import com.academy.sda.checkers.model.Field;

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

    private Board board2;

    private int[][] board = new int[8][8];

    public int[][] getBoard() {
        return board;
    }

    public Game() {
        currentPlayer = PLAYER_A;
        Field field;
        for (int i = 0; i <= 2; ++i) {
            for (int j = 0; j < 8; j++) {
                field = new Field(i, j);
                if (isFieldBlack(field)) {
                    setField(field, PLAYER_A);
                }
            }
        }

        for (int i = 5; i <= 7; ++i) {
            for (int j = 0; j < 8; j++) {
                field = new Field(i, j);
                if (isFieldBlack(field)) {
                    setField(field, PLAYER_B);
                }
            }
        }
    }

    public MoveType makeMove(Field from, Field to) {
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

    private boolean initialCheck(Field from, Field to) {
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

    private boolean isFieldBlack(Field field) {
        return (field.getRow() + field.getColumn()) % 2 == 0;
    }

    private boolean isOutOfBounds(Field field) {
        return field.getRow() < 0 || field.getColumn() < 0 ||
                field.getRow() > 7 || field.getColumn() > 7;
    }

    private boolean isCurrentPlayer(Field from) {
        int player = board[from.getRow()][from.getColumn()];
        return currentPlayer == player;
    }

    private boolean isValidDirection(Field from, Field to) {
        if (currentPlayer == PLAYER_A) {
            return from.getRow() < to.getRow();
        } else {
            return from.getRow() > to.getRow();
        }
    }

    private boolean isNeighbour(Field from, Field to) {
        return Math.abs(from.getRow() - to.getRow()) <= 1 &&
                Math.abs(from.getColumn() - to.getColumn()) <= 1;
    }

    private boolean isCaptureDistance(Field from, Field to) {
        return Math.abs(from.getRow() - to.getRow()) == 2 &&
                Math.abs(from.getColumn() - to.getColumn()) == 2;
    }

    private boolean isEnemyInBetween(Field from, Field to) {
        Field fieldInBetween = getFieldInBetween(from, to);
        return board[fieldInBetween.getRow()][fieldInBetween.getColumn()] == getEnemy(currentPlayer);
    }

    private Field getFieldInBetween(Field from, Field to) {
        return new Field((from.getRow() + to.getRow()) / 2,
                (from.getColumn() + to.getColumn()) / 2);
    }

    private void setField(Field field, int player) {
        board[field.getRow()][field.getColumn()] = player;
    }

    private boolean isFieldEmpty(Field field) {
        return board[field.getRow()][field.getColumn()] == PLAYER_NONE;
    }

    private int getEnemy(int player) {
        return (-1) * player;
    }

    private boolean isAnotherCapturePossibleFrom(Field field) {
        return isCapturePossible(field,
                        new Field(field.getRow() + 2, field.getColumn() + 2)) ||
                isCapturePossible(field,
                        new Field(field.getRow() - 2, field.getColumn() - 2)) ||
                isCapturePossible(field,
                        new Field(field.getRow() + 2, field.getColumn() - 2)) ||
                isCapturePossible(field,
                        new Field(field.getRow() - 2, field.getColumn() + 2));
    }

    private boolean isCapturePossible(Field from, Field to) {
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

    private void makeCapturingMove(Field from, Field to) {
        setField(getFieldInBetween(from, to), PLAYER_NONE);
        setField(from, PLAYER_NONE);
        setField(to, currentPlayer);

    }

    private void makeRegularMove(Field from, Field to) {
        setField(to, currentPlayer);
        setField(from, PLAYER_NONE);
        currentPlayer = getEnemy(currentPlayer);
    }

    private void logDebug(String msg) {
        Log.d(this.getClass().getSimpleName(), msg);
    }

}
