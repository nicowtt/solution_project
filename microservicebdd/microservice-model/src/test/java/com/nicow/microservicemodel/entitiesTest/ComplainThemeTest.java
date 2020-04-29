package com.nicow.microservicemodel.entitiesTest;

import com.nicow.microservicemodel.entities.ComplainTheme;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ComplainThemeTest {

    @Test
    public void testToString() {
        ComplainTheme complainTheme = new ComplainTheme();
        complainTheme.setId("1");
        complainTheme.setName("covid19");
        complainTheme.setComplainRequests(null);
        complainTheme.setCreationDate(null);
        complainTheme.setCreatorEmail("nico.bod@gmail.com");
        complainTheme.setPhotoUrl("testUrl");
        complainTheme.setPopularity(0);
        complainTheme.setSubscriptionThemeUsers(null);

        Assert.assertEquals("ComplainTheme{id='1', name='covid19', photoUrl='testUrl', " +
                        "creatorEmail='nico.bod@gmail.com', creationDate=null, popularity=0, " +
                        "complainRequests=null, subscriptionThemeUsers=null}",
                complainTheme.toString());

    }
}
