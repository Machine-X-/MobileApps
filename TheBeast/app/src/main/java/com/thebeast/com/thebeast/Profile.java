package com.thebeast.com.thebeast;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Tyler on 3/27/2016.
 */

public class Profile {

    private String name;
    private int wins;
    private int losses;
    private IndividualSportInfo basketball;
    private IndividualSportInfo football;
    private IndividualSportInfo soccer;
    private IndividualSportInfo volleyball;

    public Profile() { }

    public Profile(String name) {
        this.name = name;
        this.wins = 0;
        this.losses = 0;
        this.basketball = new IndividualSportInfo();
        this.football = new IndividualSportInfo();
        this.soccer = new IndividualSportInfo();
        this.volleyball = new IndividualSportInfo();
    }

    public String getName() { return name; }

    public int getWins() {
        return wins;
    }

    public int getLosses() { return losses; }

    public IndividualSportInfo getBasketball() {
        return basketball;
    }

    public IndividualSportInfo getFootball() {
        return football;
    }

    public IndividualSportInfo getSoccer() {
        return soccer;
    }

    public IndividualSportInfo getVolleyball() {
        return volleyball;
    }
}
