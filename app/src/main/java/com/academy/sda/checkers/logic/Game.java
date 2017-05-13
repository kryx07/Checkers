package com.academy.sda.checkers.logic;


import android.util.Log;

import com.academy.sda.checkers.model.Board;
import com.academy.sda.checkers.model.Field;
import com.academy.sda.checkers.model.Pawn;
import com.academy.sda.checkers.model.Player;

import java.util.Arrays;

import static com.academy.sda.checkers.logic.Move.MoveType.*;
import static com.academy.sda.checkers.model.Player.PLAYER_A;
import static com.academy.sda.checkers.model.Player.PLAYER_B;
import static com.academy.sda.checkers.model.Player.PLAYER_NONE;

public class Game {

    private Player currentPlayer;

    private Board board;

    public Board getBoard() {
        return board;
    }

    public Game() {
        currentPlayer = PLAYER_A;
        board = new Board();
    }

    public Move.MoveType attemptMove(Move move) {
        logDebug("Attempting a move: from: " + move.getFrom() + "to: " + move.getTo());
        if (!isMoveFormallyImpossible(move)) {
            return MOVE_ILLEGAL;
        }
        logDebug("Initial check passed");

        if (board.getPawn(move.getFrom()).isQueen()) {
            logDebug("Attempting to move Queen");
            return moveQueen();
        } else {
            logDebug("Attempting to move Pawn");
            return movePawn(move);
        }
    }

    private Move.MoveType movePawn(Move move) {
        PawnMoveValidator pawnMoveValidator = new PawnMoveValidator(currentPlayer, board);
        Move.MoveType moveType = pawnMoveValidator.check(move);

        switch (moveType) {
            case MOVE_FINAL: {
                makeRegularMove(move);
                switchPlayersTurn();
                logDebug("Regular move has been made from: " + move.getFrom() + " to: " + move.getTo());
                break;
            }
            case CAPTURE_FINAL: {
                makeCapturingMove(move);
                switchPlayersTurn();
                logDebug("Capturing move has been made from: " + move.getFrom() + " to: " + move.getTo());
                break;
            }
            case CAPTURE_NOT_FINAL: {
                makeCapturingMove(move);
                logDebug("Capturing move has been made from: " + move.getFrom() + " to: " + move.getTo());
                logDebug("Another capture is possible");
                break;
            }
            case MOVE_ILLEGAL: {
                logDebug("Move is illegal.");
                break;
            }
        }

        return moveType;
    }

    private Move.MoveType moveQueen() {

        return null;
    }

    private boolean isMoveFormallyImpossible(Move move) {
        if (move.getFrom().equals(move.getTo())) {
            logDebug("From and to coordinates are the same - no move!");
            return false;
        }
        Player clickedPlayer = board.getPawn(move.getFrom()).getPlayer();
        if (!(clickedPlayer == currentPlayer)) {
            logDebug("It's not the turn of " + clickedPlayer);
            return false;
        }
        if (board.isOutOfBounds(move.getTo())) {
            logDebug("Move out of board's bounds");
            return false;
        }
        if (!move.getFrom().isBlack() || !move.getTo().isBlack()) {
            logDebug("Source or target field is not black");
            return false;
        }
        //If it's a regular check we need to check direction, if it's a capture- direction doesn't matter
        return true;
    }

    private void makeRegularMove(Move move) {
        board.setField(move.getTo(), board.getPawn(move.getFrom()));
        board.setField(move.getFrom(), new Pawn(PLAYER_NONE));

        if (isEndOfBoard(move.getTo())) {
            makeQueen(move.getTo());
        }

    }


    private void makeCapturingMove(Move move) {
        board.setField(move.getTo(), board.getPawn(move.getFrom()));
        board.setField(board.getFieldInBetween(move), new Pawn(PLAYER_NONE));
        board.setField(move.getFrom(), new Pawn(PLAYER_NONE));

        if (isEndOfBoard(move.getTo())) {
            makeQueen(move.getTo());
        }
    }

    private boolean isEndOfBoard(Field field) {
        final int PLAYER_A_LIMIT = 7;
        final int PLAYER_B_LIMIT = 0;
        return board.getPawn(field).getPlayer() == PLAYER_A && field.getRow() == PLAYER_A_LIMIT ||
                board.getPawn(field).getPlayer() == PLAYER_B && field.getRow() == PLAYER_B_LIMIT;
    }

    private void makeQueen(Field field) {
        Pawn pawn = board.getPawn(field);
        pawn.setQueen(true);
        board.setField(field, pawn);
        logDebug(pawn.getPlayer() + " has got a new queen in: " + field);
    }

    private void switchPlayersTurn() {
        currentPlayer = Player.getEnemy(currentPlayer);
    }

    private void logDebug(String msg) {
        Log.e(this.getClass().getSimpleName(), msg);
    }


}
