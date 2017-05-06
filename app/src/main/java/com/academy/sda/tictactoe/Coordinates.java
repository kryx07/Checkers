package com.academy.sda.tictactoe;

/**
 * Created by wd42 on 06.05.17.
 */

public class Coordinates {

    private int row;
    private int column;


    public Coordinates(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "row=" + row +
                ", column=" + column +
                '}';
    }
}
