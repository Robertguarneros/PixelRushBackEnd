package edu.upc.dsa.models;

import java.util.ArrayList;
import java.util.List;

public class Badge {
    String user;
    String name;
    String avatar;

    public Badge(){}

    public Badge(String userB,String nameB, String avatarB) {
        this.user = userB;
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}