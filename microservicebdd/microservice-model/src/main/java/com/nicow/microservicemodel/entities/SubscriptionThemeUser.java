package com.nicow.microservicemodel.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "SubscriptionThemeUser")
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class SubscriptionThemeUser {

    @Id
    private String id;
    private String emailSubscriptionUser;
}
