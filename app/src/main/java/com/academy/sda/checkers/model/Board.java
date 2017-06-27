package com.academy.sda.checkers.model;

import android.util.Log;

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

    private void initNewBoard() {
        Field field;

        for (int i = 0; i < board.length; ++i) {
            for (int j = 0; j < board.length; j++) {
                field = new Field(i, j);
                if ((field.isBlack())) {
                    if(i<=2){
                        setField(field, new Pawn(PLAYER_A));
                    }else if(i>=5 && i<=7){
                        setField(field, new Pawn(PLAYER_B));
                    }else{
                        setField(field, new Pawn(PLAYER_NONE));
                    }

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
        Field newField = move.getFrom();
        int fieldsCount = Math.abs(move.getFrom().getColumn() - move.getTo().getColumn()) - 1;

        for (int i = 0; i < fieldsCount; ++i) {
            newField = move(newField, move.getDirection());
            fields.add(newField);
        }

        return fields;
    }


    public Field move(Field from, Move.Direction direction) {
        return new Field(from.getRow() + direction.getRowOffset(),
                from.getColumn() + direction.getColumnOffset());

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
