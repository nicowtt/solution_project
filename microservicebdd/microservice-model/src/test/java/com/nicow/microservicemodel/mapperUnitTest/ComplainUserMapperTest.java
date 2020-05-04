package com.nicow.microservicemodel.mapperUnitTest;

import com.nicow.microservicemodel.conf.MongoConfig;
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
public class ComplainUserMapperTest {

    @Autowired
    private ComplainUserMapper complainUserMapper;

    @Autowired
    private MongoTemplate mongoTemplate;

    private ComplainUserDto complainUserDtoTest;
    private ComplainUser complainUserTest;


    @Before
    public void setUp() {
        complainUserDtoTest = new ComplainUserDto(null, "nico", "bod", "nicow",
                "test@test.com", "mdp",
                0, null, null, "ADMIN", null);
        complainUserTest = new ComplainUser(null, "nico", "bod", "nicow",
                "test@test.com", "mdp", 0, null, null, "ADMIN");
    }

    @Test
    public void testShouldMapUserDtoToUser() {
        ComplainUser complainUserTest = complainUserMapper.toComplainUser(complainUserDtoTest);

        assertThat( complainUserTest ).isNotNull();
        assertThat( complainUserTest.getEmail() ).isEqualTo( "test@test.com" );
    }

    @Test
    public void testShouldMapUserToUserDto() {
        ComplainUserDto complainUserDtoTest = complainUserMapper.toComplainUserDto(complainUserTest);

        assertThat(complainUserDtoTest).isNotNull();
        assertThat(complainUserDtoTest.getEmail()).isEqualTo("test@test.com");
    }

}
