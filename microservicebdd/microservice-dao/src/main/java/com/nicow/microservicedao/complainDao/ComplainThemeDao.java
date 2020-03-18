package com.nicow.microservicedao.complainDao;

import com.nicow.microservicemodel.entities.Complain.ComplainTheme;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ComplainThemeDao extends MongoRepository<ComplainTheme, String> {

}
