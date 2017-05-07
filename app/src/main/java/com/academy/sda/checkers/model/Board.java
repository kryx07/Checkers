package com.academy.sda.checkers.model;

import android.util.Log;

import java.net.DatagramSocketImpl;

import static com.academy.sda.checkers.model.Player.*;

public class Board {

    private Player[][] board = new Player[8][8];

    public Board() {
        initNewBoard();
    }

    public void initNewBoard() {
        Field field;

        for (int i = 0; i < board.length; ++i) {
            for (int j = 0; j < board.length; j++) {
                field = new Field(i, j);
                if ((field.isBlack())) {
                    setField(field, PLAYER_NONE);
                }
            }
        }

        for (int i = 0; i <= 2; ++i) {
            for (int j = 0; j < board.length; j++) {
                field = new Field(i, j);
                if ((field.isBlack())) {
                    setField(field, PLAYER_A);
                }
            }
        }

        for (int i = 5; i <= 7; ++i) {
            for (int j = 0; j < board.length; j++) {
                field = new Field(i, j);
                if ((field.isBlack())) {
                    setField(field, PLAYER_B);
                }
            }
        }



    }

    public Player getPlayer(Field field) {
        return board[field.getRow()][field.getColumn()];
    }

    public void setField(Field field, Player player) {
        board[field.getRow()][field.getColumn()] = player;
    }

    public boolean isFieldEmpty(Field field) {
        Player thisPlayer = board[field.getRow()][field.getColumn()];
        logDebug(field + " contains: " + thisPlayer);
        return  thisPlayer == PLAYER_NONE;
    }

    public Field getFieldInBetween(Field f1, Field f2) {
        return new Field((f1.getRow() + f2.getRow()) / 2,
                (f1.getColumn() + f2.getColumn()) / 2);
    }



    public boolean isOutOfBounds(Field field) {
        return field.getRow() < 0 || field.getColumn() < 0 ||
                field.getRow() > board.length - 1 || field.getColumn() > board.length - 1;
    }

    public int size(){
        return board.length;
    }

    private void logDebug(String msg) {
        Log.d(this.getClass().getSimpleName(), msg);
    }
}
