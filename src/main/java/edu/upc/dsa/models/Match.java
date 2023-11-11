package edu.upc.dsa.models;

public class Match {
    String username;
    int points;
    int currentLVL;
    int maxLVL;
    boolean isInMatch;
    //we don't need pointsPerLevel because already exist on InfoLVL class

    //empty constructor
    public Match(){}

    //fully constructor
    public Match(String username, int points, int currentLVL, boolean isInMatch) {
        this.username = username;
        this.points = points;
        this.currentLVL = currentLVL;
        //This value will change at the end of the project because it depends on the number od levels we make
        //this.maxLVL = 0;
        this.isInMatch = isInMatch;
    }

    //all getters and setters from Match class
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getCurrentLVL() {
        return currentLVL;
    }

    public void setCurrentLVL(int currentLVL) {
        this.currentLVL = currentLVL;
    }

    public int getMaxLVL() {
        return maxLVL;
    }

    public void setMaxLVL(int maxLVL) {
        this.maxLVL = maxLVL;
    }

    public boolean isInMatch() {
        return isInMatch;
    }

    public void setInMatch(boolean inMatch) {
        isInMatch = inMatch;
    }
}
