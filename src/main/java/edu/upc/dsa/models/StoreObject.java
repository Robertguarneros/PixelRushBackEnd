package edu.upc.dsa.models;

public class StoreObject {
    String objectID;
    String articleName;
    int price;
    String description;
    String articlePhoto; //same as attribute photo from User class

    //empty constructor
    public StoreObject(){}

    //fully constructor
    public StoreObject(String objectID, String articleName, int price,
                       String description, String articlePhoto) {
        this.objectID = objectID;
        this.articleName = articleName;
        this.price = price;
        this.description = description;
        this.articlePhoto = articlePhoto;
    }

    //all getters and setters from Store class
    public String getObjectID() {
        return objectID;
    }

    public void setObjectID(String objectID) {
        this.objectID = objectID;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getArticlePhoto() {
        return articlePhoto;
    }

    public void setArticlePhoto(String articlePhoto) {
        this.articlePhoto = articlePhoto;
    }
}
