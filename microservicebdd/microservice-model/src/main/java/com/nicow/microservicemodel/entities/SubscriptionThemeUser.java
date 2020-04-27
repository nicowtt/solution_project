package com.nicow.microservicemodel.entities;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "SubscriptionThemeUser")
public class SubscriptionThemeUser {

    @Id
    private String id;
    private String emailSubscriptionUser;

    // constructor
    public SubscriptionThemeUser() {
    }

    public SubscriptionThemeUser(String id, String emailSubscriptionUser) {
        this.id = id;
        this.emailSubscriptionUser = emailSubscriptionUser;
    }

    // getter and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmailSubscriptionUser() {
        return emailSubscriptionUser;
    }

    public void setEmailSubscriptionUser(String emailSubscriptionUser) {
        this.emailSubscriptionUser = emailSubscriptionUser;
    }

    // to string
    @Override
    public String toString() {
        return "SubscriptionThemeUser{" +
                "id='" + id + '\'' +
                ", emailSubscriptionUser='" + emailSubscriptionUser + '\'' +
                '}';
    }
}
