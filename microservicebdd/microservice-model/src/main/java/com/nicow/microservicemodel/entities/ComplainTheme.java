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
    private Date creationDate;
    private int popularity;
//    @DBRef(lazy = true)
    private Collection<ComplainRequest> complainRequests=new ArrayList<>();
//    @DBRef
    private Collection<SubscriptionThemeUser> subscriptionThemeUsers=new ArrayList<>();

    // constructor
    public ComplainTheme() {
    }

    public ComplainTheme(String id, String name, String photoUrl, String creatorEmail, Date creationDate, int popularity, Collection<ComplainRequest> complainRequests, Collection<SubscriptionThemeUser> subscriptionThemeUsers) {
        this.id = id;
        this.name = name;
        this.photoUrl = photoUrl;
        this.creatorEmail = creatorEmail;
        this.creationDate = creationDate;
        this.popularity = popularity;
        this.complainRequests = complainRequests;
        this.subscriptionThemeUsers = subscriptionThemeUsers;
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public Collection<ComplainRequest> getComplainRequests() {
        return complainRequests;
    }

    public void setComplainRequests(Collection<ComplainRequest> complainRequests) {
        this.complainRequests = complainRequests;
    }

    public Collection<SubscriptionThemeUser> getSubscriptionThemeUsers() {
        return subscriptionThemeUsers;
    }

    public void setSubscriptionThemeUsers(Collection<SubscriptionThemeUser> subscriptionThemeUsers) {
        this.subscriptionThemeUsers = subscriptionThemeUsers;
    }

    // to string
    @Override
    public String toString() {
        return "ComplainTheme{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", creatorEmail='" + creatorEmail + '\'' +
                ", creationDate=" + creationDate +
                ", popularity=" + popularity +
                ", complainRequests=" + complainRequests +
                ", subscriptionThemeUsers=" + subscriptionThemeUsers +
                '}';
    }
}
