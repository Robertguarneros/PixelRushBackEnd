package edu.upc.dsa.models;

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
}
