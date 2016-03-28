package com.thebeast.com.thebeast;

/**
 * Created by Tyler on 3/27/2016.
 */
public class Profile {

    private String name;
    private int wins;
    private int losses;
    private int pointsScored;

    public Profile() { }

    public Profile(String name, int wins, int losses, int pointsScored) {
        this.name = name;
        this.wins = wins;
        this.losses = losses;
        this.pointsScored = pointsScored;
    }

    public String getName() { return name; }

    public int getWins() {
        return wins;
    }

    public int getLosses() { return losses; }

    public int getPointsScored() { return pointsScored; }
}
