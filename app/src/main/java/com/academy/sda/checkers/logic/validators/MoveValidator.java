package com.academy.sda.checkers.logic.validators;

import com.academy.sda.checkers.model.Move;

public interface MoveValidator {
    Move.MoveType validate(Move move);

}
