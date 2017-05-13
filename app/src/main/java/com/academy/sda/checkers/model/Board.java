package com.academy.sda.checkers.model;

import android.util.Log;

import com.academy.sda.checkers.logic.Move;

import java.util.ArrayList;
import java.util.List;

import static com.academy.sda.checkers.model.Player.PLAYER_A;
import static com.academy.sda.checkers.model.Player.PLAYER_B;
import static com.academy.sda.checkers.model.Player.PLAYER_NONE;

public class Board {

    private Pawn[][] board = new Pawn[8][8];

    public Board() {
        initNewBoard();
    }

    public void initNewBoard() {
        Field field;

        for (int i = 0; i < board.length; ++i) {
            for (int j = 0; j < board.length; j++) {
                field = new Field(i, j);
                if ((field.isBlack())) {
                    setField(field, new Pawn(PLAYER_NONE));
                }
            }
        }

        for (int i = 0; i <= 2; ++i) {
            for (int j = 0; j < board.length; j++) {
                field = new Field(i, j);
                if ((field.isBlack())) {
                    setField(field, new Pawn(PLAYER_A));
                }
            }
        }

        for (int i = 5; i <= 7; ++i) {
            for (int j = 0; j < board.length; j++) {
                field = new Field(i, j);
                if ((field.isBlack())) {
                    setField(field, new Pawn(PLAYER_B));
                }
            }
        }

    }

    public Pawn getPawn(Field field) {
        Pawn pawn = board[field.getRow()][field.getColumn()];
        return pawn == null ? new Pawn(PLAYER_NONE) : pawn;
    }

    public void setField(Field field, Pawn pawn) {
        board[field.getRow()][field.getColumn()] = pawn;
    }

    public boolean isFieldEmpty(Field field) {
        Player thisPlayer = board[field.getRow()][field.getColumn()].getPlayer();
        logDebug(field + " contains a pawn of: " + thisPlayer);
        return thisPlayer == PLAYER_NONE;
    }

    public Field getFieldInBetween(Move move) {
        return new Field((move.getFrom().getRow() + move.getTo().getRow()) / 2,
                (move.getFrom().getColumn() + move.getTo().getColumn()) / 2);
    }



    public List<Field> getFieldsInBetween(Move move) {

        List<Field> fields = new ArrayList<>();
        Field newField = new Field(-1, -1);
        Move.Direction direction = move.getDirection();

        int counter = 1;
        switch (direction) {
            case SOUTH_EAST:
                while (!newField.isNeighbour(move.getFrom())) {
                    newField = new Field(move.getTo().getRow() - counter, move.getTo().getColumn() - counter);
                    fields.add(newField);
                    ++counter;
                }
                break;
            case NORTH_WEST:
                while (!newField.isNeighbour(move.getFrom())) {
                    newField = new Field(move.getTo().getRow() + counter, move.getTo().getColumn() + counter);
                    fields.add(newField);
                    ++counter;
                }
                break;
            case SOUTH_WEST:
                while (!newField.isNeighbour(move.getFrom())) {
                    newField = new Field(move.getTo().getRow() - counter, move.getTo().getColumn() + counter);
                    fields.add(newField);
                    ++counter;
                }
                break;
            case NORTH_EAST:
                while (!newField.isNeighbour(move.getFrom())) {
                    newField = new Field(move.getTo().getRow() + counter, move.getTo().getColumn() - counter);
                    fields.add(newField);
                    ++counter;
                }
                break;
        }
        return fields;
    }


    public boolean isOutOfBounds(Field field) {
        return field.getRow() < 0 || field.getColumn() < 0 ||
                field.getRow() > board.length - 1 || field.getColumn() > board.length - 1;
    }

    public int size() {
        return board.length;
    }

    private void logDebug(String msg) {
        Log.e(this.getClass().getSimpleName(), msg);
    }



}
