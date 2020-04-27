package com.nicow.microservicebusiness.integrityTest;

import com.mongodb.DBObject;
import com.nicow.microservicebusiness.conf.MongoConfig;
import com.nicow.microservicebusiness.contract.ComplainUserManager;
import com.nicow.microservicemodel.dto.ComplainUserDto;
import com.nicow.microservicemodel.entities.ComplainUser;
import com.nicow.microservicemodel.mapper.ComplainUserMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = MongoConfig.class)
public class ComplainUserManagerImplIntegrityTest {


    private ComplainUserDto complainUserDtoTest;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ComplainUserManager complainUserManager;

    @Autowired
    private ComplainUserMapper complainUserMapper;


    @Before
    public void setUp() {
        complainUserDtoTest = new ComplainUserDto(null, "nico", "bod", "nicow",
                "test@test.com", "test",
                0, null, "ADMIN", null);
    }

    @Test
    public void saveData() {

        // when
        ComplainUser userAdded = complainUserManager.addUser(complainUserDtoTest);
        mongoTemplate.save(userAdded, "test");

        // then
        assertThat(mongoTemplate.findAll(DBObject.class, "test")).extracting("email")
                .containsOnly("test@test.com");
    }

    @Test
    public void shouldMapUserDtoToUser() {

        //when
        ComplainUser complainUserTest = complainUserMapper.toComplainUser(complainUserDtoTest);

        //then
        assertThat( complainUserTest ).isNotNull();
        assertThat( complainUserTest.getEmail() ).isEqualTo( "test@test.com" );

    }



    @Test
    public void contextLoads() {
    }

}
