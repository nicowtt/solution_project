package com.nicow.microservicedao.complainDao;

import com.nicow.microservicemodel.entities.ComplainRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ComplainRequestDao extends MongoRepository<ComplainRequest, String> {
}
