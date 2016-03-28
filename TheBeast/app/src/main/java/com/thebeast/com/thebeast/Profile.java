package com.thebeast.com.thebeast;

/**
 * Created by Tyler on 3/27/2016.
 */
public class Profile {

    private int wins;
    private int losses;
    private int pointsScored;

    public Profile(int wins, int losses, int pointsScored) {
        this.wins = wins;
        this.losses = losses;
        this.pointsScored = pointsScored;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() { return losses; }

    public int getPointsScored() { return pointsScored; }
}
