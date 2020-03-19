package com.nicow.microservicemodel.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Document(collection = "complainTheme")
@Data @AllArgsConstructor @NoArgsConstructor @ToString
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
}
