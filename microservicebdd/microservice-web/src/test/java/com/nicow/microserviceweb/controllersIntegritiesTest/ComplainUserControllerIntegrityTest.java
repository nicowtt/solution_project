package com.nicow.microserviceweb.controllersIntegritiesTest;

import com.nicow.microservicebusiness.impl.ComplainUserManagerImpl;
import com.nicow.microservicemodel.dto.ComplainUserDto;
import com.nicow.microservicemodel.entities.ComplainUser;
import com.nicow.microserviceweb.controllers.ComplainUserController;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;

@WebMvcTest(ComplainUserController.class)
@RunWith(SpringRunner.class)
public class ComplainUserControllerIntegrityTest extends UtilsForTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ComplainUserManagerImpl complainUserManager;

    private ComplainUserDto complainUserDtoTest;


    @Before
    public void setUp() {

        complainUserDtoTest = new ComplainUserDto(null, "nico", "bod", "nicow",
                "test@test.com", "mdp",
                0, null, null, "ADMIN", null);
    }

    @After
    public void CleanAfter() {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is("test@test.com"));
        mongoTemplate.remove(query,ComplainUser.class);
    }

    @Test
    public void Given_newUser_When_performPostNewUser_Then_shouldReturnStatus201() throws Exception{
        // GIVEN
        String uri = "/newUser";
        String inputJson = super.mapToJson(complainUserDtoTest);

        // WHEN
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();

        // THEN
        assertEquals(201, status);
    }

    @Test
    public void Given_newUserDto_When_performPostCheckLogin_Then_shouldReturnResponsonEntityOk() throws Exception {
        // GIVEN
        complainUserManager.addUser(complainUserDtoTest);
        String uri = "/checkLogin";
        String inputJson = super.mapToJson(complainUserDtoTest);

        // WHEN
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();

        // THEN
        assertEquals(200, status);
        }

//    @Test
//    public void context() {
//    }

}
