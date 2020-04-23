package com.nicow.microservicebusiness.impl;

import com.nicow.microservicebusiness.contract.ComplainUserManager;
import com.nicow.microservicebusiness.contract.PasswordEncoder;
import com.nicow.microservicedao.complainDao.ComplainUserDao;
import com.nicow.microservicemodel.dto.ComplainUserDto;
import com.nicow.microservicemodel.entities.ComplainUser;
import com.nicow.microservicemodel.mapper.ComplainUserMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class ComplainUserManagerImpl implements ComplainUserManager {

    @Autowired
    private ComplainUserDao complainUserDao;

    @Autowired
    private ComplainUserMapper complainUserMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    static final Log logger = LogFactory.getLog(ComplainUserManagerImpl.class);


    /**
     * For find complain use on bdd with one email
     * @param email
     * @return full ComplainUser
     */
    @Override
    public ComplainUser findByEmail(String email) {
        return complainUserDao.findByEmail(email);
    }

    /**
     * for check if User Mail and Password is valid
     * @param userToValid
     * @return
     */
    @Override
    public boolean checkIfUserMailAndPasswordIsOk(ComplainUser userToValid) {
        ComplainUser userOnBdd;
        boolean mailExist = false;
        boolean passwordIsValid = false;
        boolean mailAndUserExist = false;

        // check if email exist on bdd
        mailExist = this.checkIfMailExist(userToValid.getEmail());

        if (mailExist) {
            // get user on bdd
            userOnBdd = complainUserDao.findByEmail(userToValid.getEmail());
            //compare password
            passwordIsValid = passwordEncoder.checkPassword(userToValid.getPassword(), userOnBdd.getPassword());
            if (passwordIsValid) {
                mailAndUserExist = true;
                logger.info("L'utilisateur " + userToValid.getEmail() + " est validé.");
            } else {
                logger.info("L'utilisateur " + userToValid.getEmail() + " n'a pas rentré le bon mot de passe.");
            }
        }
        return  mailAndUserExist;
    }

    /**
     * for check if mail exist on bdd
     * @param email
     * @return
     */
    @Override
    public boolean checkIfMailExist(String email) {
        boolean mailExist = false;

        ComplainUser oneUser = complainUserDao.findByEmail(email);
        if (oneUser != null) {
            mailExist = true;
        } else {
            logger.info("L'utilisateur: " + email + " n'existe pas en BDD.");
        }
        return mailExist;
    }

    /**
     * for create new user (user address and user)
     * @param userDto
     */
    @Override
    public ComplainUser addUser(ComplainUserDto userDto) {
        ComplainUser newUserFromDto;
        ComplainUser newUserOnBdd = new ComplainUser();
        ComplainUser userAlreadyOnBdd;
        String emailIn = userDto.getEmail();

        // check if email already exist on bdd
        userAlreadyOnBdd = complainUserDao.findByEmail(emailIn);
        if (userAlreadyOnBdd == null) {
            // create new user on bdd
            newUserFromDto = complainUserMapper.toComplainUser(userDto);
            String hashedPassword = passwordEncoder.hashPassword(userDto.getPassword());
            newUserFromDto.setPassword(hashedPassword);
            newUserFromDto.setRole("USER");

            newUserOnBdd = complainUserDao.save(newUserFromDto);
        }
        return newUserOnBdd;
    }



}
