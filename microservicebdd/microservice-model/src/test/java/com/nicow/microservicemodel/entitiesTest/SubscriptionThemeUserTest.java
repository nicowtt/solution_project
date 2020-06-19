package com.nicow.microservicemodel.entitiesTest;


import com.nicow.microservicemodel.entities.SubscriptionThemeUser;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SubscriptionThemeUserTest {

    @Test
    public void testToString() {
        SubscriptionThemeUser subscriptionThemeUser = new SubscriptionThemeUser();
        subscriptionThemeUser.setId("1");
        subscriptionThemeUser.setEmailSubscriptionUser("test@test.com");

        Assert.assertEquals("SubscriptionThemeUser{id='1', emailSubscriptionUser='test@test.com'}", subscriptionThemeUser.toString());
    }


}
