package com.nicow.microserviceweb;

import com.nicow.microservicedao.complainDao.ComplainRequestDao;
import com.nicow.microservicedao.complainDao.ComplainResponseDao;
import com.nicow.microservicedao.complainDao.ComplainUserDao;
import com.nicow.microservicemodel.dto.ComplainUserDto;
import com.nicow.microservicemodel.entities.ComplainRequest;
import com.nicow.microservicemodel.entities.ComplainResponse;
import com.nicow.microservicemodel.entities.ComplainUser;
import com.nicow.microservicemodel.mapper.ComplainUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@SpringBootApplication
@ComponentScan(basePackages = {"com.nicow"})
@EnableMongoRepositories(basePackages = {"com.nicow"})
@EntityScan("com.nicow")
public class MicroserviceWebApplication {

    @Autowired
    private ComplainUserDao userDao;

    @Autowired
    private ComplainRequestDao requestDao;

    @Autowired
    private ComplainResponseDao responseDao;

    @Autowired
    private ComplainUserMapper userMapper;

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceWebApplication.class, args);
    }

//    @Bean
//    CommandLineRunner start() {
//        return args->{
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//            Date date = new Date();
//            String todayDate = dateFormat.format(date);
//            Date yesterdayDate = new Date();
//            yesterdayDate.setDate(date.getDate() - 1);
//            String yesterday = dateFormat.format(yesterdayDate);
//
//            //ajout user
//            userDao.deleteAll();
//            userDao.save(new ComplainUser(null, "nico", "bod", "nicow","nico.bod@gmail.com", "$2a$10$ZrNev/FCEyfKp3.Zc/irx.OrtFuqL7X6t.tJytIOiYLQ458k2jasO", 0, null, todayDate, "ADMIN"));
//            userDao.save(new ComplainUser(null, "steven", "seagal", "seagul", "steven.seagal@gmail.com", "$2a$10$ZrNev/FCEyfKp3.Zc/irx.OrtFuqL7X6t.tJytIOiYLQ458k2jasO", 0, null, todayDate, "USER"));
//            userDao.findAll().forEach(System.out::println);
//
//            //ajout de request
//            requestDao.deleteAll();
//            responseDao.deleteAll();
//
//            ComplainRequest firstRequest = requestDao.save (new ComplainRequest(null, "debut du confinement trop tard!", "nicow", "nico.bod@gmail.com", todayDate, 0, new ArrayList<>(), new ArrayList<>(), "corona-virus", todayDate));
//            // creation of response
//            ComplainResponse firstResponseFirstRequest = responseDao.save (new ComplainResponse(null, "pas d'accord, il y aurais plus de mort!", 0, "steven.seagal@gmail.com" , "seagul", todayDate, new ArrayList<>(), firstRequest.getId()));
//            ComplainResponse secondResponseFirstRequest = responseDao.save (new ComplainResponse(null, "d'accord, ça aurais été mieux", 0, "nico.bod@gmail.com", "nicow", todayDate, new ArrayList<>(), firstRequest.getId()));
//            // ajout de la response id dans la request
//            firstRequest.addResponseIdOnRequest(firstResponseFirstRequest.getId());
//            firstRequest.addResponseIdOnRequest(secondResponseFirstRequest.getId());
//            // update request
//            requestDao.save(firstRequest);
//
//            ComplainRequest secondRequest = requestDao.save (new ComplainRequest(null, "temp pourris la semaine prochaine", "seagul", "steven.seagal@gmail.com", yesterday, 0, new ArrayList<>(), new ArrayList<>(), "Méteo", yesterday));
//            // ajout de la request id dans le theme
//            // create responses
//            ComplainResponse firstResponseSecondRequest = responseDao.save (new ComplainResponse(null,
//                    "c'est vrai", 0, "nico.bod@gmail.com", "nicow", yesterday,
//                    new ArrayList<>(), secondRequest.getId()));
//            ComplainResponse secondResponseSecondRequest = responseDao.save (new ComplainResponse(null,
//                    "non pas le samedi", 0, "steven.seagal@gmail.com" , "seagul", yesterday,
//                    new ArrayList<>(), secondRequest.getId()));
//            ComplainResponse thirdResponsesSecondRequest = responseDao.save (new ComplainResponse(null,
//                    "pas sur", 0, "steven.seagal@gmail.com", "seagul", yesterday,
//                    new ArrayList<>(), secondRequest.getId()));
//            // ajout de la response id dans la request
//            secondRequest.addResponseIdOnRequest(firstResponseSecondRequest.getId());
//            secondRequest.addResponseIdOnRequest(secondResponseSecondRequest.getId());
//            secondRequest.addResponseIdOnRequest(thirdResponsesSecondRequest.getId());
//            // update request
//            requestDao.save(secondRequest);
//
//            // test dto
//            ComplainUser userFromBdd = userDao.findByEmail("nico.bod@gmail.com");
//            ComplainUserDto complainUserDto = userMapper.toComplainUserDto(userFromBdd);
//            ComplainUser userTest = userMapper.toComplainUser(complainUserDto);
//            System.out.println(userTest.toString());
//            };
//    }

}
