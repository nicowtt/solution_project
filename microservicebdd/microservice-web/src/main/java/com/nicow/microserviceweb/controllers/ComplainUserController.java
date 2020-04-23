package com.nicow.microserviceweb.controllers;

import com.nicow.microservicebusiness.contract.ComplainUserManager;
import com.nicow.microservicebusiness.securitytoken.JwtUserDetailsService;
import com.nicow.microservicemodel.dto.ComplainUserDto;
import com.nicow.microservicemodel.entities.ComplainUser;
import com.nicow.microservicemodel.mapper.ComplainUserMapper;
import com.nicow.microserviceweb.security.JwtTokenUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class ComplainUserController {

    static final Log logger = LogFactory.getLog(ComplainUserController.class);

    @Autowired
    private ComplainUserMapper complainUserMapper;

    @Autowired
    private ComplainUserManager complainUserManager;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @PostMapping(value = "/checkLogin", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ComplainUserDto> checkLogin(@RequestBody ComplainUserDto complainUserDto) {

        ComplainUser complainUserInput = complainUserMapper.toComplainUser(complainUserDto);
        boolean userInputToValid = complainUserManager.checkIfUserMailAndPasswordIsOk(complainUserInput);
        if (userInputToValid) {
            ComplainUser fullUserFromDao = complainUserManager.findByEmail(complainUserInput.getEmail());
            ComplainUserDto fullUserFromDaoDto = complainUserMapper.toComplainUserDto(fullUserFromDao);
            // token creation
            final UserDetails userDetails = jwtUserDetailsService
                    .loadUserByUsername(complainUserInput.getEmail());
            final String token = jwtTokenUtil.generateToken(userDetails);
            fullUserFromDaoDto.setToken(token);
            fullUserFromDaoDto.setPassword(null);
            return ResponseEntity.ok(fullUserFromDaoDto);
        } else {
            return (new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE));
        }

    }

    /**
     * for create new user
     * @param newUserDto
     * @return
     */
    @PostMapping(value = "/newUser", consumes = "application/json")
    public ResponseEntity<String> newUser(@RequestBody ComplainUserDto newUserDto) {
       ComplainUser newUserOnBdd;
        newUserOnBdd = complainUserManager.addUser(newUserDto);

        if (newUserOnBdd.getId() != null) {
            return (new ResponseEntity<>(HttpStatus.CREATED));
        } else {
            return (new ResponseEntity<>("email already exist",HttpStatus.CONFLICT));
        }
    }
}
