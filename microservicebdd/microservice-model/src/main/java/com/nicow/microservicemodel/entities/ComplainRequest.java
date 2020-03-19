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

@Document(collection = "complainRequest")
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class ComplainRequest {

    @Id
    private String id;
    private String request;
    private String creatorEmail;
    private Date creationDate;
    private int popularity;
//    @DBRef(lazy = true)
    private Collection<ComplainResponse> complainResponses=new ArrayList<>();
}
