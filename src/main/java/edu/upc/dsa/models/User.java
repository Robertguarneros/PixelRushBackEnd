package edu.upc.dsa.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class User {
    String username;
    String password;
    String mail;
    String name;
    String surname;
    String photo; //.png or .jpg <img src="photo.jpg"> (Front-end job)
    String state;
    String birthDate;

    public void setPointsEarned(int pointsEarned) {
        this.pointsEarned = pointsEarned;
    }

    int pointsEarned; //We need an attribute points to buy!!!!!
    //Played Matches list
    List<Match> matchesPlayed;
    //list of owned objects;
    List<StoreObject> ownedObjects;
    //empty constructor
    public User(){}
    // full constructor
    public User(String username, String password, String mail, String name,
                String surname, String birthDate) {
        this.username = username;
        this.password = password;
        this.mail = mail;
        this.name = name;
        this.surname = surname;
        this.photo = null; //user will put a photo after the register
        this.state = null; //same as photo
        this.birthDate = birthDate;
        this.matchesPlayed = null;//create empty lists of matches
        this.ownedObjects = null;//create empty list of owned objects
        this.pointsEarned = 0;//User starts with 0 points earned
    }
    //all getters and setters from attributes of User class
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public List<Match> getMatchesPlayed() {
        return matchesPlayed;
    }

    public void setMatchesPlayed(List<Match> matchesPlayed) {
        this.matchesPlayed = matchesPlayed;
    }

    public List<StoreObject> getOwnedObjects() {
        return ownedObjects;
    }

    public void setOwnedObjects(List<StoreObject> ownedObjects) {
        this.ownedObjects = ownedObjects;
    }
    public int getPointsEarned() {
        return pointsEarned;
    }

    public void setPointsEarned() {
        int sumOfPointsEarned=0;
        for(Match m:matchesPlayed){
            sumOfPointsEarned = sumOfPointsEarned+m.getTotalPoints();
        }

        this.pointsEarned = sumOfPointsEarned;
    }

    // Methods of our class
    public void addNewFinishedMatch(Match m){ this.matchesPlayed.add(m);}//function to add a new match to the played matches list
    public int addNewOwnedObject(StoreObject newObject){
        if (newObject == null){
            ownedObjects = new ArrayList<>();
        }
        // Check if the item ID is already in ownedObjects
        for (StoreObject ownedObject : ownedObjects) {
            if (ownedObject.getObjectID().equals(newObject.getObjectID())) {
                return -1; // Object with the same ID is already owned
            }
        }
        if(pointsEarned> newObject.getPrice()){
            ownedObjects.add(newObject);
            return 1;
        }else{
            return -2;
        }
    }


}