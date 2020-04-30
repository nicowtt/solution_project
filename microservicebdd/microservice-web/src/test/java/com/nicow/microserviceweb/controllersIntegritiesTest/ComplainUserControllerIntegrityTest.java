package com.nicow.microserviceweb.controllersIntegritiesTest;

import com.nicow.microservicebusiness.impl.ComplainUserManagerImpl;
import com.nicow.microservicemodel.dto.ComplainUserDto;
import com.nicow.microservicemodel.entities.ComplainUser;
import com.nicow.microserviceweb.conf.MongoConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertEquals;


//@SpringBootTest(classes = MongoConfig.class)
public class ComplainUserControllerIntegrityTest extends AbstractTest{

    @Autowired
    private ComplainUserManagerImpl complainUserManager;

    @Autowired
    private MongoTemplate mongoTemplate;

    private ComplainUserDto complainUserDtoTest;
    private ComplainUser complainUserTest;


    @Override
    @Before
    public void setUp() {
        super.setUp();

        complainUserDtoTest = new ComplainUserDto(null, "nico", "bod", "nicow",
                "test@test.com", "mdp",
                0, null, "ADMIN", null);

    }

    @After
    public void CleanAfter() {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is("test@test.com"));
        mongoTemplate.remove(query,ComplainUser.class);
    }

    @Test
    public void testNewUser() throws Exception{
        String uri = "/newUser";
        String inputJson = super.mapToJson(complainUserDtoTest);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
    }

    @Test
    public void context() {

    }

}
