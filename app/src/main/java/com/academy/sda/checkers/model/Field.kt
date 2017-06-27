package com.academy.sda.checkers.model

class Field(val row: Int, val column: Int) {

    val isBlack: Boolean
        get() = (row + column) % 2 == 0

    fun isNeighbour(anotherField: Field): Boolean {
        return Math.abs(row - anotherField.row) <= 1 &&
                Math.abs(column - anotherField.column) <= 1 &&
                this != anotherField
    }

    override fun toString(): String {
        return "Field{$row,$column}"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Field

        if (row != other.row) return false
        if (column != other.column) return false

        return true
    }

    override fun hashCode(): Int {
        var result = row
        result = 31 * result + column
        return result
    }


}
