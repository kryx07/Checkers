package com.academy.sda.checkers.logic.validators;

import android.util.Log;

import com.academy.sda.checkers.model.Board;
import com.academy.sda.checkers.model.Field;
import com.academy.sda.checkers.model.Move;
import com.academy.sda.checkers.model.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.academy.sda.checkers.model.Move.MoveType.CAPTURE_FINAL;
import static com.academy.sda.checkers.model.Move.MoveType.CAPTURE_NOT_FINAL;
import static com.academy.sda.checkers.model.Move.MoveType.MOVE_FINAL;
import static com.academy.sda.checkers.model.Move.MoveType.MOVE_ILLEGAL;

public class QueenMoveValidator implements MoveValidator {
    private Player currentPlayer;
    private Board board;

    public QueenMoveValidator(Player currentPlayer, Board board) {
        this.currentPlayer = currentPlayer;
        this.board = board;
    }

    @Override
    public Move.MoveType validate(Move move) {

        if (!isDiagonal(move)) {
            logDebug("The move" + move + "is not diagonal");
            return MOVE_ILLEGAL;
        }

        List<Field> fieldsBetween = board.getFieldsInBetween(move);
        if (areNoPawnsBetween(fieldsBetween)) {
            logDebug("There are no pawns between " + move);
            return MOVE_FINAL;
        }

        if (areMultiplePawnsBetween(fieldsBetween)) {
            logDebug("The are multiple pawns between " + move);
            return MOVE_ILLEGAL;
        }

        if (isEnemyBetween(fieldsBetween)) {
            logDebug("An enemy is between " + move);
            if (isAnotherCapturePossibleFrom(move)) {
                logDebug("There is another capture possible from " + move.getTo());
                return CAPTURE_NOT_FINAL;
            }
            logDebug("There are no more captures possible from " + move.getTo() + " - the move " + move + " is final");
            return CAPTURE_FINAL;
        }

        logDebug("The move " + move + " is illegal");
        return MOVE_ILLEGAL;
    }

    private boolean isAnotherCapturePossibleFrom(Move move) {

        Map<Move.Direction, Field> enemyFields = getDirectEnemies(move.getTo());
        enemyFields.remove(move.getReverseDirection(move.getDirection()));

        for (Field enemyField : enemyFields.values()) {
            if (canEnemyBeCaptured(new Move(move.getTo(), enemyField))) {
                return true;
            }
        }
        return false;

    }

    private Map<Move.Direction, Field> getDirectEnemies(Field field) {
        Map<Move.Direction, Field> enemyFields = new HashMap<>();
        Field tmpField = field;
        Player tmpPlayer;
        for (Move.Direction direction : Move.Direction.values()) {
            while (!board.isOutOfBounds(tmpField)) {
                tmpPlayer = board.getPawn(tmpField).getPlayer();
                if (isEnemy(tmpPlayer)) {
                    enemyFields.put(direction, tmpField);
                    break;
                }
                tmpField = board.move(tmpField, direction);
            }
            tmpField = field;
        }
        return enemyFields;

    }

    private boolean canEnemyBeCaptured(Move move) {
        List<Field> fieldsBetween = board.getFieldsInBetween(move);
        Field destination = board.move(move.getTo(), move.getDirection());
        if (board.isOutOfBounds(destination)) {
            return false;
        }
        return areNoPawnsBetween(fieldsBetween) && board.isFieldEmpty(destination);
    }

    private boolean isEnemy(Player player) {
        return player.equals(Player.getEnemy(currentPlayer));
    }

    private boolean isFriend(Player player) {
        return player.equals(currentPlayer);
    }

    private boolean isEnemyBetween(List<Field> fieldsBetween) {
        for (Field field : fieldsBetween) {
            if (board.getPawn(field).getPlayer().equals(Player.getEnemy(currentPlayer))) {
                return true;
            }
        }
        return false;
    }

    private boolean areMultiplePawnsBetween(List<Field> fieldsBetween) {
        int counter = 0;
        for (Field field : fieldsBetween) {
            if (!board.getPawn(field).getPlayer().equals(Player.PLAYER_NONE)) {
                ++counter;
            }
        }
        return counter > 1;
    }

    private boolean areNoPawnsBetween(List<Field> fieldsBetween) {
        for (Field field : fieldsBetween) {
            if (!board.getPawn(field).getPlayer().equals(Player.PLAYER_NONE)) {
                return false;
            }
        }
        return true;

    }

    private boolean isDiagonal(Move move) {
        return Math.abs(move.getFrom().getRow() - move.getTo().getRow()) ==
                Math.abs(move.getFrom().getColumn() - move.getTo().getColumn());
    }

    private void logDebug(String msg) {
        Log.e(this.getClass().getSimpleName(), msg);
    }


}
