package com.nicow.microservicemodel.entitiesTest;

import com.nicow.microservicemodel.entities.ComplainUser;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ComplainUserTest {

    @Test
    public void testToString() {
        ComplainUser complainUser = new ComplainUser();
        complainUser.setId("1");
        complainUser.setName("nico");
        complainUser.setFirstName("bod");
        complainUser.setPseudo("nicow");
        complainUser.setEmail("nico.bod@gmail.com");
        complainUser.setPassword("test");
        complainUser.setPopularity(0);
        complainUser.setCreationDate(null);
        complainUser.setRole("ADMIN");

        Assert.assertEquals("ComplainUser{id='1', name='nico', firstName='bod', pseudo='nicow', " +
                "email='nico.bod@gmail.com', password='test', popularity=0, " +
                "creationDate=null, role='ADMIN'}", complainUser.toString());
    }
}
