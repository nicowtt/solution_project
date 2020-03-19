package com.nicow.microservicedao.complainDao;

import com.nicow.microservicemodel.entities.ComplainResponse;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ComplainResponseDao extends MongoRepository<ComplainResponse, String> {
}
