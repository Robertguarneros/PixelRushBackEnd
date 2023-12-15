package edu.upc.dsa.models;

public class OwnedObject {
    String username;
    String objectID;

    public OwnedObject(String username, String objectID) {
        this.username = username;
        this.objectID = objectID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getObjectID() {
        return objectID;
    }

    public void setObjectID(String objectID) {
        this.objectID = objectID;
    }
}
