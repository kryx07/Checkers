package com.academy.sda.checkers.model;

public class Board {

    private Player[][] board = new Player[8][8];

    public Board() {
        initNewBoard();
    }

    public void initNewBoard() {
        Field field;
        for (int i = 0; i <= 2; ++i) {
            for (int j = 0; j < 8; j++) {
                field = new Field(i, j);
                if ((field.isBlack())) {
                    setField(field, Player.PLAYER_A);
                }
            }
        }

        for (int i = 5; i <= 7; ++i) {
            for (int j = 0; j < 8; j++) {
                field = new Field(i, j);
                if ((field.isBlack())) {
                    setField(field, Player.PLAYER_B);
                }
            }
        }
    }


    public void setField(Field field, Player player) {
        board[field.getRow()][field.getColumn()] = player;
    }
}
