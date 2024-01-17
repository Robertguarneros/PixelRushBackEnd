package edu.upc.dsa.models;

public class Badge {
    String name;
    String avatar;

    public Badge(){}

    public Badge(String nameB, String avatarB) {
        this.name = nameB;
        this.avatar = avatarB;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

}