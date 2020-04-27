package com.nicow.microservicemodel.entities;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Document(collection = "complainRequest")
public class ComplainRequest {

    @Id
    private String id;
    private String request;
    private String creatorEmail;
    private Date creationDate;
    private int popularity;
//    @DBRef(lazy = true)
    private Collection<ComplainResponse> complainResponses=new ArrayList<>();

    // constructor
    public ComplainRequest() {
    }

    public ComplainRequest(String id, String request, String creatorEmail, Date creationDate, int popularity, Collection<ComplainResponse> complainResponses) {
        this.id = id;
        this.request = request;
        this.creatorEmail = creatorEmail;
        this.creationDate = creationDate;
        this.popularity = popularity;
        this.complainResponses = complainResponses;
    }

    // getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
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

    public Collection<ComplainResponse> getComplainResponses() {
        return complainResponses;
    }

    public void setComplainResponses(Collection<ComplainResponse> complainResponses) {
        this.complainResponses = complainResponses;
    }

    // to string
    @Override
    public String toString() {
        return "ComplainRequest{" +
                "id='" + id + '\'' +
                ", request='" + request + '\'' +
                ", creatorEmail='" + creatorEmail + '\'' +
                ", creationDate=" + creationDate +
                ", popularity=" + popularity +
                ", complainResponses=" + complainResponses +
                '}';
    }
}
