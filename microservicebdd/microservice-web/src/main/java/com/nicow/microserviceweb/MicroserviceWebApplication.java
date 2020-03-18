package com.nicow.microserviceweb;

import com.nicow.microservicedao.complainDao.ComplainUserDao;
import com.nicow.microservicemodel.entities.Complain.ComplainUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Date;

@SpringBootApplication
@ComponentScan(basePackages = {"com.nicow"})
@EnableMongoRepositories(basePackages = {"com.nicow"})
public class MicroserviceWebApplication {

    @Autowired
    private ComplainUserDao userDao;


    public static void main(String[] args) {
        SpringApplication.run(MicroserviceWebApplication.class, args);
    }

    @Bean
    CommandLineRunner start() {
        return args->{
            //ajout user
            userDao.deleteAll();
            Date todayDate = new Date();
            userDao.save(new ComplainUser(null, "nico", "bod", "nicow","test@test.com", "mdp", 0, todayDate, "ADMIN"));
            userDao.findAll().forEach(System.out::println);

            ComplainUser userOnDao = userDao.findByEmail("test@test.com");
            System.out.println(userOnDao.getName());

            //ajout


        };
    }

}
