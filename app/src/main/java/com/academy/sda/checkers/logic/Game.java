package com.academy.sda.checkers.logic;


import android.util.Log;

import com.academy.sda.checkers.logic.validators.GeneralMoveValidator;
import com.academy.sda.checkers.logic.validators.MoveValidator;
import com.academy.sda.checkers.logic.validators.PawnMoveValidator;
import com.academy.sda.checkers.logic.validators.QueenMoveValidator;
import com.academy.sda.checkers.model.*;

import java.util.List;

import static com.academy.sda.checkers.model.Move.MoveType.*;
import static com.academy.sda.checkers.model.Player.*;

public class Game {

    private MoveValidator moveValidator;
    private Player currentPlayer;
    private Board board;

    public Board getBoard() {
        return board;
    }

    public Game() {
        this.currentPlayer = PLAYER_A;
        this.board = new Board();
    }

    public Move.MoveType attemptMove(Move move) {

        logDebug("Attempting a move: from: " + move.getFrom() + "to: " + move.getTo());
        moveValidator = new GeneralMoveValidator(board, currentPlayer);
        if (moveValidator.validate(move)==MOVE_ILLEGAL) {
            return MOVE_ILLEGAL;
        }
        logDebug("Initial check passed");

        return movePawn(move);
    }

    private Move.MoveType movePawn(Move move) {
        if (board.getPawn(move.getFrom()).isQueen()) {
            logDebug("Attempting to move Queen");
            moveValidator = new QueenMoveValidator(currentPlayer, board);
        } else {
            logDebug("Attempting to move Pawn");
            moveValidator = new PawnMoveValidator(currentPlayer, board);
        }
        Move.MoveType moveType = moveValidator.validate(move);

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

    private void makeRegularMove(Move move) {
        board.setField(move.getTo(), board.getPawn(move.getFrom()));
        board.setField(move.getFrom(), new Pawn(PLAYER_NONE));

        if (isEndOfBoard(move.getTo())) {
            makeQueen(move.getTo());
        }

    }

    private void makeCapturingMove(Move move) {
        board.setField(move.getTo(), board.getPawn(move.getFrom()));
        board.setField(move.getFrom(), new Pawn(PLAYER_NONE));

        List<Field> fieldsBetween = board.getFieldsInBetween(move);
        for (int i = 0; i < fieldsBetween.size(); ++i) {
            board.setField(fieldsBetween.get(i), new Pawn(PLAYER_NONE));
        }

        if (!board.getPawn(move.getFrom()).isQueen()) {
            if (isEndOfBoard(move.getTo())) {
                makeQueen(move.getTo());
            }
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
