package com.nicow.microserviceweb;

import com.nicow.microservicedao.complainDao.ComplainRequestDao;
import com.nicow.microservicedao.complainDao.ComplainThemeDao;
import com.nicow.microservicedao.complainDao.ComplainUserDao;
import com.nicow.microservicemodel.dto.ComplainUserDto;
import com.nicow.microservicemodel.entities.ComplainRequest;
import com.nicow.microservicemodel.entities.ComplainResponse;
import com.nicow.microservicemodel.entities.ComplainTheme;
import com.nicow.microservicemodel.entities.ComplainUser;
import com.nicow.microservicemodel.entities.SubscriptionThemeUser;
import com.nicow.microservicemodel.mapper.ComplainUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.ArrayList;
import java.util.Date;

@SpringBootApplication
@ComponentScan(basePackages = {"com.nicow"})
@EnableMongoRepositories(basePackages = {"com.nicow"})
public class MicroserviceWebApplication {

    @Autowired
    private ComplainUserDao userDao;

    @Autowired
    private ComplainThemeDao themeDao;

    @Autowired
    private ComplainRequestDao requestDao;

    @Autowired
    private ComplainUserMapper userMapper;


    public static void main(String[] args) {
        SpringApplication.run(MicroserviceWebApplication.class, args);
    }

    @Bean
    CommandLineRunner start() {
        return args->{
            //ajout user
            userDao.deleteAll();
            Date todayDate= new Date();
            userDao.save(new ComplainUser(null, "nico", "bod", "nicow","nico.bod@gmail.com", "mdp", 0, todayDate, "ADMIN"));
            userDao.save(new ComplainUser(null, "steven", "seagal", "seagul", "steven.seagal@gmail.com", "mdp", 0, todayDate, "USER"));
            userDao.findAll().forEach(System.out::println);

            //ajout d'un theme et d'un request
            themeDao.deleteAll();
            requestDao.deleteAll();

            ComplainTheme firstTheme = themeDao.save(new ComplainTheme(null, "corona-virus","http://photoDeTrucs.com",  "nico.bod@gmail.com", todayDate, 0, new ArrayList<ComplainRequest>(), new ArrayList<SubscriptionThemeUser>()));
            ComplainRequest firstRequest = requestDao.save (new ComplainRequest(null, "debut du confinement trop tard!", "nico.bod@gmail.com", todayDate, 0, new ArrayList<ComplainResponse>()));

            // ajout de la request dans le theme
            firstTheme.getComplainRequests().add(firstRequest);
            // update theme
            themeDao.save(firstTheme);

            // test dto
            ComplainUser userFromBdd = userDao.findByEmail("nico.bod@gmail.com");
            ComplainUserDto complainUserDto = userMapper.toComplainUserDto(userFromBdd);
            ComplainUser userTest = userMapper.toComplainUser(complainUserDto);
            System.out.println(userTest.toString());
            };
    }

}
