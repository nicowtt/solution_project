package com.nicow.microservicebusiness.securitytoken;

import com.nicow.microservicedao.complainDao.ComplainUserDao;
import com.nicow.microservicemodel.entities.Complain.ComplainUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private ComplainUserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        ComplainUser userOnBdd = userDao.findByEmail(userEmail);
        return new User(userOnBdd.getEmail(), userOnBdd.getPassword(), new ArrayList<>());
    }
}
