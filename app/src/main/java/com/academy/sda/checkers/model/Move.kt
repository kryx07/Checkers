package com.academy.sda.checkers.model

import android.support.annotation.Nullable
import com.academy.sda.checkers.model.Move.Direction.NORTH_EAST
import com.academy.sda.checkers.model.Move.Direction.NORTH_WEST
import com.academy.sda.checkers.model.Move.Direction.SOUTH_EAST
import com.academy.sda.checkers.model.Move.Direction.SOUTH_WEST

data class Move(val from: Field?, val to: Field?) {

    enum class MoveType {
        CAPTURE_FINAL, MOVE_FINAL, MOVE_ILLEGAL, CAPTURE_NOT_FINAL, MOVE_POTENTIALLY_POSSIBLE
    }


    enum class Direction private constructor(val rowOffset: Int, val columnOffset: Int) {
        NORTH_EAST(-1, 1), NORTH_WEST(-1, -1), SOUTH_EAST(1, 1), SOUTH_WEST(1, -1)
    }

    val direction: Direction
        get() {
            if (to!!.row > from!!.row && to.column > from.column) {
                return SOUTH_EAST
            } else if (to.row < from.row && to.column < from.column) {
                return NORTH_WEST
            } else if (to.row > from.row && to.column < from.column) {
                return SOUTH_WEST
            } else {
                return NORTH_EAST
            }
        }

    fun getReverseDirection(direction: Direction): Direction? {
        return Direction.values().firstOrNull {
            it.rowOffset == -1 * direction.rowOffset &&
                    it.columnOffset == -1 * direction.columnOffset
        }
    }

    fun getRowDistance(): Int = Math.abs(from!!.row - to!!.row)

    fun getColumnDistance(): Int = Math.abs(from!!.column - to!!.column)

    fun isRegularDistance(): Boolean = getRowDistance() == 1 && getColumnDistance() == 1

    fun isCapturingDistance(): Boolean = getRowDistance() == 2 && getColumnDistance() == 2

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false

        val move = o as Move?

        if (if (from != null) from != move!!.from else move!!.from != null) return false
        return if (to != null) to == move.to else move.to == null

    }

    override fun hashCode(): Int {
        var result = from?.hashCode() ?: 0
        result = 31 * result + (to?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Move{" +
                "from=" + from +
                ", to=" + to +
                '}'
    }
}
