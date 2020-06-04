package com.nicow.microservicemodel.dto;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


public class ComplainUserDto {

    private String id;
    private String name;
    private String firstName;
    private String pseudo;
    private String email;
    private String password;
    private int popularity;
    private Collection<Integer> peopleForPopularity = new ArrayList<>();
    private String creationDate;
    private String role;
    private String token;

    // constructor
    public ComplainUserDto() {
    }

    public ComplainUserDto(String id, String name, String firstName, String pseudo, String email, String password, int popularity, Collection<Integer> peopleForPopularity, String creationDate, String role, String token) {
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
        this.token = token;
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

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    // to string
    @Override
    public String toString() {
        return "ComplainUserDto{" +
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
                ", token='" + token + '\'' +
                '}';
    }
}
