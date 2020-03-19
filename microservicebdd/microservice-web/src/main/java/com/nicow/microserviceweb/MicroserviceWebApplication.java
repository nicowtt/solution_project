package com.nicow.microserviceweb;

import com.nicow.microservicedao.complainDao.ComplainRequestDao;
import com.nicow.microservicedao.complainDao.ComplainThemeDao;
import com.nicow.microservicedao.complainDao.ComplainUserDao;
import com.nicow.microservicemodel.entities.Complain.ComplainRequest;
import com.nicow.microservicemodel.entities.Complain.ComplainResponse;
import com.nicow.microservicemodel.entities.Complain.ComplainTheme;
import com.nicow.microservicemodel.entities.Complain.ComplainUser;
import com.nicow.microservicemodel.entities.subscription.SubscriptionThemeUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
            };
    }

}
