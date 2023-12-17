package edu.upc.dsa.models;

public class OwnedObjects {
    int id;//No se usa, solo es para que no de error al usar el setter.
    String username;
    String objectID;

    public OwnedObjects(){};
    public OwnedObjects(String username, String objectID) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
