package edu.upc.dsa.models;

import java.util.List;

public class UserBadges {
    String user;
    List<Badge> badges;

    public UserBadges(){}
    public UserBadges(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<Badge> getBadges() {
        return badges;
    }

    public void setBadges(List<Badge> badges) {
        this.badges = badges;
    }
    public void addBadge(Badge badge){
        this.badges.add(badge);
    }
}
