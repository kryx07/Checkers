package com.academy.sda.checkers.logic;

import com.academy.sda.checkers.model.Board;
import com.academy.sda.checkers.model.Player;

public class QueenMoveValidator {
    private Player currentPlayer;
    private Board board;

    public QueenMoveValidator(Player currentPlayer, Board board) {
        this.currentPlayer = currentPlayer;
        this.board = board;
    }

    public Move.MoveType check(Move move) {
        return null;
    }



}
