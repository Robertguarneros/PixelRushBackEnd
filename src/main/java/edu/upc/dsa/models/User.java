package edu.upc.dsa.models;

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
    int age;
    //empty constructor
    int allPoints; //We need an attribute points to buy!!!!!
    //Played Matches list
    List<Match> matchesPlayed;
    //list of owned objects;
    List<StoreObject> ownedObjects;

    public User(){}
    // fully constructor
    public User(String username, String password, String mail, String name,
                String surname, String photo, String state, int age) {
        this.username = username;
        this.password = password;
        this.mail = mail;
        this.name = name;
        this.surname = surname;
        this.photo = photo;
        this.state = state;
        this.age = age;
        this.matchesPlayed = new ArrayList<>();//create empty lists of matches
        this.ownedObjects = new ArrayList<>();//create empty list of owned objects
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

    // Methods of our class
    public void addNewFinishedMatch(Match m){ this.matchesPlayed.add(m);}//function to add a new match to the played matches list

}
