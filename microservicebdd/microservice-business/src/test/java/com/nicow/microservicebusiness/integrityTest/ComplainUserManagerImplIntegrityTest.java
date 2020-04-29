package com.nicow.microservicebusiness.integrityTest;

import com.mongodb.DBObject;
import com.nicow.microservicebusiness.conf.MongoConfig;
import com.nicow.microservicebusiness.contract.ComplainUserManager;
import com.nicow.microservicemodel.dto.ComplainUserDto;
import com.nicow.microservicemodel.entities.ComplainUser;
import com.nicow.microservicemodel.mapper.ComplainUserMapper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = MongoConfig.class)
public class ComplainUserManagerImplIntegrityTest {


    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ComplainUserManager complainUserManager;

    @Autowired
    private ComplainUserMapper complainUserMapper;

    private ComplainUserDto complainUserDtoTest;
    private ComplainUser complainUserTest;


    @Before
    public void setUp() {
        complainUserDtoTest = new ComplainUserDto(null, "nico", "bod", "nicow",
                "test@test.com", "mdp",
                0, null, "ADMIN", null);

        complainUserTest = complainUserManager.addUser(complainUserDtoTest);
    }

    @After
    public void CleanAfter() {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is("test@test.com"));
        mongoTemplate.remove(query,ComplainUser.class);
    }

    @Test
    public void testAddUser() {
        mongoTemplate.save(complainUserTest, "complainUser");

        assertThat(mongoTemplate.findAll(DBObject.class, "complainUser")).extracting("email")
                .containsOnly("test@test.com");

    }

    @Test
    public void testCheckIfMailExist() {
        boolean mailExist = complainUserManager.checkIfMailExist("test@test.com");

        assertTrue("Mail don't exist", mailExist);

    }

    @Test
    public void testCheckIfUserMailAndPasswordIsOk() {
        complainUserTest.setPassword("mdp");
        boolean userMailAndPasswordIsOk = complainUserManager.checkIfUserMailAndPasswordIsOk(complainUserTest);

        assertTrue("mail or password is false", userMailAndPasswordIsOk);
    }

    @Test
    public void testFindByMail() {
        ComplainUser userFindByMail = complainUserManager.findByEmail("test@test.com");

        assertEquals( "test@test.com", userFindByMail.getEmail());
    }



    @Test
    public void contextLoads() {
    }

}
