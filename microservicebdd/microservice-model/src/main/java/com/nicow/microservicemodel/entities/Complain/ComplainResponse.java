package com.nicow.microservicemodel.entities.Complain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "complainresponse")
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class ComplainResponse {

    @Id
    private String id;
    private String response;
    private int popularity;
    private String creatorEmail;
    private Date creationDate;

}
