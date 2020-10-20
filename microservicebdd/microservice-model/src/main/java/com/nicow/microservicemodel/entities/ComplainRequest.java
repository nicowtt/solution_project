package com.nicow.microservicemodel.entities;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Document(collection = "complainRequest")
public class ComplainRequest {

    @Id
    private String id;
    private String request;
    private String creatorPseudo;
    private String creatorEmail;
    private String creationDate;
    private int popularity;
    private List<String> userWhoChangePopularityList;
//    @DBRef(lazy = true)
    private List<String> complainResponsesId;
    private String themeName;
    private String lastResponseDate;
    private Boolean forgotten;

    // constructor
    public ComplainRequest() {
    }

    public ComplainRequest(String id, String request, String creatorPseudo, String creatorEmail, String creationDate,
                           int popularity, List<String> userWhoChangePopularityList,
                           List<String> complainResponsesId, String themeName,
                           String lastResponseDate, Boolean forgotten) {
        this.id = id;
        this.request = request;
        this.creatorPseudo = creatorPseudo;
        this.creatorEmail = creatorEmail;
        this.creationDate = creationDate;
        this.popularity = popularity;
        this.userWhoChangePopularityList = userWhoChangePopularityList;
        this.complainResponsesId = complainResponsesId;
        this.themeName = themeName;
        this.lastResponseDate = lastResponseDate;
        this.forgotten = forgotten;
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

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public List<String> getUserWhoChangePopularityList() {
        return userWhoChangePopularityList;
    }

    public void setUserWhoChangePopularityList(List<String> userWhoChangePopularityList) {
        this.userWhoChangePopularityList = userWhoChangePopularityList;
    }

    public List<String> getComplainResponsesId() {
        return complainResponsesId;
    }

    public void setComplainResponsesId(List<String> complainResponsesId) {
        this.complainResponsesId = complainResponsesId;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public String getLastResponseDate() {
        return lastResponseDate;
    }

    public void setLastResponseDate(String lastResponseDate) {
        this.lastResponseDate = lastResponseDate;
    }

    public Boolean isForgetIt() { return forgotten; }

    public void setForgetIt(Boolean forgotten) { this.forgotten = forgotten; }

    // to string
    @Override
    public String toString() {
        return "ComplainRequest{" +
                "id='" + id + '\'' +
                ", request='" + request + '\'' +
                ", creatorPseudo='" + creatorPseudo + '\'' +
                ", creatorEmail='" + creatorEmail + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", popularity=" + popularity +
                ", userWhoChangePopularityList=" + userWhoChangePopularityList +
                ", complainResponsesId=" + complainResponsesId +
                ", themeName='" + themeName + '\'' +
                ", lastResponseDate='" + lastResponseDate + '\'' +
                ", forgetIt=" + forgotten +
                '}';
    }

    // methods
    public void addUserWhoIncreasePopularity(String userInput) {
        this.userWhoChangePopularityList.add(userInput);
    }
    public void addResponseIdOnRequest(String responseId) { this.complainResponsesId.add(responseId); }
}
