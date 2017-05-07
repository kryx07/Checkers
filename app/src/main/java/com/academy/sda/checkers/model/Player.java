package com.academy.sda.checkers.model;

/**
 * Created by wd40 on 07.05.17.
 */

public enum Player {

    PLAYER_NONE(0),PLAYER_A(1),PLAYER_B(-1);

    private int value;

    Player(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public Player getPlayerByValue(int value){
        for(Player p: values()){
            if(p.value==value){
                return p;
            }
        }
        return null;
    }

    public Player getEnemy(Player player){
        return getPlayerByValue(player.value*(-1));
    }
}
