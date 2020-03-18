package com.nicow.microservicedao.subscriptionDao;

import com.nicow.microservicemodel.entities.subscription.SubscriptionThemeUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface SubscriptionThemeUserDao extends MongoRepository<SubscriptionThemeUser, String> {
}
