package com.nicow.microservicemodel.entitiesTest;

import com.nicow.microservicemodel.entities.ComplainRequest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ComplainRequestTest {

    @Test
    public void testToString() {
        ComplainRequest complainRequest = new ComplainRequest();
        complainRequest.setId("1");
        complainRequest.setCreatorEmail("test@test.com");
        complainRequest.setCreationDate(null);
        complainRequest.setPopularity(0);
        complainRequest.setRequest("deconfinement trop rapide?");
        complainRequest.setComplainResponsesId(null);

        Assert.assertEquals("ComplainRequest{id='1', request='deconfinement trop rapide?', " +
                "creatorEmail='test@test.com', creationDate=null, popularity=0, complainResponses=null}",
                complainRequest.toString());
    }
}
