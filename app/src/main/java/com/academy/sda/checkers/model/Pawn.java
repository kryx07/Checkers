package com.academy.sda.checkers.model;


/**
 * Created by wd40 on 08.05.17.
 */

public class Pawn {

    private Player player;
    private boolean isQueen;

    public Pawn(Player player, boolean isQueen) {
        this.player = player;
        this.isQueen = isQueen;
    }

    public Pawn(Player player){
        this.player = player;
    }

    public Player getPlayer() {
        if (player==null){
            return Player.PLAYER_NONE;
        }
            return player;
    }

    public boolean isQueen() {
        return isQueen;
    }

    public void setQueen(boolean queen) {
        this.isQueen = queen;
    }
}
