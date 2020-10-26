package com.nicow.microservicedao.complainDao;

import com.nicow.microservicemodel.entities.ComplainComment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ComplainCommentDao extends MongoRepository<ComplainComment, String> {
}
