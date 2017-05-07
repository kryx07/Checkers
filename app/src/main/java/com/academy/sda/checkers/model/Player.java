package com.academy.sda.checkers.model;

/**
 * Created by wd40 on 07.05.17.
 */

public enum Player {

    PLAYER_NONE("No player", 0), PLAYER_A("Player A", 1), PLAYER_B("Player B", -1);

    private String description;
    private int value;

    Player(String description, int value) {
        this.description = description;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    private static Player getPlayerByValue(int value) {
        for (Player p : values()) {
            if (p.value == value) {
                return p;
            }
        }
        return null;
    }

    public static Player getEnemy(Player player) {
        return getPlayerByValue(player.value * (-1));
    }

    @Override
    public String toString() {
        return description;
    }
}
