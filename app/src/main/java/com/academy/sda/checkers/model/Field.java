package com.academy.sda.checkers.model;

/**
 * Created by wd42 on 06.05.17.
 */

public class Field {

    private int row;
    private int column;


    public Field(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean isBlack() {
        return (row + column) % 2 == 0;
    }

    public boolean isNeighbour(Field anotherField) {
        return Math.abs(row - anotherField.getRow()) <= 1 &&
                Math.abs(column - anotherField.getColumn()) <= 1 &&
                !this.equals(anotherField);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Field that = (Field) o;

        if (row != that.row) return false;
        return column == that.column;

    }

    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + column;
        return result;
    }

    @Override
    public String toString() {
        return "Field{" +
                "row=" + row +
                ", column=" + column +
                '}';
    }
}
