package com.nicow.microservicebusiness.contract;

import com.nicow.microservicemodel.dto.ComplainUserDto;
import com.nicow.microservicemodel.entities.ComplainUser;
import org.springframework.stereotype.Service;

@Service
public interface ComplainUserManager {

    ComplainUser findByEmail(String Email);
    boolean checkIfUserMailAndPasswordIsOk(ComplainUser userToValidate);
    boolean checkIfMailExist(String mail);
    ComplainUser addUser(ComplainUserDto userDto) ;
}
