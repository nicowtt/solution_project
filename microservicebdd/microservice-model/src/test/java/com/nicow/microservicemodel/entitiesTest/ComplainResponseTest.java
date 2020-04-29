package com.nicow.microservicemodel.entitiesTest;

import com.nicow.microservicemodel.entities.ComplainResponse;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ComplainResponseTest {

    @Test
    public void testToString() {
        ComplainResponse complainResponse = new ComplainResponse();
        complainResponse.setId("1");
        complainResponse.setCreationDate(null);
        complainResponse.setCreatorEmail("nico@test.com");
        complainResponse.setPopularity(0);
        complainResponse.setResponse("test");

        Assert.assertEquals("ComplainResponse{id='1', response='test', popularity=0, " +
                "creatorEmail='nico@test.com', creationDate=null}", complainResponse.toString());
    }
}
