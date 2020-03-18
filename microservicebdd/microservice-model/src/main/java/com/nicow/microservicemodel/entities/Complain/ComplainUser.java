package com.nicow.microservicemodel.entities.Complain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document(collection = "complainUser")
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class ComplainUser {

    @Id
    private String id;
    private String name;
    private String firstName;
    private String pseudo;
    private String email;
    private String password;
    private int popularity;
    private Date creationDate;
    private String role;
}
