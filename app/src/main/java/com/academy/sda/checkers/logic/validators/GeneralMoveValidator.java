package com.academy.sda.checkers.logic.validators;

import android.util.Log;

import com.academy.sda.checkers.model.Board;
import com.academy.sda.checkers.model.Move;
import com.academy.sda.checkers.model.Player;

import static com.academy.sda.checkers.model.Move.MoveType.MOVE_ILLEGAL;
import static com.academy.sda.checkers.model.Move.MoveType.MOVE_POTENTIALLY_POSSIBLE;

public class GeneralMoveValidator implements MoveValidator{

    private Board board;
    private Player currentPlayer;

    public GeneralMoveValidator(Board board, Player currentPlayer) {
        this.board = board;
        this.currentPlayer = currentPlayer;
    }

    @Override
    public Move.MoveType validate(Move move) {
        if (move.getFrom().equals(move.getTo())) {
            logDebug("From and to coordinates are the same - no move!");
            return MOVE_ILLEGAL;
        }
        Player clickedPlayer = board.getPawn(move.getFrom()).getPlayer();
        if (!(clickedPlayer == currentPlayer)) {
            logDebug("It's not the turn of " + clickedPlayer);
            return MOVE_ILLEGAL;

        }
        if (board.isOutOfBounds(move.getTo())) {
            logDebug("Move out of board's bounds");
            return MOVE_ILLEGAL;

        }
        if (!move.getFrom().isBlack() || !move.getTo().isBlack()) {
            logDebug("Source or target field is not black");
            return MOVE_ILLEGAL;

        }
        if(!board.isFieldEmpty(move.getTo())){
            return MOVE_ILLEGAL;

        }
        return MOVE_POTENTIALLY_POSSIBLE;
    }

    private void logDebug(String msg) {
        Log.e(this.getClass().getSimpleName(), msg);
    }



}
