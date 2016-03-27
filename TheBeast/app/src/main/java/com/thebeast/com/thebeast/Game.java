package com.thebeast.com.thebeast;

import java.io.Serializable;

/**
 * Created by loganpatino on 3/25/16.
 */
public class Game implements Serializable {

    private String additionalInfo;
    private String location;
    private String sport;
    private int teamSize;
    private int time;

    public Game() {

    }

    public Game(String additionalInfo, String location, String sport, int teamSize, int time) {
        this.additionalInfo = additionalInfo;
        this.location = location;
        this.sport = sport;
        this.teamSize = teamSize;
        this.time = time;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public String getLocation() {
        return location;
    }

    public String getSport() {
        return sport;
    }

    public int getTeamSize() {
        return teamSize;
    }

    public int getTime() {
        return time;
    }
}
