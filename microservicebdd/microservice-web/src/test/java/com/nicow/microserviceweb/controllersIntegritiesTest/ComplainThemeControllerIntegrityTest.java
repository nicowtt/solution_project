package com.nicow.microserviceweb.controllersIntegritiesTest;

import com.nicow.microservicedao.complainDao.ComplainThemeDao;
import com.nicow.microservicemodel.entities.*;
import com.nicow.microserviceweb.controllers.ComplainThemeController;
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

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@WebMvcTest(ComplainThemeController.class)
@RunWith(SpringRunner.class)
public class ComplainThemeControllerIntegrityTest extends UtilsForTest{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ComplainThemeDao themeDao;

    private ComplainTheme complainTheme;


    @Before
    public void setUp() {

        complainTheme = themeDao.save(new ComplainTheme(null, "corona-virus",
                "http://photoDeTrucs.com",  "test@test.com", null,
                0, new ArrayList<>(), new ArrayList<>()));

    }

    @After
    public void CleanAfter() {
        Query query = new Query();
        query.addCriteria(Criteria.where("creatorEmail").is("test@test.com"));
        mongoTemplate.remove(query, ComplainTheme.class);
    }

    @Test
    public void Given_When_getAllTheme() throws Exception{
        String uri = "/GetAllThemes";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        ComplainTheme[] themeslist = super.mapFromJson(content, ComplainTheme[].class);
        assertTrue(themeslist.length > 0);
    }
}
