package com.nicow.microservicemodel.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "complainComment")
public class ComplainComment {

    @Id
    private String id;
    private String comment;
    private String creatorPseudo;
    private String creatorEmail;
    private String creationDate;
    private String responseId;

    // constructor
    public ComplainComment() {}

    public ComplainComment(String id, String comment, String creatorPseudo, String creatorEmail,
                           String creationDate, String responseId) {
        this.id = id;
        this.comment = comment;
        this.creatorPseudo = creatorPseudo;
        this.creatorEmail = creatorEmail;
        this.creationDate = creationDate;
        this.responseId = responseId;
    }

    // getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreatorPseudo() {
        return creatorPseudo;
    }

    public void setCreatorPseudo(String creatorPseudo) {
        this.creatorPseudo = creatorPseudo;
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

    public String getResponseId() {
        return responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    // to string
    @Override
    public String toString() {
        return "ComplainComment{" +
                "id='" + id + '\'' +
                ", comment='" + comment + '\'' +
                ", creatorPseudo='" + creatorPseudo + '\'' +
                ", creatorEmail='" + creatorEmail + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", responseId='" + responseId + '\'' +
                '}';
    }
}
