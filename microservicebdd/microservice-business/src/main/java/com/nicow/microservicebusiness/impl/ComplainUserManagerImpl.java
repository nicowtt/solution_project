package com.nicow.microservicebusiness.impl;

import com.nicow.microservicebusiness.contract.ComplainUserManager;
import com.nicow.microservicedao.complainDao.ComplainUserDao;
import com.nicow.microservicemodel.entities.ComplainUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ComplainUserManagerImpl implements ComplainUserManager {

    @Autowired
    private ComplainUserDao complainUserDao;

    /**
     * For find complain use on bdd with one email
     * @param email
     * @return full ComplainUser
     */
    @Override
    public ComplainUser findByEmail(String email) {
        return complainUserDao.findByEmail(email);
    }
}
