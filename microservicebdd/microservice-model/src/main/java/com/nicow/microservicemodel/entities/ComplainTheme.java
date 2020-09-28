package com.nicow.microservicemodel.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Document(collection = "complainTheme")
public class ComplainTheme {

    @Id
    private String id;
    private String name;
    private String photoUrl;
    private String creatorEmail;
    private String creationDate;
    private int popularity;
//    @DBRef(lazy = true)
    private Collection<String> complainRequestsId;
//    @DBRef
    private Collection<String> subscriptionThemeUsersId;

    // constructor
    public ComplainTheme() {
    }

    public ComplainTheme(String id, String name, String photoUrl, String creatorEmail, String creationDate, int popularity, Collection<String> complainRequestsId, Collection<String> subscriptionThemeUsersId) {
        this.id = id;
        this.name = name;
        this.photoUrl = photoUrl;
        this.creatorEmail = creatorEmail;
        this.creationDate = creationDate;
        this.popularity = popularity;
        this.complainRequestsId = complainRequestsId;
        this.subscriptionThemeUsersId = subscriptionThemeUsersId;
    }

    // getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getCreatorEmail() {
        return creatorEmail;
    }

    public void setCreatorEmail(String creatorEmail) {
        this.creatorEmail = creatorEmail;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public Collection<String> getComplainRequestsId() {
        return complainRequestsId;
    }

    public void setComplainRequestsId(Collection<String> complainRequestsId) {
        this.complainRequestsId = complainRequestsId;
    }

    public Collection<String> getSubscriptionThemeUsersId() {
        return subscriptionThemeUsersId;
    }

    public void setSubscriptionThemeUsersId(Collection<String> subscriptionThemeUsersId) {
        this.subscriptionThemeUsersId = subscriptionThemeUsersId;
    }

    // to string
    @Override
    public String toString() {
        return "ComplainTheme{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", creatorEmail='" + creatorEmail + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", popularity=" + popularity +
                ", complainRequestsId=" + complainRequestsId +
                ", subscriptionThemeUsersId=" + subscriptionThemeUsersId +
                '}';
    }

    // methods
    public void addComplainRequestId(String requestId) {
        this.complainRequestsId.add(requestId);
    }
}
