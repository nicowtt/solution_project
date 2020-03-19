package com.nicow.microservicedao.complainDao;

import com.nicow.microservicemodel.entities.ComplainUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ComplainUserDao extends MongoRepository<ComplainUser, String> {

    ComplainUser findByEmail(String userEmail);
}
