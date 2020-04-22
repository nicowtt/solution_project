package com.nicow.microservicebusiness.impl;

import com.nicow.microservicebusiness.contract.ComplainUserManager;
import com.nicow.microservicebusiness.contract.PasswordEncoder;
import com.nicow.microservicedao.complainDao.ComplainUserDao;
import com.nicow.microservicemodel.entities.ComplainUser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ComplainUserManagerImpl implements ComplainUserManager {

    @Autowired
    private ComplainUserDao complainUserDao;

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



}
