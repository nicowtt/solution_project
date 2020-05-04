package com.nicow.microservicemodel.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Document(collection = "complainUser")
public class ComplainUser {

    @Id
    private String id;
    private String name;
    private String firstName;
    private String pseudo;
    private String email;
    private String password;
    private int popularity;
    private Collection<Integer> peopleForPopularity = new ArrayList<>();
    private Date creationDate;
    private String role;

    // constructor
    public ComplainUser() {
    }

    public ComplainUser(String id, String name, String firstName, String pseudo, String email, String password, int popularity, Collection<Integer> peopleForPopularity, Date creationDate, String role) {
        this.id = id;
        this.name = name;
        this.firstName = firstName;
        this.pseudo = pseudo;
        this.email = email;
        this.password = password;
        this.popularity = popularity;
        this.peopleForPopularity = peopleForPopularity;
        this.creationDate = creationDate;
        this.role = role;
    }

    //getters and setters
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public Collection<Integer> getPeopleForPopularity() {
        return peopleForPopularity;
    }

    public void setPeopleForPopularity(Collection<Integer> peopleForPopularity) {
        this.peopleForPopularity = peopleForPopularity;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // to string
    @Override
    public String toString() {
        return "ComplainUser{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", firstName='" + firstName + '\'' +
                ", pseudo='" + pseudo + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", popularity=" + popularity +
                ", peopleForPopularity=" + peopleForPopularity +
                ", creationDate=" + creationDate +
                ", role='" + role + '\'' +
                '}';
    }
}
