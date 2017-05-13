package com.academy.sda.checkers.logic;

import com.academy.sda.checkers.model.Field;

import static com.academy.sda.checkers.logic.Move.Direction.NORTH_EAST;
import static com.academy.sda.checkers.logic.Move.Direction.NORTH_WEST;
import static com.academy.sda.checkers.logic.Move.Direction.SOUTH_EAST;
import static com.academy.sda.checkers.logic.Move.Direction.SOUTH_WEST;

/**
 * Created by wd42 on 13.05.17.
 */

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
        CAPTURE_FINAL, MOVE_FINAL, MOVE_ILLEGAL, CAPTURE_NOT_FINAL;
    }

    public enum Direction {
        NORTH_EAST, NORTH_WEST, SOUTH_EAST, SOUTH_WEST;
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
}
