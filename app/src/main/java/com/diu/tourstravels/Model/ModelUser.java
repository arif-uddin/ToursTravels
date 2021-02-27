package com.diu.tourstravels.Model;

public class ModelUser {

    private String id;
    private String username;
    private String email;
    private String contactNo;
    private String imageURL;

    public ModelUser() {
    }

    public ModelUser(String id, String username, String email, String contactNo, String imageURL) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.contactNo = contactNo;
        this.imageURL = imageURL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
