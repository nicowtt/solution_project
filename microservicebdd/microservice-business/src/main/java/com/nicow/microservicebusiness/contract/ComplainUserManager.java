package com.nicow.microservicebusiness.contract;

import com.nicow.microservicemodel.entities.ComplainUser;
import org.springframework.stereotype.Service;

@Service
public interface ComplainUserManager {

    ComplainUser findByEmail(String Email);
}
