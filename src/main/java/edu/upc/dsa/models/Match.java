package edu.upc.dsa.models;

import java.util.ArrayList;
import java.util.List;

public class
Match {
    String username;
    int totalPoints;
    int currentLVL;
    int maxLVL;
    //List<Integer> pointsObtainedPerLevel;

    //empty constructor
    public Match(){}//must have isInMatch = false to avoid errors.

    //fully constructor
    public Match(String username ) {
        this.username = username;
        this.totalPoints = 0;//user always starts with 0 points
        this.currentLVL = 1;//user always start at level 1
        //This value will change at the end of the project because it depends on the number od levels we make, we start with 3
        this.maxLVL = 3;
        //this.pointsObtainedPerLevel = new ArrayList<>();
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

   /* public List<Integer> getPointsObtainedPerLevel() {
        return pointsObtainedPerLevel;
    }*/

   /* public void setPointsObtainedPerLevel(List<Integer> pointsObtainedPerLevel) {
        this.pointsObtainedPerLevel = pointsObtainedPerLevel;
    }*/

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


    //methods
    public void nextLevel(int points){//method to change to the next level
        this.currentLVL = this.currentLVL+1;
        this.totalPoints = this.totalPoints+points;
       // pointsObtainedPerLevel.add(points);
    }

    public void endMatchLastLevel(int points){//method to end the match if the user is at the last level
        this.totalPoints = this.totalPoints+points;
       // pointsObtainedPerLevel.add(points);
    }
}
