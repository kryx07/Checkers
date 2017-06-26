package com.academy.sda.checkers.model;

import static com.academy.sda.checkers.model.Move.Direction.NORTH_EAST;
import static com.academy.sda.checkers.model.Move.Direction.NORTH_WEST;
import static com.academy.sda.checkers.model.Move.Direction.SOUTH_EAST;
import static com.academy.sda.checkers.model.Move.Direction.SOUTH_WEST;

public class Move {

    private Field from;
    private Field to;

    public Move(Field from, Field to) {
        this.from = from;
        this.to = to;
    }

    public Field getFrom() {
        return from;
    }

    public Field getTo() {
        return to;
    }

    public enum MoveType {
        CAPTURE_FINAL, MOVE_FINAL, MOVE_ILLEGAL, CAPTURE_NOT_FINAL, MOVE_POTENTIALLY_POSSIBLE
    }

    public enum Direction {
        NORTH_EAST(-1, 1), NORTH_WEST(-1, -1), SOUTH_EAST(1, 1), SOUTH_WEST(1, -1);

        private int rowOffset;
        private int columnOffset;

        Direction(int rowOffset, int columnOffset) {
            this.rowOffset = rowOffset;
            this.columnOffset = columnOffset;
        }

        public int getRowOffset() {
            return rowOffset;
        }

        public int getColumnOffset() {
            return columnOffset;
        }
    }

    public Direction getDirection() {
        if (getTo().getRow() > getFrom().getRow() &&
                getTo().getColumn() > getFrom().getColumn()) {
            return SOUTH_EAST;
        } else if (getTo().getRow() < getFrom().getRow() &&
                getTo().getColumn() < getFrom().getColumn()) {
            return NORTH_WEST;
        } else if (getTo().getRow() > getFrom().getRow() &&
                getTo().getColumn() < getFrom().getColumn()) {
            return SOUTH_WEST;
        } else {
            return NORTH_EAST;
        }
    }

   /* public int getRowDistance(){
        return Math.abs(from.getRow()-to.getRow());
    }

    public int getColumnDistance(){
        return Math.abs(from.getColumn()-to.getColumn());
    }*/

    public Direction getReverseDirection(Direction direction) {
        for (Direction dir : Direction.values()) {
            if (dir.getRowOffset() == (-1) * direction.getRowOffset() &&
                    dir.getColumnOffset() == (-1) * direction.getColumnOffset()) {
                return dir;
            }
        }
        return null;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Move move = (Move) o;

        if (from != null ? !from.equals(move.from) : move.from != null) return false;
        return to != null ? to.equals(move.to) : move.to == null;

    }

    @Override
    public int hashCode() {
        int result = from != null ? from.hashCode() : 0;
        result = 31 * result + (to != null ? to.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Move{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }
}
