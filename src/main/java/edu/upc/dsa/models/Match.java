package edu.upc.dsa.models;

import java.util.ArrayList;
import java.util.List;

public class Match {
    String username;
    int totalPoints;
    int currentLVL;
    int maxLVL;
    boolean isInMatch;
    List<Integer> pointsObtainedPerLevel;

    //empty constructor
    public Match(){this.isInMatch=false;}//must have isInMatch = false to avoid errors.

    //fully constructor
    public Match(String username ) {
        this.username = username;
        this.totalPoints = 0;//user always starts with 0 points
        this.currentLVL = 0;//user always start at level 0
        //This value will change at the end of the project because it depends on the number od levels we make, we start with 3
        this.maxLVL = 3;
        this.isInMatch = true;
        this.pointsObtainedPerLevel = new ArrayList<>();
    }

    //all getters and setters from Match class
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public List<Integer> getPointsObtainedPerLevel() {
        return pointsObtainedPerLevel;
    }

    public void setPointsObtainedPerLevel(List<Integer> pointsObtainedPerLevel) {
        this.pointsObtainedPerLevel = pointsObtainedPerLevel;
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

    //methods
    public void nextLevel(int points){//method to change to the next level
        this.currentLVL = this.currentLVL+1;
        this.totalPoints = this.totalPoints+points;
        pointsObtainedPerLevel.add(points);
    }

    public void endMatch(int points){//method to end the match if the user is at the last level
        this.totalPoints = this.totalPoints+points;
        pointsObtainedPerLevel.add(points);
        this.isInMatch=false;
    }

    public void endMatchNow(){//method used to end the match without finishing the level. Points are not saved.
        this.isInMatch=false;
    }

    public int SumAllPoints(){
        int res =0;
        for (int n : pointsObtainedPerLevel){
            res += n;
        }
        return res;
    }
}
