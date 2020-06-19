package com.nicow.microservicemodel.dto;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ComplainUserDtoTest {

    @Test
    public void testToString() {
        ComplainUserDto complainUserDto = new ComplainUserDto();
        complainUserDto.setId("1");
        complainUserDto.setName("nico");
        complainUserDto.setFirstName("bod");
        complainUserDto.setPseudo("nicow");
        complainUserDto.setEmail("nico.bod@gmail.com");
        complainUserDto.setPassword("test");
        complainUserDto.setPopularity(0);
        complainUserDto.setPeopleForPopularity(null);
        complainUserDto.setCreationDate(null);
        complainUserDto.setRole("ADMIN");
        complainUserDto.setToken(null);

        Assert.assertEquals("ComplainUserDto{id='1', name='nico', firstName='bod', pseudo='nicow', " +
                "email='nico.bod@gmail.com', password='test', popularity=0, peopleForPopularity=null, " +
                "creationDate=null, role='ADMIN', token='null'}", complainUserDto.toString());
    }
}
