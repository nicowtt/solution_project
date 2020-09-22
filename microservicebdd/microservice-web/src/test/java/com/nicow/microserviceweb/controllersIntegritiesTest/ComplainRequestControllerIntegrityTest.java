package com.nicow.microserviceweb.controllersIntegritiesTest;

import com.nicow.microservicebusiness.impl.ComplainrequestManagerImpl;
import com.nicow.microservicebusiness.securitytoken.JwtUserDetailsService;
import com.nicow.microservicedao.complainDao.ComplainRequestDao;
import com.nicow.microservicedao.complainDao.ComplainThemeDao;
import com.nicow.microservicedao.complainDao.ComplainUserDao;
import com.nicow.microservicemodel.dto.ComplainUserDto;
import com.nicow.microservicemodel.entities.*;
import com.nicow.microserviceweb.controllers.ComplainRequestControlleur;
import com.nicow.microserviceweb.controllers.ComplainUserController;
import com.nicow.microserviceweb.security.JwtTokenUtil;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@WebMvcTest(ComplainRequestControlleur.class)
@RunWith(SpringRunner.class)
public class ComplainRequestControllerIntegrityTest extends UtilsForTest{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ComplainUserDao userDao;

    @Autowired
    private ComplainThemeDao themeDao;

    @Autowired
    private ComplainRequestDao requestDao;

    private ComplainTheme complainTheme;
    private ComplainRequest complainRequest;
    private ComplainUser complainUser;


    @Before
    public void setUp() {
        // add user who already vote for change popularity
        String userAlreadyVote = "nicow";
        List<String> userAlreadyVoteList = new ArrayList<>();
        userAlreadyVoteList.add(userAlreadyVote);

        complainUser = userDao.save(new ComplainUser(null, "nico", "bod", "nicow",
                "test@test.com", "mdp",
                0, null, null, "ADMIN"));

        complainTheme = themeDao.save(new ComplainTheme(null, "corona-virus",
                "http://photoDeTrucs.com",  "test@test.com", null,
                0, new ArrayList<>(), new ArrayList<>()));

        complainRequest = requestDao.save (new ComplainRequest(null,
                "debut du confinement trop tard!", "nicow", "test@test.com",
                null, 0, userAlreadyVoteList, new ArrayList<>(),
                "corona-virus", null));

    }

    @After
    public void CleanAfter() {
        Query query = new Query();
        query.addCriteria(Criteria.where("creatorEmail").is("test@test.com"));
        mongoTemplate.remove(query, ComplainTheme.class);
        mongoTemplate.remove(query, ComplainRequest.class);
        Query query2 = new Query();
        query.addCriteria(Criteria.where("email").is("test@test.com"));
        mongoTemplate.remove(query2, ComplainUser.class);
    }


    @Test
    public void Given_When_getAllRequest() throws Exception{
        String uri = "/getAllRequests";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        ComplainRequest[] requestlist = super.mapFromJson(content, ComplainRequest[].class);
        assertTrue(requestlist.length > 0);
    }

    @Test
    @WithMockUser
    public void Given_request_When_changePopularity_Then_shouldReturnStatus200() throws Exception{
        // GIVEN
        String uri = "/changeRequestPopularity/marcel"; //  marcel is not on already voted list
        String inputJson = super.mapToJson(complainRequest);

        // WHEN
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();

        // THEN
        assertEquals(200, status);
    }

    @Test
    @WithMockUser
    public void Given_request_When_changePopularityAlreadyVoted_Then_shouldReturnStatus403() throws Exception{
        // GIVEN
        String uri = "/changeRequestPopularity/nicow"; //  nicow is on already voted list
        String inputJson = super.mapToJson(complainRequest);

        // WHEN
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();

        // THEN
        assertEquals(403, status);
    }

}
