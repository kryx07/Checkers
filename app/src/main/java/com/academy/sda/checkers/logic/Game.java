package com.academy.sda.checkers.logic;


import android.util.Log;

import com.academy.sda.checkers.model.Board;
import com.academy.sda.checkers.model.Field;
import com.academy.sda.checkers.model.Player;

import static com.academy.sda.checkers.logic.Game.MoveType.MOVE_FINAL;
import static com.academy.sda.checkers.logic.Game.MoveType.MOVE_ILLEGAL;
import static com.academy.sda.checkers.logic.Game.MoveType.MOVE_NOT_FINAL;
import static com.academy.sda.checkers.model.Player.PLAYER_A;
import static com.academy.sda.checkers.model.Player.PLAYER_NONE;

public class Game {

    public enum MoveType {
        MOVE_FINAL, MOVE_ILLEGAL, MOVE_NOT_FINAL;
    }

    private Player currentPlayer;

    private Board board;

    public Board getBoard() {
        return board;
    }

    public Game() {
        currentPlayer = PLAYER_A;
        board=new Board();
    }

    public MoveType makeMove(Field from, Field to) {
        logDebug("Attempting a move: from: " + from + "to: " + to);
        if (!initialCheck(from, to)) {
            return MOVE_ILLEGAL;
        }
        logDebug("Initial check passed");

        if (from.isNeighbour(to)) {
            logDebug("Target and source are neighbouring fields");
            //Regular moves
            if (board.isFieldEmpty(to)) {
                logDebug("The target field is empty");
                //Pawn is moved(regular move) / flag informs the controller that the move is final
                makeRegularMove(from, to);
                currentPlayer =Player.getEnemy(currentPlayer);
                logDebug("Regular move has been made from: " + from + " to: " + to);
                return MOVE_FINAL;
            }
        }
        if (!isCaptureDistance(from, to)) {
            logDebug("The attempted move is not of a capture distance");
            return MOVE_ILLEGAL;
        } else {
            if (isCapturePossible(from, to)) {
                logDebug("Capture is possible");
                if (isAnotherCapturePossibleFrom(to)) {
                    makeCapturingMove(from, to);
                    logDebug("Capturing move has been made from: " + from + " to: " + to);
                    logDebug("Another capture is possible");
                    return MOVE_NOT_FINAL;
                } else {
                    //Capturing move
                    makeCapturingMove(from, to);
                    currentPlayer = Player.getEnemy(currentPlayer);
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
        Player clickedPlayer = board.getPlayer(from);
        if (!(clickedPlayer==currentPlayer)) {
            logDebug("It's not the turn of "+ clickedPlayer);
            return false;
        }
        if (board.isOutOfBounds(to)) {
            logDebug("Move out of board's bounds");
            return false;
        }
        if (!from.isBlack() || !to.isBlack()) {
            logDebug("Source or target field is not black");
            return false;
        }
        //If it's a regular move we need to check direction, if it's a capture- direction doesn't matter
        if (!isValidDirection(from, to) && !isCapturePossible(from, to)) {
            logDebug("Invalid Direction");
            return false;
        }
        return true;
    }

    private boolean isValidDirection(Field from, Field to) {
        if (currentPlayer == PLAYER_A) {
            return from.getRow() < to.getRow();
        } else {
            return from.getRow() > to.getRow();
        }
    }

    private boolean isCaptureDistance(Field from, Field to) {
        return Math.abs(from.getRow() - to.getRow()) == 2 &&
                Math.abs(from.getColumn() - to.getColumn()) == 2;
    }

    private boolean isEnemyInBetween(Field from, Field to) {
        return board.getPlayer(board.getFieldInBetween(from,to))==Player.getEnemy(currentPlayer);
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
            capturePossible = board.isFieldEmpty(to) && isEnemyInBetween(from, to) ;
        } catch (ArrayIndexOutOfBoundsException e){
            logDebug("Is capture from " + from + " to " + to + " possible? " + false);
            return false;
        }
        logDebug("Is capture from " + from + " to " + to + " possible? " + capturePossible);
        return  capturePossible;

    }

    private void makeCapturingMove(Field from, Field to) {
        board.setField(board.getFieldInBetween(from,to),PLAYER_NONE);
        board.setField(from,PLAYER_NONE);
        board.setField(to,currentPlayer);
    }

    private void makeRegularMove(Field from, Field to) {
        board.setField(to,currentPlayer);
        board.setField(from,PLAYER_NONE);

    }

    private void logDebug(String msg) {
        Log.d(this.getClass().getSimpleName(), msg);
    }

}
